package com.andreick.dependencyinjection.screens.questionslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreick.dependencyinjection.databinding.LayoutQuestionListItemBinding
import com.andreick.dependencyinjection.databinding.LayoutQuestionsListBinding
import com.andreick.dependencyinjection.questions.Question
import com.andreick.dependencyinjection.screens.common.viewsmvc.BaseViewMvc

class QuestionsListViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) : BaseViewMvc<QuestionsListViewMvc.Listener>() {

    interface Listener {
        fun onRefresh()
        fun onQuestionClicked(clickedQuestion: Question)
        fun onViewModelClicked()
    }

    private val binding = LayoutQuestionsListBinding.inflate(layoutInflater, parent, false)
    private val questionsAdapter: QuestionsAdapter

    override val rootView get() = binding.root

    init {
        binding.toolbar.setViewModelListener {
            for (listener in listeners) {
                listener.onViewModelClicked()
            }
        }

        // init pull-down-to-refresh
        binding.swipeRefresh.setOnRefreshListener {
            for (listener in listeners)
                listener.onRefresh()
        }

        // init recycler view
        binding.recycler.layoutManager = LinearLayoutManager(binding.root.context)
        questionsAdapter = QuestionsAdapter { clickedQuestion ->
            for (listener in listeners)
                listener.onQuestionClicked(clickedQuestion)
        }
        binding.recycler.adapter = questionsAdapter
    }

    fun bindQuestions(questions: List<Question>) {
        questionsAdapter.bindData(questions)
    }

    fun showProgressIndication() {
        binding.swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        binding.swipeRefresh.isRefreshing = false
    }

    class QuestionsAdapter(
        private val onQuestionClickListener: (Question) -> Unit
    ) : RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

        private var questionsList: List<Question> = ArrayList(0)

        fun bindData(questions: List<Question>) {
            questionsList = ArrayList(questions)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = LayoutQuestionListItemBinding.inflate(inflater, parent, false)
            return QuestionViewHolder(binding)
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.bind(questionsList[position])
        }

        override fun getItemCount(): Int = questionsList.size

        inner class QuestionViewHolder(private val binding: LayoutQuestionListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(question: Question) {
                binding.txtTitle.text = question.title
                binding.root.setOnClickListener { onQuestionClickListener(question) }
            }
        }
    }
}