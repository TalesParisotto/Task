package com.TalesParisotto.task.constants

class DataBaseConstants{
    object USER{
        val TABLE_NAME = "user"

        object COLUMS{
            val ID = "id"
            val NAME = "name"
            val EMAIL = "email"
            val PASSWORD = "password"
        }

    }

    object PRIORITY{
        val TABLE_NAME = "priority"

        object COLUMS{
            val ID = "id"
            val DESCRIPTION = "name"

        }

    }

    object TASK{
        val TABLE_NAME = "task"

        object COLUMS{
            val ID = "id"
            val USERID = "userid"
            val PRIORITY = "priority"
            val DESCRIPTION = "description"
            val COMPLETE = "complete"
            val DUEDATE= "duedate"
        }

    }

}