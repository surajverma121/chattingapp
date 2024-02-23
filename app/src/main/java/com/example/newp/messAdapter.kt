package com.example.newp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class messAdapter(val context: Context,val messageList: ArrayList<Message>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    val ITEM_RECEVIE =1;
    val ITEM_SEND = 2;

    class SendViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val senMessage  = itemView.findViewById<TextView>(R.id.text_send_mess)
    }

    class RecevieViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val recMessage  = itemView.findViewById<TextView>(R.id.text_recevid_mess)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view :View = LayoutInflater.from(context).inflate(R.layout.recevie,parent,false)
            return RecevieViewHolder(view)
        }else {
            val view :View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderID)) {
            return ITEM_SEND
        } else {
            return  ITEM_RECEVIE
        }
    }
    override fun getItemCount(): Int {
        return  messageList.size

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage =  messageList[position]

        if (holder.javaClass==SendViewHolder::class.java){
            val viewHolder= holder as SendViewHolder
            holder.senMessage.text = currentMessage.message
        }else {
            val viewHolder = holder as RecevieViewHolder

            holder.recMessage.text =currentMessage.message

        }
    }
}
