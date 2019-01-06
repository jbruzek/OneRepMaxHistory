package com.joebruzek.onerepmax

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.joebruzek.onerepmax.fragments.ChartFragment
import kotlinx.android.synthetic.main.activity_main.*

/*
 * Main activity for the app. All fragments are served from here
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class MainActivity : AppCompatActivity(),
    com.joebruzek.onerepmax.fragments.ListFragment.OnListFragmentInteractionListener {

    companion object {
        const val BACK_ENABLED = "back_enabled"
        const val ITEM_TITLE = "item_title"
    }

    private var backEnabled: Boolean = false
    private var itemTitle: String = ""

    /*
     * Create the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    /*
     * There was an interaction with the ListFragment. Open the ChartFragment for the item that was pressed
     *
     * @param item - the OneRepMaxRecord of the item in the list that the user pressed
     */
    override fun onListFragmentInteraction(item: OneRepMaxRecord?) {
        val newFragment = ChartFragment.newInstance(item!!.exercise)

        val transaction = supportFragmentManager.beginTransaction()

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment)
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()

        //update the toolbar
        itemTitle = item?.exercise
        putBackOnToolBar()
    }

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
