package com.andreick.dependencyinjection.screens.questionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreick.dependencyinjection.R
import com.andreick.dependencyinjection.databinding.LayoutQuestionListItemBinding
import com.andreick.dependencyinjection.databinding.LayoutQuestionsListBinding
import com.andreick.dependencyinjection.questions.Question

class QuestionsListViewMvc(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
) {
    interface Listener {
        fun onRefresh()
        fun onQuestionClicked(clickedQuestion: Question)
    }

    private val binding = LayoutQuestionsListBinding.inflate(layoutInflater, parent, false)
    val rootView = binding.root

    private val listeners = HashSet<Listener>()
    private val questionsAdapter: QuestionsAdapter

    init {
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

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
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