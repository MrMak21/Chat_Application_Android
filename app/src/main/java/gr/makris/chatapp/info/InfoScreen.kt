package gr.makris.chatapp.info

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import gr.makris.chatapp.R
import gr.makris.chatapp.info.vm.InfoViewModel
import gr.makris.chatapp.login.LoginScreen
import gr.makris.chatapp.main.vm.MainViewModel
import gr.makris.chatapp.utils.SharedPrefsUtils

class InfoScreen : AppCompatActivity() {

    private val TAG = "InfoScreen"
    private lateinit var vm: InfoViewModel

    private lateinit var logoutBtn: TextView
    private lateinit var userFullname: TextView
    private var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        vm = ViewModelProvider(this).get(InfoViewModel::class.java)
        prefs = SharedPrefsUtils.getPrefs(this)

        setUpActionBar()
        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpBindings() {
        logoutBtn = findViewById(R.id.info_logout)
        userFullname = findViewById(R.id.info_name)

        userFullname.text =  prefs?.getString(SharedPrefsUtils.USER_FULLNAME, null)

    }

    private fun setUpListeners() {
        logoutBtn.setOnClickListener { it ->
            vm.logOut()
        }
    }

    private fun setUpObservers() {
        vm.logoutObserver.observe(this, Observer { it ->
            if (!it.hasError) {
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
                finish()
            }
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
