package com.andreick.dependencyinjection.screens.questionslist

import android.os.Bundle
import android.util.Log
import com.andreick.dependencyinjection.R
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity
import javax.inject.Inject

class QuestionsListActivity : BaseActivity() {

    @Inject lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
        Log.d("QuestionsListActivity", "$screenNavigator")
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_content, QuestionsListFragment())
                .commit()
        }
    }
}