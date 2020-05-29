package gr.makris.chatapp.chat.vm

import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.User

interface IChatViewModel {

     var messagesListObserver: MutableLiveData<ArrayList<User>>

    fun setMessageHistory()
    fun sendMessageToServer(receipientUser: User)
}