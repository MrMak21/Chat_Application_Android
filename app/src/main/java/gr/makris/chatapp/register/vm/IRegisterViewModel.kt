package gr.makris.chatapp.register.vm

import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.User
import gr.makris.chatapp.result.*

interface IRegisterViewModel {

    var registerObserver: MutableLiveData<Boolean>
    var registerResult: MutableLiveData<Result<User>>
    fun doRegister(user: User,password: String)
}