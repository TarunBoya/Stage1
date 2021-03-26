package com.tarun.stage1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tarun.stage1.Models.UserDataModel
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var bt_signUp: Button
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        name = findViewById(R.id.name_signup)
        email = findViewById(R.id.email_signup)
        password = findViewById(R.id.password_signup)
        bt_signUp = findViewById(R.id.signup_bt)
        database = FirebaseDatabase.getInstance("https://stage1-9e304-default-rtdb.firebaseio.com/")
        databaseReference = database.getReference("Users/Credentials")

        bt_signUp.setOnClickListener(View.OnClickListener {
            signUp()
        })
    }

    private fun signUp() {
        val name = name.text.toString().trim()
        val email = email.text.toString().trim()
        val password = password.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){

        } else{
            val pattern=Patterns.EMAIL_ADDRESS
            if (pattern.matcher(email).matches()) {
                val id = databaseReference.push().key
                val model = UserDataModel(name, email, password, id!!)

                databaseReference.child(id).setValue(model)
                Toast.makeText(applicationContext, "Account Created", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext,HomeActivity::class.java))
                finish()
            }
        }
    }
}