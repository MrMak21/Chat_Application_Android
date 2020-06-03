package gr.makris.chatapp.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.android.gms.common.util.SharedPreferencesUtils

object SharedPrefsUtils {

    val PREFERENCES = "ChatPrefs"
    val USER_EMAIL = "userEmail"
    val USER_ID = "userId"
    val USER_FULLNAME = "userFullname"
    val USER_FIRSTNAME = "userFirstname"
    val USER_LASTNAME = "userLastname"
    val USER_IMAGE = "userImage"
    val USER_IMAGE_THUMB = "userImageThumb"

    fun getPrefsEditor(app: Context): SharedPreferences.Editor? {
        val prefs = app.getSharedPreferences(PREFERENCES,0)
        return prefs.edit()
    }

    fun getPrefs(app: Context): SharedPreferences? {
        val prefs = app.getSharedPreferences(PREFERENCES,0)
        return prefs
    }
}