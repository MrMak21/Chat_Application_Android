package gr.makris.chatapp.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.User
import java.util.ArrayList
import gr.makris.chatapp.result.*

interface IMainViewModel {

    var namesListObserver: MutableLiveData<ArrayList<User>>
    var mainScreenCommand: MutableLiveData<User>
    var logoutObserver: MutableLiveData<Result<Unit>>


    fun getUsers()
    fun itemClicked(user: User)
    fun logOut()
}