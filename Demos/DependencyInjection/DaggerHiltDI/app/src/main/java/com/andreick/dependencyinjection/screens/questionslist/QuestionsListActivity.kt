package com.andreick.dependencyinjection.screens.questionslist

import android.os.Bundle
import com.andreick.dependencyinjection.R
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity

class QuestionsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_frame)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_content, QuestionsListFragment())
                .commit()
        }
    }
}