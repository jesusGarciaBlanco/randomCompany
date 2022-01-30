package com.gbjm.core.domain

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gbjm.core.api.ApiService
import com.gbjm.core.domain.entity.User
import com.gbjm.core.domain.mapper.UserMapperImp
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val INITIAL_PAGE = 10

@OptIn(ExperimentalPagingApi::class)
class CustomRemoteMediator(
    private val database: AppDatabase,
    private val networkService: ApiService,
    private val userMapper: UserMapperImp
) : RemoteMediator<Int, User>() {
    val userDao = database.userDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, User>
    ): MediatorResult {
        return try {
            // The network load method takes an optional String
            // parameter. For every page after the first, pass the String
            // token returned from the previous page to let it continue
            // from where it left off. For REFRESH, pass null to load the
            // first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                // Get the last User object id for the next RemoteKey.
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.uuid
                }
            }

            // Suspending network load via Retrofit. This doesn't need to
            // be wrapped in a withContext(Dispatcher.IO) { ... } block
            // since Retrofit's Coroutine CallAdapter dispatches on a
            // worker thread.
            val usersDbSize = userDao.count()

            Timber.d("user dbSize= $usersDbSize")
            var page : Int = INITIAL_PAGE
            if (usersDbSize >= 10){
                page = (usersDbSize / 10) +1
            }

            Timber.d("page=$page")
            val response = networkService.getUsers(NETWORK_PAGE_SIZE, page)

            // Store loaded data, and next key in transaction, so that
            // they're always consistent.
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
//                    userDao.deleteById(query)
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                val userListDomain = userMapper.mapRemoteUsersListToDomain(response.results)
                userDao.insertAll(userListDomain)
            }

            // End of pagination has been reached if no users are returned from the
            // service
            MediatorResult.Success(
                endOfPaginationReached = response.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        // Require that remote REFRESH is launched on initial load and succeeds before launching
        // remote PREPEND / APPEND.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}