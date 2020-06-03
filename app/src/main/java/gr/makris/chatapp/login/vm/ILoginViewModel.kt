package gr.makris.chatapp.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.User
import gr.makris.chatapp.result.Result

interface ILoginViewModel {


    var loginResult: MutableLiveData<Result<Unit>>

    fun doLogin(email: String, pass: String)
    fun checkIfUserSignIn(): Boolean
    fun getUserByEmail(email: String)
    fun writeUserCredentialsTOPreferences(user: User)
}