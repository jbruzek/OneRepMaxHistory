package com.joebruzek.onerepmax.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

/*
 * View Model for the Graph Fragment. Stores graph data
 *
 * @author: Joe Bruzek - 1/5/2018
 */
class ChartFragmentViewModel(application: Application) : AndroidViewModel(application) {
    var name: String = ""
}