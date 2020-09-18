package com.example.shift_lab.View

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shift_lab.R
import kotlinx.android.synthetic.main.activity_sign_up_result.*

class SignUpResultActivity : AppCompatActivity() {

    lateinit var preferences: SharedPreferences
    private var name:String? = ""
    private var lastName:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_result)

        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        name = preferences.getString("NAME", "")
        lastName = preferences.getString("LAST_NAME", "")

        btn_hello.setOnClickListener {
            showWelcomeDialog()
        }
    }

    private fun showWelcomeDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.welcome))
        builder.setMessage("$name $lastName, добро пожаловать!")
        builder.setPositiveButton("OK") { _: DialogInterface, _ ->  }
        builder.show()
    }
}