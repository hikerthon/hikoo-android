package com.hackathon.hikoo.login

import com.hackathon.hikoo.BaseView

interface LoginView: BaseView {
    fun loginSuccess()
    fun loginFailed()

}