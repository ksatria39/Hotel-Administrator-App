package com.ksatria.hotel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {

    private lateinit var mViewUser: EditText
    private lateinit var mViewEmail: EditText
    private lateinit var mViewNumber: EditText
    private lateinit var mViewPassword: EditText
    private lateinit var mViewPassword2: EditText
    private lateinit var signIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mViewUser = findViewById(R.id.etUsernameRegister)
        mViewEmail = findViewById(R.id.etEmailRegister)
        mViewNumber = findViewById(R.id.etNumberRegister)
        mViewPassword = findViewById(R.id.etPassRegister)
        mViewPassword2 = findViewById(R.id.etPassRegister2)
        signIn = findViewById(R.id.btnSignIn)

        mViewPassword2.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                check()
                return@OnEditorActionListener true
            }
            false
        })

        signIn.setOnClickListener(View.OnClickListener { check() })
    }

    //Fungsi untuk mengecek apakah semua kolom sudah di isi
    private fun check() {
        mViewUser.error = null
        mViewEmail.error = null
        mViewNumber.error = null
        mViewPassword.error = null
        mViewPassword2.error = null
        var focusView: View? = null
        var cancel = false

        val password2 = mViewPassword2.text.toString()
        val user = mViewUser.text.toString()
        val email = mViewEmail.text.toString()
        val number = mViewNumber.text.toString()
        val password = mViewPassword.text.toString()

        // peringatan jika nama kosong atau sudah digunakan
        if (TextUtils.isEmpty(user)) {
            mViewUser.error = "Nama Pengguna harus diisi"
            focusView = mViewUser
            cancel = true
        } else if (checkUser(user)) {
            mViewUser.error = "Nama Pengguna tidak tersedia"
            focusView = mViewUser
            cancel = true
        }

        // peringatan jika email kosong atau sudah digunakan
        if (TextUtils.isEmpty(email)) {
            mViewEmail.error = "Email harus diisi"
            focusView = mViewEmail
            cancel = true
        } else if (checkUser(email)) {
            mViewEmail.error = "Email sudah digunakan"
            focusView = mViewEmail
            cancel = true
        }

        // peringatan jika no telpon kosong atau sudah digunakan
        if (TextUtils.isEmpty(number)) {
            mViewUser.error = "No Telpon harus diisi"
            focusView = mViewUser
            cancel = true
        } else if (checkUser(number)) {
            mViewNumber.error = "No Telpon sudah digunakan"
            focusView = mViewNumber
            cancel = true
        }

        // perinatan jika kata sandi tidak diisi dan konfirmasi kata sandi tidak cocok
        if (TextUtils.isEmpty(password)) {
            mViewPassword.error = "Kata Sandi harus diisi"
            focusView = mViewPassword
            cancel = true
        } else if (!checkPassword(password, password2)) {
            mViewPassword2.error = "Kata Sandi salah"
            focusView = mViewPassword2
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            // mengatur nilai praferences sesuai inputan user
            Preferences.setRegisteredUser(baseContext, user)
            Preferences.setRegisteredEmail(baseContext, email)
            Preferences.setRegisteredNumber(baseContext, number)
            Preferences.setRegisteredPass(baseContext, password)
            finish()
        }
    }

    //untuk mengecek apakah password yang diisikan kembali sama atau tidak
    private fun checkPassword(password: String, repassword: String): Boolean {
        return password == repassword
    }

    //untuk mengecek apakah nama pengguna sudah digunakan atau tidak
    private fun checkUser(user: String): Boolean {
        return user == Preferences.getRegisteredUser(baseContext)
    }
}
