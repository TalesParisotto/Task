package com.TalesParisotto.task.business

import android.content.Context
import android.content.IntentFilter
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.entities.OntaskListFragmentInteractionlistener
import com.TalesParisotto.task.entities.TaskEntity
import com.TalesParisotto.task.repository.TaskRepository
import com.TalesParisotto.task.util.SecurityPreferences

class TaskBusiness(context: Context){

    private val mTaskRepository: TaskRepository = TaskRepository.getInstance(context)
    private val mSecurityPreferences: SecurityPreferences = SecurityPreferences(context)

    fun get(id:Int) = mTaskRepository.get(id)

    fun getList(taskFilter: Int) : MutableList<TaskEntity> {
        val userId = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_EMAIL).toInt()
        return mTaskRepository.getList(userId,taskFilter)
    }

    fun insert(taskEntity: TaskEntity) = mTaskRepository.insert(taskEntity)

    fun upDate(taskEntity: TaskEntity) = mTaskRepository.update(taskEntity)

    fun delete(taskId: Int) = mTaskRepository.delete(taskId)

    fun complete(taskId:Int , complete:Boolean){
        val task = mTaskRepository.get(taskId)
        if(task != null){
            task.complete = complete
            mTaskRepository.update(task)
        }
    }
}