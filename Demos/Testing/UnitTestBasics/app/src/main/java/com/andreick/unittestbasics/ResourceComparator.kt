package com.andreick.unittestbasics

import android.content.Context

class ResourceComparator {

    fun isEqual(context: Context, resId: Int, string: String) = context.getString(resId) == string
}