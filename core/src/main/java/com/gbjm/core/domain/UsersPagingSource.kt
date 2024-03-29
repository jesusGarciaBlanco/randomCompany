package com.gbjm.core.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gbjm.core.api.ApiService
import com.gbjm.core.model.entity.UserDataEntity
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

private const val TMDB_STARTING_PAGE_INDEX = 1

class UsersPagingSource constructor(
    private val service: ApiService
) : PagingSource<Int, UserDataEntity>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDataEntity> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getUsers(
                pageSize = 10,
                page = pageIndex
            )
            val users = response.results
            val nextKey =
                if (users.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = users,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, UserDataEntity>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}