package gr.makris.chatapp.info

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import gr.makris.chatapp.R
import gr.makris.chatapp.info.vm.InfoViewModel
import gr.makris.chatapp.login.LoginScreen
import gr.makris.chatapp.main.vm.MainViewModel
import gr.makris.chatapp.utils.SharedPrefsUtils
import pl.aprilapps.easyphotopicker.*

class InfoScreen : AppCompatActivity() {

    private val TAG = "InfoScreen"
    private val CAMERA_REQUEST = 100
    private lateinit var vm: InfoViewModel

    private lateinit var logoutBtn: TextView
    private lateinit var userFullname: TextView
    private lateinit var image: ImageView
    private var prefs: SharedPreferences? = null
    private lateinit var easyImage: EasyImage


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        vm = ViewModelProvider(this).get(InfoViewModel::class.java)
        prefs = SharedPrefsUtils.getPrefs(this)
        easyImage = EasyImage.Builder(this)
            .setChooserTitle("Pick image")
            .allowMultiple(false)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .build()

        checkForPermissions()
        setUpActionBar()
        setUpBindings()
        setUpListeners()
        setUpObservers()

        setProfilePicture()
    }

    private fun setProfilePicture() {
        val img = prefs?.getString(SharedPrefsUtils.USER_IMAGE_THUMB,"")
        val uri = Uri.parse(img)

        if (uri != null) {
//            image.setImageURI(uri)
            Glide.with(this)
                .load(uri)
                .into(image)
        }
    }


    private fun setUpActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpBindings() {
        logoutBtn = findViewById(R.id.info_logout)
        userFullname = findViewById(R.id.info_name)
        image = findViewById(R.id.info_image)

        userFullname.text =  prefs?.getString(SharedPrefsUtils.USER_FULLNAME, null)

    }

    private fun setUpListeners() {
        logoutBtn.setOnClickListener { it ->
            vm.logOut()
        }

        image.setOnClickListener { it ->
            easyImage.openChooser(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        easyImage.handleActivityResult(requestCode,resultCode,data,this,object: DefaultCallback() {
            override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                image.setImageURI(Uri.fromFile(imageFiles[0].file))
                vm.uploadFileToServer(imageFiles[0].file)
            }
        })
    }

    private fun checkForPermissions() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),CAMERA_REQUEST)
        }
    }
}
