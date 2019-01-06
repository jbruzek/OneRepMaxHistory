package com.joebruzek.onerepmax

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val BACK_ENABLED = "back_enabled"
        const val ITEM_TITLE = "item_title"
    }

    var backEnabled: Boolean = false
    var itemTitle: String = ""

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBoolean(BACK_ENABLED, backEnabled)
        outState?.putString(ITEM_TITLE, itemTitle)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val backEnabledRecovery = savedInstanceState?.getBoolean(BACK_ENABLED)
        val itemTitleRecovery = savedInstanceState?.getString(ITEM_TITLE)

        if (backEnabledRecovery != null) backEnabled = backEnabledRecovery
        if (itemTitleRecovery != null) itemTitle = itemTitleRecovery

        if (backEnabled) {
            putBackOnToolBar()
        } else {
            clearToolBar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        clearToolBar()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.data_add -> {
                //TODO: select a file
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun putBackOnToolBar() {
        backEnabled = true
        supportActionBar?.title = itemTitle
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {onBackPressed()}
    }

    private fun clearToolBar() {
        backEnabled = false
        toolbar.navigationIcon = null
        toolbar.title = title
    }
}
