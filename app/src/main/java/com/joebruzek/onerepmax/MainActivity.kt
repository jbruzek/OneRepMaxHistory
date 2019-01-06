package com.joebruzek.onerepmax

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Main activity for the app. All fragments are served from here
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class MainActivity : AppCompatActivity() {

    companion object {
        const val BACK_ENABLED = "back_enabled"
        const val ITEM_TITLE = "item_title"
    }

    var backEnabled: Boolean = false
    var itemTitle: String = ""

    /*
     * save the values needed to update the toolbar
     */
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putBoolean(BACK_ENABLED, backEnabled)
        outState?.putString(ITEM_TITLE, itemTitle)
    }

    /*
     * restore the activity to where it was before teardown
     */
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

    /*
     * Create the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    /*
     * override so that we can control the display of the toolbar
     */
    override fun onBackPressed() {
        super.onBackPressed()
        clearToolBar()
    }

    /*
     * inflate menu button
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation, menu)
        return true
    }

    /*
     * Selected menu button
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.data_add -> {
                //TODO: select a file
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
     * Put a back arrow on the top toolbar and update the title to be the value of $itemTitle
     */
    private fun putBackOnToolBar() {
        backEnabled = true
        supportActionBar?.title = itemTitle
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {onBackPressed()}
    }

    /*
     * remove the back arrow from the toolbar and set the title to be activity title
     */
    private fun clearToolBar() {
        backEnabled = false
        toolbar.navigationIcon = null
        toolbar.title = title
    }
}
