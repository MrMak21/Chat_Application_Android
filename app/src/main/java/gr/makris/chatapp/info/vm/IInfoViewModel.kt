package gr.makris.chatapp.info.vm

import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.result.Result
import java.io.File

interface IInfoViewModel {

    var logoutObserver: MutableLiveData<Result<Unit>>
    fun logOut()
    fun uploadFileToServer(file: File): Result<Unit>
}