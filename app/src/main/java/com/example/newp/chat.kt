package com.example.newp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class chat : AppCompatActivity() {

    private lateinit var  chatRV  :RecyclerView
    private lateinit var  messageBox :EditText
    private lateinit var sendBtn : ImageView
    private lateinit var messageAdapter :messAdapter
    private lateinit var  messageList : ArrayList<Message>
    private  lateinit var mDbRef :DatabaseReference
    var receiverRoom :String ? =null
    var sendRoom :String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name  = intent.getStringExtra("name")
        val receiverUid  = intent.getStringExtra("uid")

        val senderUid  = FirebaseAuth.getInstance().currentUser?.uid

        mDbRef = FirebaseDatabase.getInstance().getReference()
        sendRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title= name

        chatRV = findViewById(R.id.chatRV)
        messageBox = findViewById(R.id.messageBox)
        sendBtn = findViewById(R.id.sendbtn)
        messageList = ArrayList()
        messageAdapter  = messAdapter(this,messageList)
        chatRV.layoutManager = LinearLayoutManager(this)
        chatRV.adapter = messageAdapter
// logic for addinf dta to rv

        mDbRef.child("chats").child(sendRoom!!).child("message")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnaoshot in snapshot.children){
                        val message = postSnaoshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        // adding the message to database
        sendBtn.setOnClickListener {
            val message = messageBox.text.toString()
            val messageaObject  = Message(message,senderUid)
            mDbRef.child("chats").child(sendRoom!!).child("message").push()
                .setValue(messageaObject).addOnSuccessListener {

                    mDbRef.child("chats").child(receiverRoom!!).child("message").push()
                        .setValue(messageaObject)

                }
            messageBox.setText("")
        }

    }
}