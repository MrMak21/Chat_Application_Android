package gr.makris.chatapp.main.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gr.makris.chatapp.data.User
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(app: Application): AndroidViewModel(app),IMainViewModel {

    private val TAG = "MainViewModel"
    private var db = FirebaseDatabase.getInstance()
    private var ref = db.getReference("Users")

    override var namesListObserver: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()
    override var mainScreenCommand: MutableLiveData<User> = MutableLiveData()

    override fun send() {

        var user = User(UUID.randomUUID().toString(),"Stavros","Megremis","meg@gmail.com")
        ref.child(user.id).setValue(user)
    }

    override fun getUsers() {
        var list = arrayListOf<User>()
        namesListObserver.value = list

        ref.addValueEventListener(object: ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                for (i in p0.children) {
                    var user = i.getValue(User::class.java)!!
                    list.add(user)
                }

                namesListObserver.value = list
            }
        })

    }

    override fun itemClicked(user: User) {
        Log.d(TAG,user.email)
        mainScreenCommand.value = user
    }


}