package com.joebruzek.onerepmax.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joebruzek.onerepmax.R
import com.joebruzek.onerepmax.viewmodels.ChartFragmentViewModel

/*
 * This fragment displays a chart of the one rep max history for a movement
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class ChartFragment : Fragment() {

    companion object {
        const val ARG_NAME = "name"

        /*
         * Create a new instance of this fragment with a name argument
         *
         * @param name - the name
         */
        fun newInstance(name: String) = ChartFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_NAME, name)
            }
        }
    }

    private var name = ""
    private lateinit var viewModel: ChartFragmentViewModel

    /*
     * create the fragment, collect the name from initialization
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            name = it.getString(ARG_NAME)
        }
    }

    /*
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    /*
     * init the view model with the fragment information
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChartFragmentViewModel::class.java)
        viewModel.name = name
    }
}