package com.TalesParisotto.task.repository

import android.content.Context
import android.database.Cursor
import com.TalesParisotto.task.constants.DataBaseConstants
import com.TalesParisotto.task.entities.PriorityEntitity

class PriorityRepository private constructor(context: Context){
    private var nTaskDatabaseHelper : TaskDatabaseHelper = TaskDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context) : PriorityRepository {
            if(INSTANCE == null){
                INSTANCE = PriorityRepository(context)
            }
            return INSTANCE as PriorityRepository
        }

        private var INSTANCE: PriorityRepository? = null
    }

    fun getList():MutableList<PriorityEntitity>{

        val list = mutableListOf<PriorityEntitity>()

        try{
            val cursor : Cursor
            val db = nTaskDatabaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.PRIORITY.TABLE_NAME}",null)
            if(cursor.count > 0){
                while(cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMS.ID))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.PRIORITY.COLUMS.DESCRIPTION))

                    list.add(PriorityEntitity(id,description))
                }
                cursor.close()
            }
        }catch(e:Exception){
            return list
        }
        return list
    }
}