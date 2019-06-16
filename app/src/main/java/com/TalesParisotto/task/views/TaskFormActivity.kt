package com.TalesParisotto.task.views

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.TalesParisotto.task.R
import com.TalesParisotto.task.business.PriorityBusiness
import com.TalesParisotto.task.business.TaskBusiness
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.entities.PriorityEntitity
import com.TalesParisotto.task.entities.TaskEntity
import com.TalesParisotto.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_task_form.*
import java.lang.reflect.Executable
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private lateinit var mPriorityBusiness: PriorityBusiness
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private val mSimpleDateFormat:SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    private var mLstPrioritiesEntity: MutableList<PriorityEntitity> = mutableListOf()
    private var mLstPrioritiesEntityId: MutableList<Int> = mutableListOf()
    private var mTaskId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        mPriorityBusiness = PriorityBusiness(this)
        mTaskBusiness = TaskBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()
        loadPriorities()
        loadDataFromActivity()
    }

    private fun setListeners(){
        buttonDate.setOnClickListener(this)
        buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.buttonDate -> {
                openDatePickerDialog()
            }
            R.id.buttonSave ->{
                handleSave()
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar =  Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)

        buttonDate.text = mSimpleDateFormat.format(calendar.time)
    }

    private fun loadDataFromActivity(){
        val bundle = intent.extras
        if(bundle != null){
            mTaskId =  bundle.getInt(TaskConstants.BUNDLE.TASKID)

            val task = mTaskBusiness.get(mTaskId)
            if(task != null ) {
                description.setText(task.description)
                buttonDate.text = task.dueDate
                checkcomplete.isChecked = task.complete
                spinnerPriority.setSelection(getIndex(task.priority))

                buttonSave.text = "atualizar tarefa"
            }
        }
    }

    private fun handleSave(){
        try {

            val description = description.text.toString()
            val priorityId = mLstPrioritiesEntityId[spinnerPriority.selectedItemPosition]
            val complete = checkcomplete.isChecked
            val dueDate = buttonDate.text.toString()
            val userId = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_ID).toInt()

            val taskEntitry =  TaskEntity(mTaskId,userId,priorityId,description,dueDate,complete)

            if(mTaskId == 0) {
                mTaskBusiness.insert(taskEntitry)
                Toast.makeText(this,"tarefa incluida com sucesso",Toast.LENGTH_LONG).show()
            }else{
                mTaskBusiness.upDate(taskEntitry)
                Toast.makeText(this,"tente novamemte",Toast.LENGTH_LONG).show()
            }

            finish()

        }catch (e:Exception){
            Toast.makeText(this,"Erro inesperado", Toast.LENGTH_LONG).show()
        }
    }

    private fun openDatePickerDialog(){

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val dayOfMonth = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,this,year,month,dayOfMonth).show()
    }

    private fun getIndex(id:Int): Int{
        var index = 0
        for(i in 0..mLstPrioritiesEntity.size){
            if(mLstPrioritiesEntity[i].id == id){
                index = i
                break
            }
        }
        return index
    }

    private fun loadPriorities(){
        mLstPrioritiesEntity = mPriorityBusiness.getList()
        val lstPriorities = mLstPrioritiesEntity.map{it.description}
        mLstPrioritiesEntityId = mLstPrioritiesEntity.map{it.id}.toMutableList()

        val adapter = ArrayAdapter<String> (this,android.R.layout.simple_spinner_dropdown_item,lstPriorities)
        spinnerPriority.adapter = adapter
    }


}
