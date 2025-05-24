package data.models

import domain.User

data class UserResponse (
    val id : Int = 0,
    val message : String = "",
    val data : User
)