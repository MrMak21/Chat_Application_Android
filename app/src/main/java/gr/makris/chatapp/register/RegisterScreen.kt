package gr.makris.chatapp.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.vm.ChatViewModel
import gr.makris.chatapp.login.LoginScreen
import gr.makris.chatapp.main.MainActivity
import gr.makris.chatapp.register.vm.RegisterViewModel

class RegisterScreen : AppCompatActivity() {

    private val TAG = "RegisterScreen"
    private lateinit var vm: RegisterViewModel

    private lateinit var loginBtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_screen)

        vm = ViewModelProvider(this).get(RegisterViewModel::class.java)

        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBindings() {
        loginBtn = findViewById(R.id.registerLoginIndicator)
    }

    private fun setUpListeners() {
        loginBtn.setOnClickListener { it ->
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun setUpObservers() {

    }
}
