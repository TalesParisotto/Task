package com.TalesParisotto.task.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.TalesParisotto.task.constants.DataBaseConstants

class TaskDatabaseHelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object {
        private val DATABASE_VERSION:Int = 1
        private val DATABASE_NAME:String = "task.db"
    }

    private val createTableUser = """ CREATE TABLE ${DataBaseConstants.USER.TABLE_NAME}(
        ${DataBaseConstants.USER.COLUMS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.USER.COLUMS.NAME} TEXT,
        ${DataBaseConstants.USER.COLUMS.EMAIL} TEXT,
        ${DataBaseConstants.USER.COLUMS.PASSWORD} TEXT
         );"""

    private val createTablePriority = """ CREATE TABLE ${DataBaseConstants.PRIORITY.TABLE_NAME}(
        ${DataBaseConstants.PRIORITY.COLUMS.ID} INTEGER PRIMARY KEY,
        ${DataBaseConstants.PRIORITY.COLUMS.DESCRIPTION} TEXT
         );"""

    private val createTableTask = """ CREATE TABLE ${DataBaseConstants.TASK.TABLE_NAME}(
        ${DataBaseConstants.TASK.COLUMS.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${DataBaseConstants.TASK.COLUMS.USERID} INTEGER,
        ${DataBaseConstants.TASK.COLUMS.PRIORITY} INTEGER,
        ${DataBaseConstants.TASK.COLUMS.DESCRIPTION} TEXT,
        ${DataBaseConstants.TASK.COLUMS.COMPLETE} INTEGER,
        ${DataBaseConstants.TASK.COLUMS.DUEDATE} TEXT
         );"""

    private val insertPriorities = """INSERT INTO ${DataBaseConstants.PRIORITY.TABLE_NAME}
        VALUES (1,'Baixa'), (2,'Media'), (3, 'Alta'), (4,'Critica')"""

    private val deleteTableUser = "drop table if exists ${DataBaseConstants.USER.TABLE_NAME}"
    private val deleteTablePriority = "drop table if exists ${DataBaseConstants.PRIORITY.TABLE_NAME}"
    private val deleteTableTask = "drop table if exists ${DataBaseConstants.TASK.TABLE_NAME}"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTableUser)
        db.execSQL(createTablePriority)
        db.execSQL(createTableTask)
        db.execSQL(createTablePriority)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(deleteTableUser)
        db.execSQL(createTablePriority)
        db.execSQL(createTableTask)

    }
}