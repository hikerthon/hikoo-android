package com.hackathon.hikoo.login

import com.hackathon.hikoo.BasePresenter
import com.hackathon.hikoo.manager.UserManager
import com.hackathon.hikoo.network.APIManager
import io.reactivex.rxkotlin.subscribeBy

class LoginPresenter(
    private val apiManager: APIManager,
    private val userManager: UserManager
) : BasePresenter<LoginActivity>() {

    fun login(email: String, password: String) {
        userManager.login(email, password, object : UserManager.OnUserLoginListener {
            override fun onUserLoginSuccess(token: String) {
                view?.loginSuccess()
            }

            override fun onUserLoginFailed() {
                view?.loginFailed()
            }
        })
    }
}