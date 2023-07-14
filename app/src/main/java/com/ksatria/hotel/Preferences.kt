package com.ksatria.hotel

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object Preferences {
    private const val KEY_USER_REGISTERED = "user"
    private const val KEY_EMAIL_REGISTERED = "email"
    private const val KEY_NUMBER_REGISTERED = "number"
    private const val KEY_PASS_REGISTERED = "pass"
    private const val KEY_USERNAME_LOGGED = "username_logged_in"
    private const val KEY_STATUS_LOGGED = "status_logged_in"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    // mengatur nilai username
    fun setRegisteredUser(context: Context, username: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_USER_REGISTERED, username)
        editor.apply()
    }

    // mendapatkan nilai username
    fun getRegisteredUser(context: Context): String {
        return getSharedPreferences(context).getString(KEY_USER_REGISTERED, "") ?: ""
    }

    // mengatur nilai email
    fun setRegisteredEmail(context: Context, email: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_EMAIL_REGISTERED, email)
        editor.apply()
    }

    // mendapatkan nilai email
    fun getRegisteredEmail(context: Context): String {
        return getSharedPreferences(context).getString(KEY_EMAIL_REGISTERED, "") ?: ""
    }

    // mengatur nilai no telpon
    fun setRegisteredNumber(context: Context, number: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_NUMBER_REGISTERED, number)
        editor.apply()
    }

    // mendapatkan nilai no telpon
    fun getRegisteredNumber(context: Context): String {
        return getSharedPreferences(context).getString(KEY_NUMBER_REGISTERED, "") ?: ""
    }

    // mengatur nilai password
    fun setRegisteredPass(context: Context, password: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_PASS_REGISTERED, password)
        editor.apply()
    }

    // mendapatkan nilai password
    fun getRegisteredPass(context: Context): String {
        return getSharedPreferences(context).getString(KEY_PASS_REGISTERED, "") ?: ""
    }

    // mengatur nilai user yang login
    fun setLoggedUser(context: Context, username: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_USERNAME_LOGGED, username)
        editor.apply()
    }

    // mendapatkan nilai user yang login
    fun getLoggedUser(context: Context): String {
        return getSharedPreferences(context).getString(KEY_USERNAME_LOGGED, "") ?: ""
    }

    // mengatur nilai status login (true/false)
    fun setLoggedStatus(context: Context, status: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_STATUS_LOGGED, status)
        editor.apply()
    }

    // mendapatkan nilai status login
    fun getLoggedStatus(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_STATUS_LOGGED, false)
    }
}
