package gr.makris.chatapp.chat.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import gr.makris.chatapp.data.Message
import gr.makris.chatapp.data.User
import java.util.*

class ChatViewModel(app: Application): AndroidViewModel(app),IChatViewModel {

    private val TAG = "ChatViewModel"
    private var db = FirebaseDatabase.getInstance()
    private var ref = db.getReference("Messages")

    override var guestId: String? = null
    override var userId: String? = null

    override var messagesListObserver: MutableLiveData<ArrayList<Message>> = MutableLiveData<ArrayList<Message>>()

    override fun setMessageHistory() {
        val list = arrayListOf<Message>()
        messagesListObserver.value = list

        ref.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                for (chat in p0.children) {
                    val chatId1 = "${userId}_${guestId}"
                    val chatId2 = "${guestId}_${userId}"
                    if (chat.key.equals(chatId1) || chat.key.equals(chatId2)) {
                        for (msg in chat.children) {
                            val message = msg.getValue(Message::class.java)
                            message?.let { list.add(it) }
                        }
                    }
                }
                //notify chatScreen that has new messages
                messagesListObserver.value = list
            }
        })
    }

    override fun sendMessageToServer(msg: String, recipientUser: User) {
        var chat_id = ""
        if (userId!! > recipientUser.id) {
            chat_id = "${userId}_${recipientUser.id}"
        } else {
            chat_id = "${recipientUser.id}_${userId}"
        }
        val tsLong = System.currentTimeMillis() / 1000
        val ts = tsLong.toString()
        var message = Message(UUID.randomUUID().toString(),userId,recipientUser.id,"TEXT",msg,chat_id,ts)

        ref.child(chat_id).child(message.msgId).setValue(message)
    }

    override fun onCleared() {
        super.onCleared()
        guestId = null
        userId = null
    }


}