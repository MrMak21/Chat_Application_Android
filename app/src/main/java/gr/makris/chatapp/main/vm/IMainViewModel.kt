package gr.makris.chatapp.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.User
import java.util.ArrayList
import gr.makris.chatapp.result.*

interface IMainViewModel {

    var namesListObserver: MutableLiveData<ArrayList<User>>
    var mainScreenCommand: MutableLiveData<User>



    fun getUsers()
//    fun getLastMessages()
    fun itemClicked(user: User)

}