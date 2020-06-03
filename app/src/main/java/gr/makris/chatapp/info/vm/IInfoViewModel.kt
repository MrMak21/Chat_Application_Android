package gr.makris.chatapp.info.vm

import androidx.lifecycle.MutableLiveData
import gr.makris.chatapp.result.Result

interface IInfoViewModel {

    var logoutObserver: MutableLiveData<Result<Unit>>
    fun logOut()
}