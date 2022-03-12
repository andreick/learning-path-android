package com.andreick.dependencyinjection.screens.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.andreick.dependencyinjection.R
import com.andreick.dependencyinjection.screens.common.ScreenNavigator
import com.andreick.dependencyinjection.screens.common.activities.BaseActivity
import com.andreick.dependencyinjection.screens.common.toolbar.MyToolbar
import com.andreick.dependencyinjection.screens.common.viewmodels.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewModelActivity : BaseActivity() {

    @Inject lateinit var screensNavigator: ScreenNavigator
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MyViewModel
    private lateinit var otherViewModel: MyOtherViewModel

    private lateinit var toolbar: MyToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_view_model)

        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener {
            screensNavigator.navigateBack()
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[MyViewModel::class.java]
        otherViewModel = ViewModelProvider(this, viewModelFactory)[MyOtherViewModel::class.java]

        viewModel.question.observe(this) { questions ->
            Toast.makeText(this, "fetched ${questions.size} questions", Toast.LENGTH_SHORT)
                .show()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ViewModelActivity::class.java)
            context.startActivity(intent)
        }
    }
}