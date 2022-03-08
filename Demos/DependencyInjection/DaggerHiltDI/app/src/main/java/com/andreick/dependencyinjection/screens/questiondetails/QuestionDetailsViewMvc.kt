package com.andreick.dependencyinjection.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import com.andreick.dependencyinjection.databinding.LayoutQuestionDetailsBinding
import com.andreick.dependencyinjection.questions.QuestionWithBody
import com.andreick.dependencyinjection.screens.common.imageloader.ImageLoader
import com.andreick.dependencyinjection.screens.common.viewsmvc.BaseViewMvc

class QuestionDetailsViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val imageLoader: ImageLoader
) : BaseViewMvc<QuestionDetailsViewMvc.Listener>() {

    interface Listener {
        fun onBackClicked()
    }

    private val binding = LayoutQuestionDetailsBinding.inflate(layoutInflater, parent, false)

    override val rootView get() = binding.root

    init {
        // init toolbar
        binding.toolbar.setNavigateUpListener {
            for (listener in listeners)
                listener.onBackClicked()
        }

        // init pull-down-to-refresh (used as a progress indicator)
        binding.swipeRefresh.isEnabled = false
    }

    fun bindQuestionsBody(question: QuestionWithBody) {
        binding.txtQuestionBody.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(question.body, Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(question.body)
        }
        imageLoader.loadImage(question.owner.imageUrl, binding.imgUser)
        binding.txtUsername.text = question.owner.name
    }

    fun showProgressIndication() {
        binding.swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        binding.swipeRefresh.isRefreshing = false
    }
}