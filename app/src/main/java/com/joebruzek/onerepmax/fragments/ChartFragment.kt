package com.joebruzek.onerepmax.fragments

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.joebruzek.onerepmax.MaxCalculator
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
            name = it.getString(ARG_NAME)!!
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

        val exercise = activity?.findViewById<TextView>(R.id.list_item_exercise_name)
        val oneRM = activity?.findViewById<TextView>(R.id.list_item_max_weight)

        exercise?.text = name
        oneRM?.text = MaxCalculator.maxMap[name]?.toString()

        initChart()
    }

    /*
     * Initialize the chart and set the formatting
     */
    private fun initChart() {
        val chart = activity?.findViewById<LineChart>(R.id.chart)
        chart?.data = viewModel.getChartData()
        chart?.lineData?.setDrawValues(false)

        //X Axis
        val xAxis = chart?.xAxis
        xAxis?.granularity = 1f
        xAxis?.valueFormatter = viewModel.getXAxisValueFormatter()
        xAxis?.textColor = Color.parseColor("#BEFFFFFF")
        xAxis?.textSize = 12f
        xAxis?.position = XAxis.XAxisPosition.BOTTOM

        //Y Axis
        val right: YAxis? = chart?.axisRight
        right?.isEnabled = false
        val left: YAxis? = chart?.axisLeft
        left?.textColor = Color.parseColor("#BEFFFFFF")
        left?.textSize = 14f

        //Legend
        chart?.legend?.isEnabled = false
        chart?.description?.isEnabled = false

        chart?.invalidate()
    }
}