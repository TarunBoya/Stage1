package com.tarun.stage1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.tarun.stage1.Models.UserDataModel

class LoginActivity : AppCompatActivity() {

    lateinit var bt_login: Button
    lateinit var bt_signup: Button
    lateinit var email: EditText
    lateinit var pass: EditText
    private lateinit var database:FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email=findViewById(R.id.email_id)
        pass=findViewById(R.id.pass)
        database= FirebaseDatabase.getInstance("https://stage1-9e304-default-rtdb.firebaseio.com/")
        reference=database.getReference("Users/Credentials")
        bt_login = findViewById(R.id.login_bt)
        bt_signup =findViewById(R.id.sign_up)

        bt_signup.setOnClickListener(View.OnClickListener {
            startActivity(Intent(applicationContext, SignupActivity::class.java))
        })
        bt_login.setOnClickListener(View.OnClickListener {
            login()
        })
    }

    private fun login(){
        var email = email.text.toString().trim()
        var password = pass.text.toString().trim()
        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Fill the Fields",Toast.LENGTH_SHORT).show()
        }else{
            isEmailExists(email, password)
        }
    }

    private fun isEmailExists(email: String, password: String) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var list = ArrayList<UserDataModel>()
                var isEmailExist = false
                for (postsnapshot in dataSnapshot.children) {
                    var value = postsnapshot.getValue() as Map<*, *>
                    if (value["email"].toString() == email && value["password"].toString() == password){
                        isEmailExist=true
                    }
                }
                if (isEmailExist){
                    Toast.makeText(applicationContext, "Logged In Succefully",Toast.LENGTH_LONG).show()
                    startActivity(Intent(applicationContext,HomeActivity::class.java ))
                }else{
                    Toast.makeText(applicationContext,"Login Failed Please check your credentials",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Toast.makeText(applicationContext, "Failed to check",Toast.LENGTH_LONG).show()
            }
        })
    }
}