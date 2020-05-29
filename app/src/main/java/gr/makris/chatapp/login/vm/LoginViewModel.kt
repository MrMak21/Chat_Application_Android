package gr.makris.chatapp.login.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gr.makris.chatapp.data.User
import gr.makris.chatapp.result.Result
import gr.makris.chatapp.utils.SharedPrefsUtils

class LoginViewModel(app: Application): AndroidViewModel(app),ILoginViewModel,LoginModelCallback {


    private var mAuth =  FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance()
    private var ref = db.getReference("Users")
    private val TAG = "LoginViewModel"
    private val app: Application = app



    override var signInObserver: MutableLiveData<Boolean> = MutableLiveData<Boolean>()



    override fun doLogin(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email,pass)

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG,"Login success - User: " + mAuth.currentUser!!.email)
                    signInObserver.value = true
                    getUserByEmail(email)
                } else {
                    Log.d(TAG,"Login failed")
                    signInObserver.value = false
                }
            }
    }

    override fun checkIfUserSignIn(): Boolean {
        var user = mAuth.currentUser
        if (user == null)
            return false

        user.email?.let { getUserByEmail(it) }
        return true
    }


    override fun getUserByEmail(email: String) {
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                for (u in p0.children) {
                    var user = u.getValue(User::class.java)
                    if (user!!.email.equals(email)) {
                        getUserByEmailCallback(user)
                    }
                }
            }
        })
    }

    override fun getUserByEmailCallback(user: User) {

        writeUserCredentialsTOPreferences(user)
    }

    override fun writeUserCredentialsTOPreferences(user: User) {
        //Here write user details to shared preferences
        Log.d(TAG,"Writing user credentials to shared preferences")
        val editor = SharedPrefsUtils.getPrefsEditor(app)
        editor?.putString(SharedPrefsUtils.USER_EMAIL,user.email)
        editor?.putString(SharedPrefsUtils.USER_ID,user.id)
        editor?.commit()
    }



}