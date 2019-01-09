package com.joebruzek.onerepmax.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.joebruzek.onerepmax.util.MaxCalculator
import com.joebruzek.onerepmax.R
import java.text.SimpleDateFormat
import java.util.*

/*
 * View Model for the Graph Fragment. Stores graph data
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class ChartFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var name: String = ""

    /*
     * Get the LineData for the chart.
     * Takes data from MaxCalculator and formats it into chart-readable data
     *
     * @return a LineData object to be used by the chart
     */
    fun getChartData() : LineData {
        val lastFive = getFirstFive()
        val entries = lastFive.mapIndexed { i,it -> Entry(i.toFloat(), it.second.toFloat()) }

        val primaryColor = ContextCompat.getColor(getApplication<Application>(), R.color.colorPrimary)

        val dataSet = LineDataSet(entries, name)
        dataSet.color = primaryColor
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 6f
        dataSet.setCircleColor(primaryColor)
        dataSet.circleHoleColor = primaryColor

        return LineData(dataSet)
    }

    /*
     * Get the First five (or less) data points for an exercise of name $name
     * Sort in reverse order so the chart displays left to right
     *
     * @return List of length <= 5
     */
    private fun getFirstFive() : List<Pair<Date, Int>> {
        if (MaxCalculator.map[name] == null) {
            return listOf()
        }

        return MaxCalculator.map[name]!!.take(5).asReversed()
    }

    /*
     * Get the labels for the xAxis of the chart
     *
     * @return a List of length <= 5
     */
    private fun getChartLabels() : List<String> {
        val lastFive = getFirstFive()
        val formatter = SimpleDateFormat("MM/dd")
        return lastFive.map { it -> formatter.format(it.first) }
    }

    /*
     * Get the XAxisValueFormatter, this associates an xAxis position with a label
     */
    fun getXAxisValueFormatter() : IAxisValueFormatter {
        val labels = getChartLabels()
        return object : IAxisValueFormatter {
            // we don't draw numbers, so no decimal digits needed
            val decimalDigits: Int
                get() = 0

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                //catch filler values if the chart is not full
                if (value.toInt() < 0 || value.toInt() >= labels.size) return ""

                return labels[value.toInt()]
            }
        }
    }
}