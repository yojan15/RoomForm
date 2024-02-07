package com.example.roomform.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.net.PasswordAuthentication

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)
    @Query("select * from user where number = :number AND password = :password")
    fun signIn(number: String , password : String) :User?
}