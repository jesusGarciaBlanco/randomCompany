package com.gbjm.core

import com.gbjm.core.domain.mapper.UserMapperImp
import com.gbjm.core.model.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UserModelMapperTest {
    val userMapper = UserMapperImp(Dispatchers.Default)
    val userDataEntityBritney = UserDataEntity(
        gender= "female",
        name = NameEntity(title = "Miss", first = "Britney", last = "Sims"),
        location = UserLocation(city = "Dublin", state = "Westmeath", country= "Ireland", postcode = "66909"),
        email= "britney.sims@example.com",
        login = Login("2d5afd5e-a598-469f-9933-4edba18bcefb"),
        registered = RegisteredDate("2014-05-20T20:54:39.692Z"),
        phone = "011-578-8963",
        picture = Pictures(
            large = "https://randomuser.me/api/portraits/women/62.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/62.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/62.jpg"
        )
    )

    val userDataEntityImogen = UserDataEntity(
        gender= "female",
        name = NameEntity(title = "Ms", first = "Imogen", last = "Edwards"),
        location = UserLocation(city = "Dunedin", state = "Canterbury", country= "New Zealand", postcode = "21471"),
        email= "imogen.edwards@example.com",
        login = Login("5a79ebec-08a6-45de-a2de-b3222f0d1ffe"),
        registered = RegisteredDate("2016-04-10T14:57:49.011Z"),
        phone = "(179)-998-3204",
        picture = Pictures(
            large = "https://randomuser.me/api/portraits/women/95.jpg",
            medium = "https://randomuser.me/api/portraits/med/women/95.jpg",
            thumbnail = "https://randomuser.me/api/portraits/thumb/women/95.jpg"
        )
    )

    val userDataList = listOf(userDataEntityBritney,userDataEntityImogen)

    @Test
    fun checkUserDataModelToDomainMapper() = runBlocking<Unit> {
        val userDataDomainList = userMapper.mapRemoteUsersListToDomain(userDataList)
        Assert.assertEquals(userDataDomainList[0].uuid, "2d5afd5e-a598-469f-9933-4edba18bcefb")
        Assert.assertEquals(userDataDomainList[0].name, "Miss Britney")
        Assert.assertEquals(userDataDomainList[0].surname, "Sims")
        Assert.assertEquals(userDataDomainList[0].phone, "011-578-8963")
        Assert.assertEquals(userDataDomainList[0].address, "Ireland, Westmeath, Dublin, 66909")
        Assert.assertEquals(userDataDomainList[0].email, "britney.sims@example.com")
        Assert.assertEquals(userDataDomainList[0].registeredDate, "2014-05-20T20:54:39.692Z")
        Assert.assertEquals(userDataDomainList[0].pictures.large, "https://randomuser.me/api/portraits/women/62.jpg")
        Assert.assertEquals(userDataDomainList[0].pictures.medium, "https://randomuser.me/api/portraits/med/women/62.jpg")
        Assert.assertEquals(userDataDomainList[0].pictures.thumbnail, "https://randomuser.me/api/portraits/thumb/women/62.jpg")
        Assert.assertEquals(userDataDomainList[0].gender, "female")

        Assert.assertEquals(userDataDomainList[1].uuid, "5a79ebec-08a6-45de-a2de-b3222f0d1ffe")
        Assert.assertEquals(userDataDomainList[1].name, "Ms Imogen")
        Assert.assertEquals(userDataDomainList[1].surname, "Edwards")
        Assert.assertEquals(userDataDomainList[1].phone, "(179)-998-3204")
        Assert.assertEquals(userDataDomainList[1].address, "New Zealand, Canterbury, Dunedin, 21471")
        Assert.assertEquals(userDataDomainList[1].email, "imogen.edwards@example.com")
        Assert.assertEquals(userDataDomainList[1].registeredDate, "2016-04-10T14:57:49.011Z")
        Assert.assertEquals(userDataDomainList[1].pictures.large, "https://randomuser.me/api/portraits/women/95.jpg")
        Assert.assertEquals(userDataDomainList[1].pictures.medium, "https://randomuser.me/api/portraits/med/women/95.jpg")
        Assert.assertEquals(userDataDomainList[1].pictures.thumbnail, "https://randomuser.me/api/portraits/thumb/women/95.jpg")
        Assert.assertEquals(userDataDomainList[1].gender, "female")


    }
}