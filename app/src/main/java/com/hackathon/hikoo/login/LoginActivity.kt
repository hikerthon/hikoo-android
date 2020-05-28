package com.hackathon.hikoo.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.button.MaterialButton
import com.hackathon.hikoo.R
import com.hackathon.hikoo.maincontainer.MainActivity
import com.hackathon.hikoo.utils.bindView
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity(), LoginView {

    private val presenter: LoginPresenter by inject()
    private val loginButton: MaterialButton by bindView(R.id.btn_login)
    private val emailEdittext: AppCompatEditText by bindView(R.id.email_edit)
    private val passwordEdittext: AppCompatEditText by bindView(R.id.password_edit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acctivity)
        presenter.attachView(this)

//        loginSuccess()

        loginButton.setOnClickListener {
            if (emailEdittext.text.toString().isNotEmpty() && passwordEdittext.text.toString().isNotEmpty()) {
                presenter.login(emailEdittext.text.toString(), passwordEdittext.text.toString())
            } else {
                AlertDialog.Builder(this)
                    .setMessage("Do not empty")
                    .create()
                    .show()
            }
        }
    }

    override fun loginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
    }

    override fun loginFailed() {
        AlertDialog.Builder(this)
            .setMessage("Login Failed")
            .create()
            .show()
    }

    override fun provideLifecycleOwner(): LifecycleOwner {
        return this
    }
}
