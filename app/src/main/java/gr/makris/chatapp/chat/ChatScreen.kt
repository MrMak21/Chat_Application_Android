package gr.makris.chatapp.chat

import android.graphics.Color
import android.media.tv.TvContract
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
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

    private lateinit var chatInput: EditText
    private lateinit var guestUserData: User
    private lateinit var sendButton: ImageButton

    private lateinit var adapter: MessageAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var mProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        vm = ViewModelProvider(this).get(ChatViewModel::class.java)

        guestUserData = intent.getParcelableExtra<User>("user")
        Log.d(TAG, guestUserData.email)

        setupActionBar()
        setUpBindings()
        setUpListeners()
        setUpObservers()

        //Show the past messages
        vm.setMessageHistory()
        mProgress.visibility = View.VISIBLE
    }

    private fun setupActionBar() {
        title = "${guestUserData.firstname} ${guestUserData.lastname}"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpBindings() {
        chatInput = findViewById(R.id.chatInput)
        sendButton = findViewById(R.id.sendMessageBtn)
        mProgress = findViewById(R.id.chat_progress_bar)

        val prefs = SharedPrefsUtils.getPrefs(this)
        vm.userId = prefs?.getString(SharedPrefsUtils.USER_ID, null)
        vm.guestId = guestUserData.id

        val editor = prefs?.edit()
        editor?.putString(SharedPrefsUtils.CHAT_RECIPIENT_IMAGE,guestUserData.imageThumb)
        editor?.apply()


        recycler = findViewById(R.id.chatRecyler)
        recycler.layoutManager = LinearLayoutManager(this)

        adapter = MessageAdapter(this)
        recycler.adapter = adapter
        adapter.setOnItemClickListener { /*vm.itemClicked(it)*/ }


    }

    private fun setUpListeners() {
        sendButton.setOnClickListener { it ->
            if (chatInput.text.toString().isNotEmpty()) {
                vm.sendMessageToServer(chatInput.text.toString(), guestUserData)
                chatInput.setText("")
            }
        }
    }

    private fun setUpObservers() {
        vm.messagesListObserver.observe(this, Observer { it ->
            Log.d(TAG, it.toString())
            it.sortBy { it.timestamp }
            adapter.setData(it)
            recycler.scrollToPosition(it.size - 1)
            mProgress.visibility = View.GONE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

