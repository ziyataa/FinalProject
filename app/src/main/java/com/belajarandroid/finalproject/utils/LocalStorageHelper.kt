package com.belajarandroid.finalproject.utils

import android.content.Context
import android.content.SharedPreferences

class LocalStorageHelper {

    private var pref: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var context: Context? = null

    private var privadeMode = 0
    private val prefName = "FinalProject"

    constructor(_context: Context) {
        this.context = _context
        pref = context?.getSharedPreferences(prefName, privadeMode)
        editor = pref!!.edit()
    }

    val id = "id"
    val username = "username"
    val fullname = "fullname"
    val nik = "nik"
    val token = "token"
    val isLogin = "isLogin"

    fun createLogin(
        _id: String?,
        _username: String?,
        _fullname: String?,
        _nik: String?,
        _token: String?
    ) {
        editor?.putString(id, _id)
        editor?.putString(username, _username)
        editor?.putString(fullname, _fullname)
        editor?.putString(nik, _nik)
        editor?.putString(token, _token)
        editor?.putBoolean(isLogin, true)
        editor?.commit()
    }

    fun setUserToken(token : String) {
        editor?.putString(token, pref?.getString(token, ""))
        editor?.commit()
    }


    fun getUserToken(): String {
        return pref!!.getString(token, "")!!
    }

    fun checkLogin() : Boolean {
        return pref!!.getBoolean(isLogin, false)
    }

    fun getUserId() : String? {
        return pref!!.getString(id, "")
    }

    fun getUserName() : String {
        return pref!!.getString(username, "")!!
    }

    fun getFullName() : String {
        return pref!!.getString(fullname, "")!!
    }


    fun logout() {
        editor?.remove(username)
        editor?.remove(fullname)
        editor?.remove(nik)
        editor?.remove(isLogin)
        editor?.remove(token)
        editor?.commit()
    }
}