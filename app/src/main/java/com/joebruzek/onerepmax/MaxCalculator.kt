package com.joebruzek.onerepmax

import android.content.Context
import android.net.Uri
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

    /*
     * Read a file and process it line by line into the map
     *
     * @param context - the Activity context, used to access the file
     * @param uri - the uri of the file to read
     *
     * @return 0 for success, else failure
     */
    fun readFile(context: Context, uri: Uri) : Int {
        //TODO: implement
        return 0
    }

    /*
     * Given a line from the file, process it and insert the data into the map if appropriate
     * Assumes line follows format "date,exercise,sets,reps,weight"
     *
     * @param line - the line to process
     */
    fun processLine(line: String) {
        //TODO: implement
    }
}