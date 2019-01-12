package com.joebruzek.onerepmax

import com.joebruzek.onerepmax.util.MaxCalculator
import org.junit.Assert
import org.junit.Test
import org.junit.Before

/*
 * Tests for MaxCalculator
 *
 * @author Joe Bruzek - 1/5/2019
 */
class MaxCalculatorTest {

    @Before
    fun setupTests() {
        //clear the map
        MaxCalculator.map = hashMapOf()
    }

    @Test
    fun testProcessLineSameExercise() {
        val backSquat1 = "Sep 15 2017,Back Squat,1,5,265"
        val backSquat2 = "Sep 15 2017,Back Squat,1,5,235"
        val backSquat3 = "Sep 11 2017,Back Squat,1,5,235"
        val backSquat4 = "Sep 11 2017,Back Squat,1,5,255"
        val exercise = "Back Squat"

        //Insert one set
        MaxCalculator.processLine(backSquat1)
        Assert.assertEquals(1, MaxCalculator.map.size)
        Assert.assertEquals(1, MaxCalculator.map[exercise]!!.size)
        Assert.assertEquals(298, MaxCalculator.map[exercise]!![0].second)

        //insert a set with a lower max, Map should not update
        MaxCalculator.processLine(backSquat2)
        Assert.assertEquals(1, MaxCalculator.map.size)
        Assert.assertEquals(1, MaxCalculator.map[exercise]!!.size)
        Assert.assertEquals(298, MaxCalculator.map[exercise]!![0].second)

        //insert a set from earlier date
        MaxCalculator.processLine(backSquat3)
        Assert.assertEquals(1, MaxCalculator.map.size)
        Assert.assertEquals(2, MaxCalculator.map[exercise]!!.size)
        Assert.assertEquals(264, MaxCalculator.map[exercise]!![1].second)

        //insert a set from earlier date, new max for that date
        MaxCalculator.processLine(backSquat4)
        Assert.assertEquals(1, MaxCalculator.map.size)
        Assert.assertEquals(2, MaxCalculator.map[exercise]!!.size)
        Assert.assertEquals(286, MaxCalculator.map[exercise]!![1].second)
    }

    @Test
    fun testProcessLineTwoExercises() {
        val backSquatLine = "Sep 11 2017,Back Squat,1,5,255"
        val deadliftLine = "Sep 14 2017,Deadlift,1,5,285"

        //Insert back squat
        MaxCalculator.processLine(backSquatLine)
        Assert.assertEquals(1, MaxCalculator.map.size)

        //Insert deadlift
        MaxCalculator.processLine(deadliftLine)
        Assert.assertEquals(2, MaxCalculator.map.size)
    }
}
