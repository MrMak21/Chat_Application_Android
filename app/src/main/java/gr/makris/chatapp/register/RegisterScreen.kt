package gr.makris.chatapp.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.vm.ChatViewModel
import gr.makris.chatapp.data.User
import gr.makris.chatapp.login.LoginScreen
import gr.makris.chatapp.main.MainActivity
import gr.makris.chatapp.register.vm.RegisterViewModel
import java.util.*

class RegisterScreen : AppCompatActivity() {

    private val TAG = "RegisterScreen"
    private lateinit var vm: RegisterViewModel

    private lateinit var loginBtn: TextView
    private lateinit var firstNameInput: TextInputLayout
    private lateinit var lastNameInput: TextInputLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var password1: TextInputLayout
    private lateinit var password2: TextInputLayout
    private lateinit var registerBtn: Button

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
        firstNameInput = findViewById(R.id.registerFirstName)
        lastNameInput = findViewById(R.id.registerLastname)
        emailInput = findViewById(R.id.registerEmail)
        password1 = findViewById(R.id.registerPassword)
        password2 = findViewById(R.id.registerPassword2)
        registerBtn = findViewById(R.id.registerButton)
    }

    private fun setUpListeners() {
        loginBtn.setOnClickListener { it ->
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener { it ->
            doRegister()
        }
    }

    private fun setUpObservers() {
        vm.registerResult.observe(this, Observer { it ->
            if (!it.hasError) {
                val intent = Intent(this,LoginScreen::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this,it.errorMessage,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun doRegister() {
        if (!firstNameInput.editText?.text.isNullOrEmpty()
            && !lastNameInput.editText?.text.isNullOrEmpty()
            && !emailInput.editText?.text.isNullOrEmpty()
            && !password1.editText?.text.isNullOrEmpty()
            && !password2.editText?.text.isNullOrEmpty()) {

            if (password1.editText?.text.toString().equals(password2.editText?.text.toString())) {
                val user = User(UUID.randomUUID().toString()
                    ,firstNameInput.editText?.text.toString()
                    ,lastNameInput.editText?.text.toString()
                    ,emailInput.editText?.text.toString()
                    ,""
                    ,"")
                vm.doRegister(user,password1.editText?.text.toString())
            } else {
                Toast.makeText(this,"Passwords does not match",Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this,"Please fill in all the fields to continue",Toast.LENGTH_SHORT).show()
        }
    }
}
