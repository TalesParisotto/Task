package com.TalesParisotto.task.entities

interface OntaskListFragmentInteractionlistener {

    fun onClickListener(taskId:Int)

    fun onDeleteClick(taskId:Int)

    fun onUncompleteCick(taskId:Int)

    fun onCompleteCick(taskId:Int)

}