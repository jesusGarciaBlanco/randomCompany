package com.gbjm.randomcompany.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.gbjm.core.domain.UsersRepository
import com.gbjm.randomcompany.ui.users.entity.UiUserRow
import com.gbjm.randomcompany.ui.users.mapper.UiUserMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        val favorites = usersRepository.getFavorites()
        return usersRepository.getAllUsers()
            .map { pagingData ->
                pagingData.map {
                    mapper.mapDomainUserToUi(domainUser = it)
                }
            }
            .cachedIn(viewModelScope)
    }
}