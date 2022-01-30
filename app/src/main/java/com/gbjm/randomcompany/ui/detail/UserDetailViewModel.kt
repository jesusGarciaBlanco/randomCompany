package com.gbjm.randomcompany.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbjm.core.domain.UsersRepository
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import com.gbjm.randomcompany.ui.detail.mapper.UiUserDetailMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    private val repository: UsersRepository,
    private val mapper: UiUserDetailMapper
): ViewModel() {
    val _uiUserDetail = MutableLiveData<UiUserDetail>()
    val uiUserDetail: LiveData<UiUserDetail>
        get() = _uiUserDetail

    val _uiError = MutableLiveData<String>()
    val uiError: LiveData<String>
        get() = _uiError


    fun onUserDetailNeeded(userId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userDomain = repository.getUserDetail(userId)
            val userDetailMapped = mapper.mapDomainUserToUiDetail(userDomain)
            _uiUserDetail.postValue(userDetailMapped)
        }
    }
}
