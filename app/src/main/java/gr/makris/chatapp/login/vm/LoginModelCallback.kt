package gr.makris.chatapp.login.vm

import gr.makris.chatapp.data.User

interface LoginModelCallback {

    fun getUserByEmailCallback(user: User)
}