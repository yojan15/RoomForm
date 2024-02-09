package com.example.roomform

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomform.data.User
import com.example.roomform.data.UserDatabase
class UserAdapter(private var users :MutableList<User> , private val database: UserDatabase)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
        inner class UserViewHolder(UserView : View): RecyclerView.ViewHolder(UserView) {
            val number : TextView = UserView.findViewById(R.id.get_number)
            val name : TextView = UserView.findViewById(R.id.get_name)
            val password : TextView = UserView.findViewById(R.id.getPassword)

        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate((R.layout.item),parent,false)
        return UserViewHolder(view)
    }
    override fun getItemCount(): Int {
        return users.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {



            holder.number.text = users[position].number
            holder.name.text = users[position].name
            holder.password.text = users[position].password

    }
}