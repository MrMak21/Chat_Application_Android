package gr.makris.chatapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.vm.ChatViewModel
import gr.makris.chatapp.data.User
import gr.makris.chatapp.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_chat_screen.*

class ChatScreen : AppCompatActivity() {

    private val TAG = "ChatScreen"
    private lateinit var vm: ChatViewModel

    private lateinit var receipientName: TextView
    private lateinit var chatInput: EditText
    private lateinit var userData: User
    private lateinit var testButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        vm = ViewModelProvider(this).get(ChatViewModel::class.java)

        userData = intent.getParcelableExtra<User>("user")
        Log.d(TAG,userData.email)

        //Show the past messages
        vm.setMessageHistory()

        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBindings() {
        receipientName = findViewById(R.id.chatName)
        chatInput = findViewById(R.id.chatInput)
        receipientName.text = ("${userData.firstname} ${userData.lastname}")
        testButton = findViewById(R.id.testButton)
    }

    private fun setUpListeners() {
        testButton.setOnClickListener { it ->
            vm.sendMessageToServer(userData)
        }
    }

    private fun setUpObservers() {

    }
}
