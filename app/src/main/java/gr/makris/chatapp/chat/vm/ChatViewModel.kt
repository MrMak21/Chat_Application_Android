package gr.makris.chatapp.chat.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import gr.makris.chatapp.data.Message
import gr.makris.chatapp.data.User
import java.util.*

class ChatViewModel(app: Application): AndroidViewModel(app),IChatViewModel {

    private val TAG = "ChatViewModel"
    private var db = FirebaseDatabase.getInstance()
    private var ref = db.getReference("Messages")

    override var messagesListObserver: MutableLiveData<ArrayList<User>> = MutableLiveData<ArrayList<User>>()

    override fun setMessageHistory() {

    }

    override fun sendMessageToServer(receipientUser: User) {
        val chat_id = "b972119a-891c-4542-a211-b78d129bd5f6_" + receipientUser.id
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var msg = Message(UUID.randomUUID().toString(),"b972119a-891c-4542-a211-b78d129bd5f6",receipientUser.id,"TEXT","First message on firebase",chat_id,ts)

        ref.child(chat_id).child(msg.msgId).setValue(msg)
    }


}