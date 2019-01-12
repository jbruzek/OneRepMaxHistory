package com.joebruzek.onerepmax.util

import android.content.Context
import android.net.Uri
import android.os.AsyncTask

/*
 * A task that processes a file in a separate thread. Returns to the caller the result code from processing the file
 *
 * @author: Joe Bruzek - 1/6/2019
 */
class ProcessFileTask(val context: Context) : AsyncTask<Uri, Int, Int>() {
    lateinit var caller: ProcessFileTaskListener

    override fun doInBackground(vararg reader: Uri): Int {
        return MaxCalculator.readFile(context, reader[0])
    }

    override fun onPostExecute(result: Int) {
        super.onPostExecute(result)
        caller.processCompleted(result)
    }

    /*
     * Callback interface
     */
    interface ProcessFileTaskListener {
        fun processCompleted(result: Int)
    }
}
