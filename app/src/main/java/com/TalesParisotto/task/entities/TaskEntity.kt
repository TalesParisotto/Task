package com.TalesParisotto.task.entities

data class TaskEntity(val id: Int,
                      val userId: Int,
                      val priority: Int,
                      val description:String,
                      val dueDate: String,
                      var complete:Boolean)