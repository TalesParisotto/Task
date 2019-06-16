package com.TalesParisotto.task.viewHolder

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.TalesParisotto.task.R
import com.TalesParisotto.task.entities.OntaskListFragmentInteractionlistener
import com.TalesParisotto.task.entities.TaskEntity
import com.TalesParisotto.task.repository.PriorityCacheConstants

class TaskViewHolder(itemView: View, val context: Context, val listener:OntaskListFragmentInteractionlistener): RecyclerView.ViewHolder(itemView){

    private val mTeskDescription: TextView = itemView.findViewById(R.id.textDescricao)
    private val mTextPriority: TextView = itemView.findViewById(R.id.textPrioridade)
    private val mTextDate: TextView = itemView.findViewById(R.id.textDueDate)
    private val mImageTask: ImageView = itemView.findViewById(R.id.imageTask)


    fun bindDate(task:TaskEntity){
        mTeskDescription.text = task.description
        mTextPriority.text = PriorityCacheConstants.getPriorityDescription(task.priority)
        mTextDate.text = task.dueDate

        if(task.complete){
            mImageTask.setImageResource(R.drawable.ic_done)
        }

        mTeskDescription.setOnClickListener(View.OnClickListener {
            listener.onClickListener(task.id)
        })

        mTeskDescription.setOnLongClickListener(View.OnLongClickListener {
            showComfirmationDialog(task)

            true
        })

        mImageTask.setOnClickListener(View.OnClickListener {
            if(task.complete){
                listener.onUncompleteCick(task.id)
            }else{
                listener.onCompleteCick(task.id)
            }
        })
    }

    private fun showComfirmationDialog(task:TaskEntity){
        AlertDialog.Builder(context)
            .setTitle("Remoção de tarefa")
            .setMessage("deseja remover ${task.description}")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Remover",{dialog: DialogInterface, i -> listener.onClickListener(task.id)})
            .setNegativeButton("cancelar",null).show()

    }
}