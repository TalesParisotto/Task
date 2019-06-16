package com.TalesParisotto.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.TalesParisotto.task.constants.DataBaseConstants
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.entities.UserEntity

class UserRepository private constructor(context:Context){

    private var nTaskDatabaseHelper : TaskDatabaseHelper = TaskDatabaseHelper(context)
    private var UserEntity:UserEntity? = null

    companion object {
        fun getInstance(context:Context) : UserRepository{
            if(INSTANCE == null){
                INSTANCE = UserRepository(context)
            }
            return INSTANCE as UserRepository
        }

        private var INSTANCE: UserRepository? = null
    }

    fun get (email: String,password: String): UserEntity?{
        var userEntity: UserEntity? = null
        try {
            var cursor: Cursor
            val db = nTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMS.ID,
                    DataBaseConstants.USER.COLUMS.NAME,
                    DataBaseConstants.USER.COLUMS.EMAIL,
                    DataBaseConstants.USER.COLUMS.PASSWORD)

            val selection = "${DataBaseConstants.USER.COLUMS.EMAIL} = ? AND ${DataBaseConstants.USER.COLUMS.PASSWORD} = ?"
            val selectionArgs = arrayOf(email,password)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if(cursor.count > 0){
                cursor.moveToFirst()

               val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.ID))
                val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.NAME))
                val email = cursor.getString(cursor.getColumnIndex(DataBaseConstants.USER.COLUMS.EMAIL))

                userEntity = UserEntity(userId,name,email)
            }

            cursor.close()
        }catch (e:Exception){
            return userEntity
        }

        return userEntity
    }

    fun isEmailExistent(email:String): Boolean{
        var ret:Boolean = false
        try {
            var cursor: Cursor
            val db = nTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.USER.COLUMS.ID)

            val selection = "${DataBaseConstants.USER.COLUMS.EMAIL} = ?"
            val selectionArgs = arrayOf(email)

            cursor = db.query(DataBaseConstants.USER.TABLE_NAME, projection, selection, selectionArgs, null, null, null)

            ret = cursor.count > 0

            cursor.close()

            //cursor =  db.rawQuery("select * from user where email = $email",null)
        }catch (e:Exception){
            throw e
        }
         return ret
    }

    fun insert(name:String,email:String,password:String): Int{
        val db = nTaskDatabaseHelper.writableDatabase

        val insertValues = ContentValues()
        insertValues.put(DataBaseConstants.USER.COLUMS.NAME,name)
        insertValues.put(DataBaseConstants.USER.COLUMS.EMAIL,email)
        insertValues.put(DataBaseConstants.USER.COLUMS.PASSWORD,password)

        return db.insert(DataBaseConstants.USER.TABLE_NAME,null,insertValues).toInt()
    }

}
