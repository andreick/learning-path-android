package com.andreick.quizapp

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity() {

    private lateinit var pbQuiz: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var ivQuestionImage: ImageView
    private lateinit var tvOptionOne: TextView
    private lateinit var tvOptionTwo: TextView
    private lateinit var tvOptionThree: TextView
    private lateinit var tvOptionFour: TextView
    private lateinit var btnAction: Button

    private lateinit var username: String
    private lateinit var optionList: List<TextView>

    private val questionList: ArrayList<Question> = Constants.getQuestions()
    private var currentQuestionPosition = 1
    private var currentSelectedOptionPosition = 0
    private var previousSelectedOptionPosition = 0
    private var correctAnswersAmount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        username = intent.getStringExtra(Constants.USER_NAME) ?: ""

        pbQuiz = findViewById(R.id.pbQuiz)
        tvProgress = findViewById(R.id.tvQuestionProgress)
        tvQuestion = findViewById(R.id.tvQuestion)
        ivQuestionImage = findViewById(R.id.ivQuestionImage)
        tvOptionOne = findViewById(R.id.tvQuestionOptionOne)
        tvOptionTwo = findViewById(R.id.tvQuestionOptionTwo)
        tvOptionThree = findViewById(R.id.tvQuestionOptionThree)
        tvOptionFour = findViewById(R.id.tvQuestionOptionFour)
        btnAction = findViewById(R.id.btnQuestionAction)

        optionList = arrayListOf(tvOptionOne, tvOptionTwo, tvOptionThree, tvOptionFour)

        tvOptionOne.setOnClickListener { handleOptionClick(it) }
        tvOptionTwo.setOnClickListener { handleOptionClick(it) }
        tvOptionThree.setOnClickListener { handleOptionClick(it) }
        tvOptionFour.setOnClickListener { handleOptionClick(it) }
        btnAction.setOnClickListener { handleBtnActionClick() }

        pbQuiz.max = questionList.size
        setQuestion()
    }

    private fun handleOptionClick(view: View) {
        previousSelectedOptionPosition = currentSelectedOptionPosition
        if (previousSelectedOptionPosition > 0) setDefaultOptionView(optionList[previousSelectedOptionPosition - 1])
        currentSelectedOptionPosition = optionList.indexOf(view) + 1
        if (currentSelectedOptionPosition > 0) setSelectedOptionView(optionList[currentSelectedOptionPosition - 1])
        btnAction.isEnabled = true
    }

    private fun handleBtnActionClick() {
        if (currentSelectedOptionPosition > 0) {
            val question = questionList[currentQuestionPosition - 1]

            if (question.correctAnswerPosition == currentSelectedOptionPosition) {
                correctAnswersAmount++
            }
            else {
                setWrongAnswerView(optionList[currentSelectedOptionPosition - 1])
            }

            setCorrectAnswerView(optionList[question.correctAnswerPosition - 1])

            btnAction.text = if (currentQuestionPosition == questionList.size) "Finish" else "GO TO NEXT QUESTION"

            currentSelectedOptionPosition = 0
            previousSelectedOptionPosition = 0
        }
        else {
            if (currentQuestionPosition < questionList.size) {
                currentQuestionPosition++
                setQuestion()
            }
            else {
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constants.USER_NAME, username)
                intent.putExtra(Constants.CORRECT_ANSWERS, correctAnswersAmount)
                intent.putExtra(Constants.TOTAL_QUESTIONS, questionList.size)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun setQuestion() {
        optionList.forEach { setDefaultOptionView(it) }

        val question = questionList[currentQuestionPosition - 1]
        pbQuiz.progress = currentQuestionPosition
        tvProgress.text = "${pbQuiz.progress} / ${pbQuiz.max}"
        tvQuestion.text = question.question
        ivQuestionImage.setImageResource(question.image)
        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
        btnAction.isEnabled = false
    }

    private fun setDefaultOptionView(tv: TextView) {
        tv.setTextColor(ContextCompat.getColor(this, R.color.gray))
        tv.typeface = Typeface.DEFAULT
        tv.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
    }

    private fun setSelectedOptionView(tv: TextView) {
        tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun setCorrectAnswerView(tv: TextView) {
        tv.setTextColor(ContextCompat.getColor(this, R.color.dark_gray))
        tv.background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
    }

    private fun setWrongAnswerView(tv: TextView) {
        tv.background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
    }
}