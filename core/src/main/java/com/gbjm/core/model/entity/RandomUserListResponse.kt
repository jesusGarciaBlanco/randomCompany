package com.gbjm.core.model.entity

import com.gbjm.core.model.common.RandomUserCommonResponse
import com.google.gson.annotations.SerializedName

data class RandomUserListResponse (
    @SerializedName("results") val userListData: List<UserDataEntity>,
    val error: String?)
