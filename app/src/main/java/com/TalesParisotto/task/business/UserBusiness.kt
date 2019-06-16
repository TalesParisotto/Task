package com.TalesParisotto.task.business

import android.content.Context
import android.provider.ContactsContract
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.entities.UserEntity
import com.TalesParisotto.task.repository.UserRepository
import com.TalesParisotto.task.util.SecurityPreferences
import com.TalesParisotto.task.util.ValidadationExeption

class UserBusiness(val context: Context){

    private val mUserRepository :UserRepository = UserRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun login(email: String,password: String):Boolean{

        val user:UserEntity? = mUserRepository.get(email, password)
        if(user != null){
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID,user.id.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME,user.name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL,user.email)
            return true
        }else{
            return false
        }
    }

    fun insert(name:String,email: String,password:String){

        try {
            if (name == "" || email == "" || password == "") {
                throw ValidadationExeption("informe todos os campos")
            }
            if(mUserRepository.isEmailExistent(email)){
                throw ValidadationExeption("email ja existe")
            }
            val userId = mUserRepository.insert(name, email, password)

            mSecurityPreferences.storeString(TaskConstants.KEY.USER_ID,userId.toString())
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_NAME,name)
            mSecurityPreferences.storeString(TaskConstants.KEY.USER_EMAIL,email)
        }catch (e:Exception){

        }
    }

}