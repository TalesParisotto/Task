package com.TalesParisotto.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.TalesParisotto.task.R
import com.TalesParisotto.task.entities.OntaskListFragmentInteractionlistener
import com.TalesParisotto.task.entities.TaskEntity
import com.TalesParisotto.task.viewHolder.TaskViewHolder

class TaskListAdapter(val taskList:List<TaskEntity>,val listener: OntaskListFragmentInteractionlistener): RecyclerView.Adapter<TaskViewHolder>(){

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.bindDate(task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): TaskViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.row_task_list,parent,false)

        return TaskViewHolder(view,context!!, listener)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }

}