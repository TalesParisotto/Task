package com.TalesParisotto.task.repository

import com.TalesParisotto.task.entities.PriorityEntitity

class PriorityCacheConstants private constructor(){

    companion object {
        fun setChache(list: List<PriorityEntitity>){
            for(item in list){
                mPriorityCache.put(item.id,item.description)
            }
        }

        fun getPriorityDescription(id:Int): String{
            if(mPriorityCache[id] == null){
                return ""
            }
            return mPriorityCache[id].toString()
        }

        private val mPriorityCache = hashMapOf<Int,String>()
    }
}