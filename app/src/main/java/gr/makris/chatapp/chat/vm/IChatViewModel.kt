package gr.makris.chatapp.chat.vm

import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.data.Message
import gr.makris.chatapp.data.User

interface IChatViewModel {

     var messagesListObserver: MutableLiveData<ArrayList<Message>>
    var userId: String?
    var guestId: String?

    fun setMessageHistory()
    fun sendMessageToServer(msg: String, recipientUser: User)
}