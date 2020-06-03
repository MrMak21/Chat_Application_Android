package gr.makris.chatapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import gr.makris.chatapp.main.MainActivity
import gr.makris.chatapp.R
import gr.makris.chatapp.login.vm.LoginViewModel
import gr.makris.chatapp.register.RegisterScreen

class LoginScreen : AppCompatActivity() {

    lateinit var mEmail: TextInputLayout
    lateinit var mPassword: TextInputLayout
    lateinit var loginButton: Button
    lateinit var registerBtn: TextView
    private lateinit var vm: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)

        vm = ViewModelProvider(this).get(LoginViewModel::class.java)


        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBindings() {
        mEmail = findViewById(R.id.loginEmail)
        mPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        registerBtn = findViewById(R.id.loginRegisterIndicator)
    }

    private fun setUpObservers() {
        //Return the response from signIn

        vm.loginResult.observe(this, Observer { it ->
            if (it.hasError) {
                Toast.makeText(this,it.errorMessage,Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun setUpListeners() {
        loginButton.setOnClickListener { it ->
            vm.doLogin(mEmail.editText!!.text.toString(),mPassword.editText!!.text.toString())
        }

        registerBtn.setOnClickListener { it ->
            val intent = Intent(this, RegisterScreen::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()

        if (vm.checkIfUserSignIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
