package com.example.newp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class userAdapter(val context: Context,val userList:ArrayList<User>) :RecyclerView.Adapter<userAdapter.UserViewHolder>(){
  class UserViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
      val textName = itemView.findViewById<TextView>(R.id.text_name)
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view :View = LayoutInflater.from(context).inflate(R.layout.user,parent,false)
        return  UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
         val currentUser = userList[position]

        holder.textName.text = currentUser.name


holder.itemView.setOnClickListener {

    val intent =Intent(context , chat::class.java)

    intent.putExtra("name",currentUser.name)
    intent.putExtra("uid",currentUser.uid)


    context.startActivity(intent)
}
    }
}