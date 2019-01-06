package com.joebruzek.onerepmax.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.joebruzek.onerepmax.OneRepMaxRecord
import com.joebruzek.onerepmax.R
import com.joebruzek.onerepmax.fragments.ListFragment
import kotlinx.android.synthetic.main.max_list_item.view.*

/*
 * Adapter for the 1rm list
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class MaxListAdapter(
    private val mValues: List<OneRepMaxRecord>,
    private val mListener: ListFragment.OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MaxListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as OneRepMaxRecord
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    /*
     * inflate the view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.max_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    /*
     * bind the view holder, set the text, set the onclicklistener
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mExerciseView.text = item.exercise
        holder.mMaxView.text = item.max.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    /*
     * Viewholder for this adapter. Grab the view elements to update on bind
     */
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mExerciseView: TextView = mView.list_item_exercise_name
        val mMaxView: TextView = mView.list_item_max_weight

        override fun toString(): String {
            return super.toString() + " " + mExerciseView.text
        }
    }
}