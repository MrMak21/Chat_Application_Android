package gr.makris.chatapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import gr.makris.chatapp.main.MainActivity
import gr.makris.chatapp.R
import gr.makris.chatapp.login.vm.LoginViewModel

class LoginScreen : AppCompatActivity() {

    lateinit var mEmail: TextInputLayout
    lateinit var mPassword: TextInputLayout
    lateinit var loginButton: Button
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
    }

    private fun setUpObservers() {
        //Return the response from signIn
        vm.signInObserver.observe(this, Observer { it ->
            loginResponse(it)
        })
    }

    private fun setUpListeners() {
        loginButton.setOnClickListener { it ->
            vm.doLogin(mEmail.editText!!.text.toString(),mPassword.editText!!.text.toString())
        }
    }

    private fun loginResponse(success: Boolean) {
        if (success) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        } else {
            //Cannot login
            Toast.makeText(this,"Cannot connect",Toast.LENGTH_SHORT).show()
        }
    }
}
