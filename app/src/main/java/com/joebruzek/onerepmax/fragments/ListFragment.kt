package com.joebruzek.onerepmax.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joebruzek.onerepmax.R

/*
 * The list fragment displays a list of one rep max values
 *
 * @author Joe Bruzek - 1/5/2018
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                //TODO: set adapter and stuff
            }
        }
        return view
    }
}