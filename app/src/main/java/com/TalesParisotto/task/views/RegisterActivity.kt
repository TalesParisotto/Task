package com.TalesParisotto.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.TalesParisotto.task.R
import com.TalesParisotto.task.business.UserBusiness
import com.TalesParisotto.task.util.ValidadationExeption
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setListeners()

        mUserBusiness = UserBusiness(this)

    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun setListeners(){
        buttonSave.setOnClickListener(this)
    }

    private fun handleSave(){
        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editSenha.text.toString()

            mUserBusiness.insert(name, email, password)

            startActivity(Intent(this,MainActivity::class.java) )
            finish()
        }catch (e:ValidadationExeption){
            Toast.makeText(this,e.message,Toast.LENGTH_LONG).show()
        }catch (e:Exception){
            Toast.makeText(this,"Erro inesperado",Toast.LENGTH_LONG).show()
        }
    }
}
