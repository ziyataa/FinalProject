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
    val position = "position"
    val address = "address"
    val token = "token"
    val isLogin = "isLogin"
    val firstIstall = "firstInstall"

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

    fun setUserToken(auth : String) : String {
        editor?.putString(token,auth)
        editor?.commit()
        return pref!!.getString(token, auth)!!
    }

    fun putFirstInstall(fisrtInstall : Boolean) : Boolean {
        editor?.putBoolean(firstIstall, fisrtInstall)
        editor?.commit()
        return pref!!.getBoolean(firstIstall, fisrtInstall)
    }

    fun setUsername(username : String) {
        editor?.putString(username, pref?.getString(username, ""))
        editor?.commit()
    }

    fun setUserId(idUser : Int) {
        editor?.putInt(id, idUser)
        editor?.commit()
    }

    fun getUserId() : Int? {
        return pref!!.getInt(id, 0 )
    }

    fun getUserToken(): String {
        return pref!!.getString(token, "")!!
    }

    fun checkLogin() : Boolean {
        return pref!!.getBoolean(isLogin, false)
    }


    fun getFirstInstall() : Boolean {
        return pref!!.getBoolean(firstIstall, false)
    }


    fun getUserName() : String {
        return pref!!.getString(username, "")!!
    }

    fun getUserNik() : String {
        return pref!!.getString(nik, "")!!
    }

    fun getUserPosition() : String {
        return pref!!.getString(position, "")!!
    }

    fun getUserAddress() : String {
        return pref!!.getString(address, "")!!
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