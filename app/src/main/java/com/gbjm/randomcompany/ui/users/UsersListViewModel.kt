package com.gbjm.randomcompany.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbjm.randomcompany.ui.users.entity.UiUserRow

class UsersListViewModel : ViewModel() {

    val _uiUserList = MutableLiveData<List<UiUserRow>>()
    val uiUserList: LiveData<List<UiUserRow>>
        get() = _uiUserList

    val _uiError = MutableLiveData<String>()
    val uiError: LiveData<String>
        get() = _uiError


    fun onListNeeded() {
        val uiUser1 = UiUserRow(0, "Pablo", "Escobar", "pablo.escobar@gmail.com",
            "https://media.gettyimages.com/photos/this-undated-file-photo-shows-jailed-medellin-drugs-cartel-leader-picture-id1228557386?s=612x612",
            "92345666", false
        )
        val uiUser2 = UiUserRow(1, "Pedro", "Picapiedra", "pedro.picapiedra@gmail.com",
            "https://static.wikia.nocookie.net/doblaje/images/d/dd/Fredflintstone.jpg/revision/latest?cb=20100707215559&path-prefix=es",
            "923454232", false
        )

        val uiUser3 = UiUserRow(2, "Ant", "Hormiga", "ant.hormiga@gmail.com",
            "https://images-na.ssl-images-amazon.com/images/S/pv-target-images/dd82baf735ca2a19d2b9bfd6e98248646df56239fa5789c1508564b7a2c7d3a9._RI_V_TTW_.jpg",
            "92345666", false
        )

        val uiUserList = mutableListOf<UiUserRow>(uiUser1, uiUser2, uiUser3)
        _uiUserList.value = uiUserList
    }
}