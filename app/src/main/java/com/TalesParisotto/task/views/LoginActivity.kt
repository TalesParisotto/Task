package com.TalesParisotto.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.TalesParisotto.task.R
import com.TalesParisotto.task.business.UserBusiness
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences : SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListenrs()

        verifyLoggerUser()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonLogin -> {
                handleLogin()
            }
            R.id.textRigister -> {
                startActivity(Intent(this,RegisterActivity::class.java))
            }
        }
    }

    private fun handleLogin(){
        val email = editEmail.text.toString()
        val password = editSenha.text.toString()

        if(mUserBusiness.login(email,password)){

            startActivity(Intent(this,MainActivity::class.java) )
            finish()
        }else{
            Toast.makeText(this,"usuario e ou senha incorretos",Toast.LENGTH_LONG).show()
        }
    }

    private fun setListenrs(){
        buttonLogin.setOnClickListener(this)
        textRigister.setOnClickListener(this)
    }

    private fun verifyLoggerUser(){
        val userId = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_NAME)

        if(userId != "" && name != ""){
            startActivity(Intent(this,MainActivity::class.java) )
            finish()
        }
    }
}
