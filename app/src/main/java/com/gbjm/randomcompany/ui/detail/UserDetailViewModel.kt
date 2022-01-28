package com.gbjm.randomcompany.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbjm.randomcompany.ui.detail.entity.UiUserDetail
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(): ViewModel() {
    val _uiUserDetail = MutableLiveData<UiUserDetail>()
    val uiUserDetail: LiveData<UiUserDetail>
        get() = _uiUserDetail

    val _uiError = MutableLiveData<String>()
    val uiError: LiveData<String>
        get() = _uiError


    fun onUserDetailNeeded(userId: Int) {
        //TODO connect with UseCase
        _uiUserDetail.value = UiUserDetail(
            "https://static.dw.com/image/55090176_303.jpg", "Pedro", "Picapiedra",
        "Varón", "pueblo paleta 3, portal 2, bajo Z", "23/11/1009",
        "pedro.picapiedra@gmail.com")
    }
}
