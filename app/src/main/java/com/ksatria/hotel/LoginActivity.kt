package com.ksatria.hotel

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private var mViewUser: EditText? = null
    private var mViewPassword: EditText? = null
    private var login: Button? = null
    private var register: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mViewUser = findViewById(R.id.etUsernameLogin)
        mViewPassword = findViewById(R.id.etPassLogin)
        login = findViewById(R.id.btnLogin)
        register = findViewById(R.id.btnRegister)

        mViewPassword?.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                checkCredentials()
                return@OnEditorActionListener true
            }
            false
        })

        login?.setOnClickListener(View.OnClickListener { checkCredentials() })

        register?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        })
    }

    private fun checkCredentials() {
        mViewUser?.error = null
        mViewPassword?.error = null
        var focusView: View? = null
        var cancel = false

        val user = mViewUser?.text.toString()
        val password = mViewPassword?.text.toString()

        // cek username, akan memberikan peringatan jika kosong atau tidak terdaftar
        if (TextUtils.isEmpty(user)) {
            mViewUser?.error = "Nama Pengguna harus di isi"
            focusView = mViewUser
            cancel = true
        } else if (!checkUser(user)) {
            mViewUser?.error = "Nama Pengguna salah"
            focusView = mViewUser
            cancel = true
        }

        // cek password, akan memberikan peringatan jika kosong atau tidak terdaftar
        if (TextUtils.isEmpty(password)) {
            mViewPassword?.error = "Kata Sandi harus di isi"
            focusView = mViewPassword
            cancel = true
        } else if (!checkPassword(password)) {
            mViewPassword?.error = "Kata Sandi salah"
            focusView = mViewPassword
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            performLogin()
        }
    }

    private fun performLogin() {
        // megatur nilai dan satatus bahwa sudah masuk
        Preferences.setLoggedUser(baseContext, Preferences.getRegisteredUser(baseContext))
        Preferences.setLoggedStatus(baseContext, true)
        startActivity(Intent(baseContext, MainActivity::class.java))
        finish()
    }

    // fungsi pencocokan pasword yang diisikan dengan yang terdaftar
    private fun checkPassword(password: String): Boolean {
        return password == Preferences.getRegisteredPass(baseContext)
    }

    // fungsi pencocokan username yang diisikan dengan yang terdaftar
    private fun checkUser(user: String): Boolean {
        return user == Preferences.getRegisteredUser(baseContext)
    }
}
