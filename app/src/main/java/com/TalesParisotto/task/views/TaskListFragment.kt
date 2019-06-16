package com.TalesParisotto.task.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.TalesParisotto.task.R
import com.TalesParisotto.task.adapter.TaskListAdapter
import com.TalesParisotto.task.business.TaskBusiness
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.entities.OntaskListFragmentInteractionlistener
import com.TalesParisotto.task.util.SecurityPreferences


class TaskListFragment : Fragment(), View.OnClickListener {

    private lateinit var mContext: Context
    private lateinit var mRecyclerTaskList: RecyclerView
    private lateinit var mTaskBusiness: TaskBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mTaskFilter: Int = 0
    private lateinit var mListener: OntaskListFragmentInteractionlistener

    companion object {
        fun newInstance(taskFilter:Int): TaskListFragment{

            val args: Bundle = Bundle()
            args.putInt(TaskConstants.TASKFILTER.KEY,taskFilter)

            val fragment = TaskListFragment()
            fragment.arguments = args

            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mTaskFilter = arguments!!.getInt(TaskConstants.TASKFILTER.KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val rootView = inflater.inflate(R.layout.fragment_task_list, container, false)

        rootView.findViewById<FloatingActionButton>(R.id.floatAddTask).setOnClickListener(this)
        mContext = rootView.context

        mTaskBusiness = TaskBusiness(mContext)
        mSecurityPreferences = SecurityPreferences(mContext)
        mListener = object : OntaskListFragmentInteractionlistener{
            override fun onClickListener(taskId:Int) {
                val bundle:Bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID,taskId)

                val intent = Intent(mContext,TaskListFragment::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }
            override fun onDeleteClick(taskId: Int) {
                mTaskBusiness.delete(taskId)
                loadtasks()
                Toast.makeText(mContext,"Tarefa removida com sucesso",Toast.LENGTH_LONG).show()
            }

            override fun onUncompleteCick(taskId: Int) {
                mTaskBusiness.complete(taskId,false)
                loadtasks()
            }

            override fun onCompleteCick(taskId: Int) {
                mTaskBusiness.complete(taskId,true)
                loadtasks()
            }
        }

        mRecyclerTaskList = rootView.findViewById(R.id.recyclerTaskList)

        mRecyclerTaskList.adapter = TaskListAdapter(mutableListOf(),mListener)

        mRecyclerTaskList.layoutManager = LinearLayoutManager(mContext)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadtasks()
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.floatAddTask ->{
                startActivity(Intent(mContext,TaskListFragment::class.java))
            }
        }
    }

    private fun loadtasks(){
        mRecyclerTaskList.adapter = TaskListAdapter(mTaskBusiness.getList(mTaskFilter),mListener)
    }
}
