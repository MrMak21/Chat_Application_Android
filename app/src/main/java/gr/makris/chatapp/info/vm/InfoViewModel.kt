package gr.makris.chatapp.info.vm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import gr.makris.chatapp.result.Result
import gr.makris.chatapp.utils.SharedPrefsUtils
import java.io.ByteArrayOutputStream
import java.io.File


class InfoViewModel(application: Application): AndroidViewModel(application),IInfoViewModel {

    private val TAG = "MainViewModel"
    private val app = application
    private var prefs: SharedPreferences? = SharedPrefsUtils.getPrefs(app)
    private val mAuth = FirebaseAuth.getInstance()
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private val imagesRef = mStorageRef.child("userImages/")
    private val imagesThumbRef = mStorageRef.child("userImagesThumb/")
    private val THUMBSIZE = 150

    private var db = FirebaseDatabase.getInstance()
    private var usersRef = db.getReference("Users")

    override var logoutObserver: MutableLiveData<Result<Unit>> = MutableLiveData()

    override fun logOut() {
        mAuth.signOut()
        logoutObserver.value = Result.success()
    }

    override fun uploadFileToServer(file: File): Result<Unit> {
        try {
            val fileType = file.name.substringAfterLast(".")
            val fullName = prefs?.getString(SharedPrefsUtils.USER_FULLNAME,file.name)
            val filename = "${fullName}.${fileType}"

            val uploadTask = imagesRef.child(filename).putFile(Uri.fromFile(file))

            uploadTask.addOnFailureListener { exc ->
                Log.d(TAG,exc.message)
            }.addOnSuccessListener { task ->
                Log.i(TAG,"Image uploaded succesfully")
            }.addOnCompleteListener { task ->
                findImage(filename,file)
            }
            return Result.success()
        }catch (t: Throwable) {
            Toast.makeText(app,t.message,Toast.LENGTH_SHORT).show()
            return Result.failure(t)
        }
    }

    private fun findImage(filename: String,file: File) {
        //Find and give the image's downloadUrl so it can be uploaded
        val reference = imagesRef.child(filename)
        var downloadTask = reference.downloadUrl
        downloadTask.addOnCompleteListener { url ->
            url.result
            notifyPhotoChanged(file, url.result)
        }
    }

    private fun notifyPhotoChanged(file: File,downloadUri: Uri?) {
        val editor = prefs?.edit()
        editor?.putString(SharedPrefsUtils.USER_IMAGE,Uri.fromFile(file).toString())
        editor?.putString(SharedPrefsUtils.USER_IMAGE_THUMB,Uri.fromFile(file).toString())
        editor?.apply()

        //TODO make the implementation so the user have imageUrl and imageThumbUrl
        // so i can put the image there
//        usersRef.child(prefs?.getString(SharedPrefsUtils.USER_ID,"")!!).child("email").setValue("")

    }

    fun getImageUri(inContext: Context, inImage: Bitmap,name:String): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, name, null)
        return Uri.parse(path)
    }
}