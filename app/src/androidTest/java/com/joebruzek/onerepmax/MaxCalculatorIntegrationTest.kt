package com.joebruzek.onerepmax

import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import com.joebruzek.onerepmax.util.MaxCalculator
import java.io.File
import java.io.InputStream

/*
 * Integration tests for MaxCalculator
 *
 * @author Joe Bruzek - 1/5/2019
 */
@RunWith(AndroidJUnit4::class)
class MaxCalculatorIntegrationTest {

    /*
     * Utility to copy an inputstream to a file.
     */
    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        inputStream.use { input ->
            this.outputStream().use { fileOut ->
                input.copyTo(fileOut)
            }
        }
    }

    /*
     * Test the readFile function. Had trouble grabbing the file resource uri to use with the test.
     * The current solution is to grab an inputStream to the file and copy it into a new file where I know the path.
     * This isn't an ideal solution but it works for now. Should be revisited in the future.
     */
    @Test
    fun testReadFile() {
        //get the file Uri
        val context = InstrumentationRegistry.getTargetContext()
        val f = File(context.externalCacheDir!!.toString() + "/test.txt")
        f.copyInputStreamToFile(context.classLoader.getResource("workoutData-1.txt").openStream())
        val uri = Uri.parse(f.toURI().toASCIIString())

        //process the file
        val resultCode = MaxCalculator.readFile(context, uri)

        assertEquals(0, resultCode)
        assertEquals(3, MaxCalculator.map.size)
        assertEquals(28, MaxCalculator.map["Deadlift"]!!.size)
        assertEquals(30, MaxCalculator.map["Barbell Bench Press"]!!.size)
        assertEquals(30, MaxCalculator.map["Back Squat"]!!.size)
    }
}