package com.ksatria.hotel

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView

class ProfileActivity : AppCompatActivity() {

    private lateinit var logout: Button
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var number: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        logout = findViewById(R.id.btnLogout)
        name = findViewById(R.id.tvProfileName)
        email = findViewById(R.id.tvProfileEmail)
        number = findViewById(R.id.tvProfileNumber)

        // mengisi nilai vaiabel yang ingin ditampilkan dengan nilai dari preferences
        name.text = Preferences.getLoggedUser(baseContext)
        email.text = Preferences.getRegisteredEmail(baseContext)
        number.text = Preferences.getRegisteredNumber(baseContext)

        logout.setOnClickListener(View.OnClickListener {
            Preferences.setLoggedStatus(baseContext, false)
            startActivity(Intent(baseContext, MainActivity::class.java))
            finish()
        })
    }
}