package com.gbjm.randomcompany.ui.detail

import android.util.Log
import androidx.lifecycle.*
import com.gbjm.core.domain.UsersRepository
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import com.gbjm.randomcompany.ui.detail.mapper.UiUserDetailMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
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
            Log.d("DBase", "userDetailMapper id=${userDetailMapped.id}")
            Log.d("DBase", "userDetailMapper name=${userDetailMapped.name}")
            Log.d("DBase", "userDetailMapper email=${userDetailMapped.email}")
            _uiUserDetail.postValue(userDetailMapped)
        }
    }

}
