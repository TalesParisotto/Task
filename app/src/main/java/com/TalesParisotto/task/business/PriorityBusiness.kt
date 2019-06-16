package com.TalesParisotto.task.business

import android.content.Context
import com.TalesParisotto.task.entities.PriorityEntitity
import com.TalesParisotto.task.repository.PriorityRepository


class PriorityBusiness(context: Context) {

    private val mPriorityRepository:PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntitity> = mPriorityRepository.getList()

}