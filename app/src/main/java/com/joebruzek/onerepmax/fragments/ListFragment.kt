package com.joebruzek.onerepmax.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joebruzek.onerepmax.MaxCalculator
import com.joebruzek.onerepmax.OneRepMaxRecord
import com.joebruzek.onerepmax.R
import com.joebruzek.onerepmax.adapters.MaxListAdapter

/*
 * The list fragment displays a list of one rep max values
 *
 * @author Joe Bruzek - 1/5/2018
 */
class ListFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MaxListAdapter(MaxCalculator.getMaxList(), listener)
            }
        }
        return view
    }

    /*
     * attach to the activity and make sure the activity implements the listener interface
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }

    }

    /*
     * kill listener on detach
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /*
     * Listener interface for the ListFragment. This allows us to send interaction information to the activity and
     * handle it there
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: OneRepMaxRecord?)
    }
}