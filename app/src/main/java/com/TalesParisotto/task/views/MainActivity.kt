package com.TalesParisotto.task.views

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import com.TalesParisotto.task.R
import com.TalesParisotto.task.business.PriorityBusiness
import com.TalesParisotto.task.constants.TaskConstants
import com.TalesParisotto.task.repository.PriorityCacheConstants
import com.TalesParisotto.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mSecurityPreferences : SecurityPreferences
    private lateinit var mPriorityBusiness: PriorityBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        mSecurityPreferences = SecurityPreferences(this)
        mPriorityBusiness = PriorityBusiness(this)

        loadPriorityCache()
        startDefaultFragment()
        formatUserName()
        formatDate()
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var fragment: Fragment? = null
        fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
        when (item.itemId) {
            R.id.nav_done -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
            }
            R.id.nav_todo -> {
                fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.TODO)
            }
            R.id.nav_logout -> {
                handleLogout()
                return false
            }
        }

        val fragmentManeger = supportFragmentManager
        fragmentManeger.beginTransaction().replace(R.id.frameContent,fragment).commit()

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadPriorityCache(){
        PriorityCacheConstants.setChache(mPriorityBusiness.getList())
    }

    private fun startDefaultFragment(){
        val fragment: Fragment = TaskListFragment.newInstance(TaskConstants.TASKFILTER.COMPLETE)
        supportFragmentManager.beginTransaction().replace(R.id.frameContent,fragment).commit()

    }

    private fun handleLogout(){

        mSecurityPreferences.removeStorageString(TaskConstants.KEY.USER_ID)
        mSecurityPreferences.removeStorageString(TaskConstants.KEY.USER_NAME)
        mSecurityPreferences.removeStorageString(TaskConstants.KEY.USER_EMAIL)

        startActivity(Intent(this,LoginActivity::class.java) )
        finish()
    }

    private fun formatDate(){
        val c = Calendar.getInstance()

        val days = arrayOf("Domingo","segunda","terça","quarta","quinta","sexta","sabado")
        val months = arrayOf("janeiro","fevereiro","março","abril","maio","junho","julho","agosto","setembro","outubro","novembro","dezembro")

        val str = "${days[c.get(Calendar.DAY_OF_WEEK) - 1]},${c.get(Calendar.DAY_OF_MONTH)} de ${months[c.get(Calendar.MONTH)]}"
        textDateDescription.text = str
    }

    private fun formatUserName(){
        val str = "Olá, ${mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_NAME)}"
        textHelp.text = str

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val header = navigationView.getHeaderView(0)

        val name = header.findViewById<TextView>(R.id.textName)
        val email = header.findViewById<TextView>(R.id.textEmail)
        name.text = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_NAME)
        email.text = mSecurityPreferences.getStorageString(TaskConstants.KEY.USER_EMAIL)
    }
}
