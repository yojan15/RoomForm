package com.example.roomform.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)
    @Query("select * from user where number = :number AND password = :password")
    fun signIn(number: String , password : String) :User?

    @Query("select * from user where number = :number")
    suspend fun getUserByPhoneNumber(number: String) : User?

    @Query("select * from user")
    fun getAllData() : List<User>
}