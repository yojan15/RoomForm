package com.example.roomform.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class User(
    @PrimaryKey
    val number : String,
    val name : String,
    val password : String
)
