package com.joebruzek.onerepmax

import android.content.Context
import android.net.Uri
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

/*
 * Singleton used to process a file and store the one rep max information from that file.
 *
 * If this were to be a larger application there would be considerations for different data storage. As is, the app
 * assumes it will only be holding one file's worth of workout data at a time, which is reasonable to keep in memory.
 * Larger implementations (where we may import and save many different files worth of data to compare or view)
 * would demand larger storage, either locally (probably with SQLite) or remote with a server.
 *
 * @author Joe Bruzek 1/5/2018
 */
object MaxCalculator {
    // Each key in the map is an exercise
    // Each value is a list of Pair<Date, calculated 1RM>
    var map: HashMap<String, MutableList<Pair<Date, Int>>> = hashMapOf()

    //This maps individual exercises to their all time max
    var maxMap: MutableMap<String, Int> = hashMapOf()

    /*
     * Read a file and process it line by line into the map
     *
     * @param context - the Activity context, used to access the file
     * @param uri - the uri of the file to read
     *
     * @return 0 for success, else failure
     */
    fun readFile(context: Context, uri: Uri) : Int {
        //clear the map - in case it previously held values
        map = hashMapOf()

        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
                    lines.forEach { processLine(it) }
                }
            }

        } catch (e: Exception) {
            println("Reading CSV Error!")
            e.printStackTrace()
            return 1
        }
        return 0
    }

    /*
     * Given a line from the file, process it and insert the data into the map if appropriate
     * Assumes line follows format "date,exercise,sets,reps,weight"
     *
     * @param line - the line to process
     */
    fun processLine(line: String) {
        val tokens = line.split(",")
        if (tokens.isNotEmpty()) {
            val oneRM = calculateOneRepMax(tokens[3].toInt(), tokens[4].toInt())
            val date = getDate(tokens[0])

            //if this is the first of this exercise, add to map and exit
            if (map[tokens[1]] == null) {
                map[tokens[1]] = mutableListOf(Pair(date, oneRM))
            } else {
                //get the index of the last item in the list
                val lastIndex: Int = if (map[tokens[1]] != null) {
                    map[tokens[1]]!!.lastIndex
                } else {
                    0
                }

                val prev1RM = map[tokens[1]]!![lastIndex].second
                val prevDate = map[tokens[1]]!![lastIndex].first

                if (prevDate == date) {
                    //if this is the same date but a larger 1RM, update the pair
                    if (oneRM > prev1RM) {
                        map[tokens[1]]!![lastIndex] = Pair(date, oneRM)
                    }
                } else {
                    //different date, append to list
                    map[tokens[1]]!!.add(Pair(date, oneRM))
                }
            }
            //insert the value into the max map if it is greater
            insertMaxIfGreater(tokens[1], oneRM)
        }
    }

    /*
     * Get a list of the max values for every exercise
     *
     * @return a list of OneRepMaxRecords
     */
    fun getMaxList() : List<OneRepMaxRecord> {
        val list: MutableList<OneRepMaxRecord> = mutableListOf()
        for ((exercise, max) in maxMap) {
            list.add(OneRepMaxRecord(exercise,max))
        }
        //make immutable before returning
        return list.toList()
    }

    /*
     * Insert a new max into the max map IFF it is greater than the previous max for that exercise
     *
     * @param exercise - the name of the exercise
     * @param max - the one rep max for that exercise
     */
    private fun insertMaxIfGreater(exercise: String, max: Int) {
        if (maxMap[exercise] == null || (maxMap[exercise] != null && max > maxMap[exercise]!!)) {
            maxMap[exercise] = max
        }
    }

    /*
     * Calculate the 1 rep max for this rep/weight pair using the Brzycki formula
     * abstracted for readability
     *
     * @param reps - the number of reps
     * @param weight - the weight lifted
     *
     * @return the calculated 1rm
     */
    private fun calculateOneRepMax(reps: Int, weight: Int) : Int {
        return ((weight * (36.toDouble() / (37 - reps))).toInt())
    }

    /*
     * Turn a string date into a Date object
     * Assumes date format "MMM dd yyyy"
     *
     * @param date - a string date in format "MMM dd yyyy"
     *
     * @return a date object
     */
    private fun getDate(date: String) : Date {
        val parser = SimpleDateFormat("MMM dd yyyy")
        return parser.parse(date)
    }
}