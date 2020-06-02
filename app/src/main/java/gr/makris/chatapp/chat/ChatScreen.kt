package gr.makris.chatapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.adapter.MessageAdapter
import gr.makris.chatapp.chat.vm.ChatViewModel
import gr.makris.chatapp.data.User
import gr.makris.chatapp.main.adapter.NamesListAdapter
import gr.makris.chatapp.main.vm.MainViewModel
import gr.makris.chatapp.utils.SharedPrefsUtils
import kotlinx.android.synthetic.main.activity_chat_screen.*

class ChatScreen : AppCompatActivity() {

    private val TAG = "ChatScreen"
    private lateinit var vm: ChatViewModel

    private lateinit var recipientName: TextView
    private lateinit var chatInput: EditText
    private lateinit var guestUserData: User
    private lateinit var sendButton: ImageButton

    private lateinit var adapter: MessageAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        vm = ViewModelProvider(this).get(ChatViewModel::class.java)

        guestUserData = intent.getParcelableExtra<User>("user")
        Log.d(TAG,guestUserData.email)

        setUpBindings()
        setUpListeners()
        setUpObservers()

        //Show the past messages
        vm.setMessageHistory()
    }

    private fun setUpBindings() {
        recipientName = findViewById(R.id.chatName)
        chatInput = findViewById(R.id.chatInput)
        recipientName.text = ("${guestUserData.firstname} ${guestUserData.lastname}")
        sendButton = findViewById(R.id.sendMessageBtn)

        val prefs = SharedPrefsUtils.getPrefs(this)
        vm.userId = prefs?.getString(SharedPrefsUtils.USER_ID,null)
        vm.guestId = guestUserData.id


        recycler = findViewById(R.id.chatRecyler)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = MessageAdapter(this)
        recycler.adapter = adapter
        adapter.setOnItemClickListener { /*vm.itemClicked(it)*/ }


    }

    private fun setUpListeners() {
        sendButton.setOnClickListener { it ->
            if (chatInput.text.toString().isNotEmpty()) {
                vm.sendMessageToServer(chatInput.text.toString(),guestUserData)
                chatInput.setText("")
            }
        }
    }

    private fun setUpObservers() {
        vm.messagesListObserver.observe(this, Observer { it ->
            Log.d(TAG,it.toString())
            it.sortBy { it.timestamp }
            adapter.setData(it)
            recycler.scrollToPosition(it.size - 1)
        })
    }

}

