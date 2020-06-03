package gr.makris.chatapp.info.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import gr.makris.chatapp.result.Result

class InfoViewModel(application: Application): AndroidViewModel(application),IInfoViewModel {

    private val TAG = "MainViewModel"
    private val mAuth = FirebaseAuth.getInstance()

    override var logoutObserver: MutableLiveData<Result<Unit>> = MutableLiveData()

    override fun logOut() {
        mAuth.signOut()
        logoutObserver.value = Result.success()
    }
}