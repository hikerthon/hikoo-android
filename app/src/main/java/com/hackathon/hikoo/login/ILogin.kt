package com.hackathon.hikoo.login

import com.hackathon.hikoo.IBase

interface ILogin: IBase {
    fun loginSuccess()
    fun loginFailed()

}