package com.gbjm.randomcompany.ui.users

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.gbjm.core.domain.UsersRepository
import com.gbjm.core.domain.entity.Favorite
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import com.gbjm.randomcompany.ui.users.mapper.UiUserMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val mapper: UiUserMapper
): ViewModel() {

    val _uiUserList = MutableLiveData<List<UiUserRow>>()
    val uiUserList: LiveData<List<UiUserRow>>
        get() = _uiUserList

    val _uiError = MutableLiveData<String>()
    val uiError: LiveData<String>
        get() = _uiError

    suspend fun onListNeeded(): Flow<PagingData<UiUserRow>> {
        val favorites = runBlocking(Dispatchers.IO){
            usersRepository.getFavorites()
        }
        return usersRepository.getAllUsers()
            .map { pagingData ->
                pagingData.map {
                    mapper.mapDomainUserToUi(domainUser = it, favorites = favorites)
                }
            }
            .cachedIn(viewModelScope)

    }

    fun onRefresh() {
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.clearUsers()
        }
    }

    fun onAddFavorite(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.addFavorite(id)
        }
    }

    fun onDeleteFavorite(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.deleteFavorite(id)
        }
    }

    fun onDeleteItem(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            usersRepository.deleteUser(id)
        }
    }
}