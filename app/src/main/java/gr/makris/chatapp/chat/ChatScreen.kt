package gr.makris.chatapp.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.vm.ChatViewModel
import gr.makris.chatapp.data.User
import gr.makris.chatapp.main.vm.MainViewModel

class ChatScreen : AppCompatActivity() {

    private val TAG = "ChatScreen"
    private lateinit var vm: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        vm = ViewModelProvider(this).get(ChatViewModel::class.java)

        val user = intent.getParcelableExtra<User>("user")
        Log.d(TAG,user.email)

        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBindings() {



    }

    private fun setUpListeners() {

    }

    private fun setUpObservers() {

    }
}
