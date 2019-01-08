package com.joebruzek.onerepmax.util

import android.support.v7.util.DiffUtil
import com.joebruzek.onerepmax.OneRepMaxRecord

class OneMaxRecordDiffUtil(val newlist: List<OneRepMaxRecord>, val oldlist: List<OneRepMaxRecord>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldlist[oldItemPosition].exercise == newlist[newItemPosition].exercise)
    }

    override fun getOldListSize(): Int {
        return oldlist.size
    }

    override fun getNewListSize(): Int {
        return newlist.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldlist[oldItemPosition].max == newlist[newItemPosition].max)
    }

}