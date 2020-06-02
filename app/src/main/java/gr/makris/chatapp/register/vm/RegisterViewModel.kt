package gr.makris.chatapp.register.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gr.makris.chatapp.data.User
import gr.makris.chatapp.result.*


class RegisterViewModel(application: Application): AndroidViewModel(application),IRegisterViewModel {

    private val mAuth = FirebaseAuth.getInstance()
    private val TAG = "RegisterViewModel"
    private val app: Application = application

    private var db = FirebaseDatabase.getInstance()
    private var usersRef = db.getReference("Users")

    override var registerObserver: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    override var registerResult: MutableLiveData<Result<User>> = MutableLiveData()

    override fun doRegister(user: User,password: String) {
        mAuth.createUserWithEmailAndPassword(user.email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"User created")
                   writeUserToDatabase(user)
                    registerResult.value = Result.success(user)
                } else {
                    Log.d(TAG,task.exception?.message)
                    registerResult.value = Result.failure(task.exception as Throwable)
                }
            }
    }

    private fun writeUserToDatabase(user: User) {
        usersRef.child(user.id).setValue(user)
    }


}