package gr.makris.chatapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.InflateException
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import gr.makris.chatapp.R
import gr.makris.chatapp.chat.ChatScreen
import gr.makris.chatapp.info.InfoScreen
import gr.makris.chatapp.login.LoginScreen
import gr.makris.chatapp.login.vm.LoginViewModel
import gr.makris.chatapp.main.adapter.NamesListAdapter
import gr.makris.chatapp.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var vm: MainViewModel

    private lateinit var logoutBtn: Button
    private lateinit var mRecycler: RecyclerView
    private lateinit var adapter: NamesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.getUsers()


        setUpBindings()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpBindings() {
        logoutBtn = findViewById(R.id.logoutBtn)
        mRecycler = findViewById(R.id.mainRecycler)
        mRecycler.layoutManager = LinearLayoutManager(this)

        adapter = NamesListAdapter(this)
        mRecycler.adapter = adapter
        adapter.setOnItemClickListener { vm.itemClicked(it) }
    }

    private fun setUpListeners() {

    }

    private fun setUpObservers() {

        vm.namesListObserver.observe(this, Observer { it ->
            Log.d(TAG,it.toString())
            adapter.setData(it)
        })

        vm.mainScreenCommand.observe(this, Observer { it ->
            val intent = Intent(this,ChatScreen::class.java)
            intent.putExtra("user",it)
            startActivity(intent)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
                R.id.menu_info -> {
                val intent = Intent(this, InfoScreen::class.java)
                    startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
