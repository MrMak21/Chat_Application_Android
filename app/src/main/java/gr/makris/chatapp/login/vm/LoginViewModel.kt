package gr.makris.chatapp.login.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gr.makris.chatapp.result.Result

class LoginViewModel(app: Application): AndroidViewModel(app),ILoginViewModel {

    private var mAuth =  FirebaseAuth.getInstance()
    private val TAG = "LoginViewModel"


    override var signInObserver: MutableLiveData<Boolean> = MutableLiveData<Boolean>()



    override fun doLogin(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email,pass)

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"Login success - User: " + mAuth.currentUser!!.email)
                    signInObserver.value = true
                } else {
                    Log.d(TAG,"Login failed")
                    signInObserver.value = false
                }
            }
    }




}