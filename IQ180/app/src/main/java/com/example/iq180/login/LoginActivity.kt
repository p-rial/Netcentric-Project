package com.example.iq180.login

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.iq180.MainActivity
import com.example.iq180.R
import com.example.iq180.SocketApplication
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this@LoginActivity, MainActivity::class.java)

        loginBtn.setOnClickListener({
            if (loginEdt.text.isEmpty()) {
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("Can't log in")
                        .setMessage("Please fill your name and IP address")
                        .setCancelable(true)
                        .setPositiveButton("OK", { dialog, which ->
                            dialog.dismiss()
                        })
                alertDialog.show()

            } else {
                intent.putExtra("name", loginEdt.text.toString())

                startActivity(intent)
            }
        })
    }
}
