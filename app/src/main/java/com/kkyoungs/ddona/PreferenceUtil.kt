package com.kkyoungs.ddona

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefsFilename = "prefs"
    private val prefsKeyEdt = "myEditText"
    private val prefsKeyNick = "myCharNick"
    private val prefsKeyInfo = "myCharInfo"
    private val prefsKeyImg = "myCharImg"
    private val prefsKeySend = "send"
    private val prefsKeyReceive = "Receive"
    private val prefsKeyToken = "token"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var myEditText: String?
        get() = prefs.getString(prefsKeyEdt, "")
        set(value) = prefs.edit().putString(prefsKeyEdt, value).apply()


    var myCharImg: String?
        get() = prefs.getString(prefsKeyImg, "")
        set(value) = prefs.edit().putString(prefsKeyImg, value).apply()


    var myCharNick: String?
        get() = prefs.getString(prefsKeyNick, "")
        set(value) = prefs.edit().putString(prefsKeyNick, value).apply()


    var myCharInfo: String?
        get() = prefs.getString(prefsKeyInfo, "")
        set(value) = prefs.edit().putString(prefsKeyInfo, value).apply()

    var send : String?
        get() = prefs.getString(prefsKeySend, "")
        set(value) = prefs.edit().putString(prefsKeySend, value).apply()

    var receive : String?
        get() = prefs.getString(prefsKeyReceive, "")
        set(value) = prefs.edit().putString(prefsKeyReceive, value).apply()

    var token : String?
        get() = prefs.getString(prefsKeyToken, "")
        set(value) = prefs.edit().putString(prefsKeyToken, value).apply()
}
