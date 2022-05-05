package com.andreick.unittestbasics

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before

import org.junit.Test

class ResourceComparatorTest {

    private lateinit var resourceComparator: ResourceComparator

    @Before
    fun setup() {
        resourceComparator = ResourceComparator()
    }

    @Test
    fun stringResourceSameAsGivenStringReturnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparator.isEqual(context, R.string.app_name, "Unit Test Basics")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenStringReturnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparator.isEqual(context, R.string.app_name, "Hello World")
        assertThat(result).isFalse()
    }
}