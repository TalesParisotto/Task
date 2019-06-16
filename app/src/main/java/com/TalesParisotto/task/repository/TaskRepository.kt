package com.TalesParisotto.task.repository

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.TalesParisotto.task.constants.DataBaseConstants
import com.TalesParisotto.task.entities.PriorityEntitity
import com.TalesParisotto.task.entities.TaskEntity
import com.TalesParisotto.task.entities.UserEntity
import java.lang.reflect.Executable

class TaskRepository private constructor(context:Context){
    private var nTaskDatabaseHelper : TaskDatabaseHelper = TaskDatabaseHelper(context)

    companion object {
        fun getInstance(context: Context) : TaskRepository {
            if(INSTANCE == null){
                INSTANCE = TaskRepository(context)
            }
            return INSTANCE as TaskRepository
        }

        private var INSTANCE: TaskRepository? = null
    }

    fun get (id:Int): TaskEntity?{

        var taskEntity: TaskEntity? = null
        try {
            var cursor: Cursor
            val db = nTaskDatabaseHelper.readableDatabase

            val projection = arrayOf(DataBaseConstants.TASK.COLUMS.ID,
                DataBaseConstants.TASK.COLUMS.USERID,
                DataBaseConstants.TASK.COLUMS.PRIORITY,
                DataBaseConstants.TASK.COLUMS.DESCRIPTION,
                DataBaseConstants.TASK.COLUMS.DUEDATE,
                DataBaseConstants.TASK.COLUMS.COMPLETE)

            val selection = "${DataBaseConstants.TASK.COLUMS.ID} = ? "
            val selectionArgs = arrayOf(id.toString())

            cursor = db.query(DataBaseConstants.TASK.TABLE_NAME, projection, selection, selectionArgs, null, null, null)
            if(cursor.count > 0){
                cursor.moveToFirst()

                val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.ID))
                val userId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.USERID))
                val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.PRIORITY))
                val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.DESCRIPTION))
                val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.DUEDATE))
                val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.COMPLETE)) == 1)

                taskEntity = TaskEntity(id,userId,priorityId,description,dueDate,complete)
            }

            cursor.close()
        }catch (e:Exception){
            return taskEntity
        }

        return taskEntity
    }

    fun getList(userId:Int,taskFilter:Int):MutableList<TaskEntity>{

        val list = mutableListOf<TaskEntity>()

        try{
            val cursor : Cursor
            val db = nTaskDatabaseHelper.readableDatabase

            cursor = db.rawQuery("SELECT * FROM ${DataBaseConstants.TASK.TABLE_NAME}" +
                "WHERE ${DataBaseConstants.TASK.COLUMS.USERID} = $userId" +
                "AND ${DataBaseConstants.TASK.COLUMS.USERID} = $taskFilter",null)

            if(cursor.count > 0){
                while(cursor.moveToNext()){
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.ID))
                    val priorityId = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.PRIORITY))
                    val description = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.DESCRIPTION))
                    val dueDate = cursor.getString(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.DUEDATE))
                    val complete = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.TASK.COLUMS.COMPLETE)) == 1)

                    list.add(TaskEntity(id,userId,priorityId,description,dueDate,complete))
                }
                cursor.close()
            }
        }catch(e:Exception){
            return list
        }
        return list
    }

    fun insert(task:TaskEntity){
      try {
          val db = nTaskDatabaseHelper.writableDatabase

          val complete : Int = if(task.complete) 1 else 0


          val insertValues = ContentValues()
          insertValues.put(DataBaseConstants.TASK.COLUMS.USERID,task.userId)
          insertValues.put(DataBaseConstants.TASK.COLUMS.PRIORITY,task.priority)
          insertValues.put(DataBaseConstants.TASK.COLUMS.DESCRIPTION,task.description)
          insertValues.put(DataBaseConstants.TASK.COLUMS.DUEDATE,task.dueDate)
          insertValues.put(DataBaseConstants.TASK.COLUMS.COMPLETE,complete)

          db.insert(DataBaseConstants.TASK.TABLE_NAME,null,insertValues)
      }catch (e:Exception){
          throw e
      }
    }

    fun update(task:TaskEntity){
       try {
           val db = nTaskDatabaseHelper.writableDatabase

           val complete : Int = if(task.complete) 1 else 0


           val updateValues = ContentValues()
           updateValues.put(DataBaseConstants.TASK.COLUMS.USERID,task.userId)
           updateValues.put(DataBaseConstants.TASK.COLUMS.PRIORITY,task.priority)
           updateValues.put(DataBaseConstants.TASK.COLUMS.DESCRIPTION,task.description)
           updateValues.put(DataBaseConstants.TASK.COLUMS.DUEDATE,task.dueDate)
           updateValues.put(DataBaseConstants.TASK.COLUMS.COMPLETE,complete)

           val selection = "${DataBaseConstants.TASK.COLUMS.ID} = ? "
           val selectionArgs = arrayOf(task.id.toString())

           db.update(DataBaseConstants.TASK.TABLE_NAME, updateValues,selection,selectionArgs)
           db.insert(DataBaseConstants.TASK.TABLE_NAME,null,updateValues)
       }catch (e:Exception){
           throw e
       }
    }

    fun delete (id: Int){
       try {
           val db = nTaskDatabaseHelper.writableDatabase

           val where = "${DataBaseConstants.TASK.COLUMS.ID} = ? "
           val whereArgs = arrayOf(id.toString())

           db.delete(DataBaseConstants.TASK.TABLE_NAME,where,whereArgs)
       }catch (e:Exception){
           throw e
       }
    }
}