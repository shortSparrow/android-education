package com.example.soccerquiz

import android.content.res.Resources
import android.icu.number.IntegerWidth
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.soccerquiz.databinding.FragmentQuizBinding
import android.util.DisplayMetrics
import android.view.animation.AnimationUtils
import android.widget.ImageView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    private val quizItems: MutableList<QuizItem> = mutableListOf(
        QuizItem(
            "How many players does each team have on the pitch when a soccer match starts?",
            listOf("11", "8", "12")
        ),
        QuizItem(
            "What should be the circumference of a Size 5 (adult) football?",
            listOf("27\" to 28\"", "24\" to 25\"", "23\" to 24\"")
        ),
        QuizItem(
            "What is given to a player for a very serious personal foul on an opponent?",
            listOf("Red Card", "Green Card", "Yellow Card")
        ),
        QuizItem(
            "To most places in the world, soccer is known as what?",
            listOf("Football", "Footgame", "Legball")
        ),
        QuizItem(
            "Offside. If a player is offside, what action does the referee take?",
            listOf(
                "Awards an indirect free kick to the opposing team",
                "Awards a penalty to the opposing team",
                "Awards a yellow card to the player"
            )
        ),
        QuizItem(
            "What should be the circumference of a Size 5 (adult) football?",
            listOf("17", "11", "23")
        ),
        QuizItem(
            "Excluding the goalkeeper, what part of the body cannot touch the ball?",
            listOf("Arm", "Head", "Shoulder")
        ),
        QuizItem(
            "What is it called when a player, without the ball on the offensive team is behind the last defender, or fullback?",
            listOf("Offside", "Outside", "Field-side")
        ),
        QuizItem(
            "The Ball. The circumference of the ball should not be greater than what?",
            listOf("70", "80", "90")
        ),
        QuizItem(
            "How many minutes are played in a regular game (without injury time or extra time)?",
            listOf("90", "95", "100")
        ),
        QuizItem(
            "What statement describes a proper throw-in?",
            listOf(
                "Both hands must be on the ball behind the head, both feet must be on the ground",
                "Both hands must be on the ball behind the head",
                "Both feet must be on the ground"
            )
        ),
        QuizItem(
            "How big is a regulation official soccer goal?",
            listOf("2.44m high, 7.32m wide", "2.55m high, 7.62m wide", "2.33m high, 8.15m wide")
        )
    )

    lateinit var binding: FragmentQuizBinding
    lateinit var currentQuizItem: QuizItem
    lateinit var answers: MutableList<String>
    private var quizItemIndex = 0;
    private val numberOfQuestions = 3
    private val ANIMATION_DURATION: Long = 600


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_quiz, container, false
        )
        getRandomQuizItem()
        binding.quizFragment = this

        setHasOptionsMenu(true) // add menu to this fragment


        binding.passButton.setOnClickListener { view: View ->
            val radioGroup = binding.quizRadioGroup
            val selectedCheckboxId = radioGroup.checkedRadioButtonId
            val answerIndex = radioGroup.indexOfChild(radioGroup.findViewById(selectedCheckboxId))

            if (selectedCheckboxId != -1) {
                if (answers[answerIndex] == currentQuizItem.answerList[0]) { // because first answer is correct
                    quizItemIndex++

                    if (quizItemIndex < numberOfQuestions) {

                        setQuizItem()
                        radioGroup.check(radioGroup.getChildAt(0).id) // set checked the first radio button
                        binding.invalidateAll() // show all changes in layout

                    } else {
                        animateBall()
                        Handler().postDelayed({
                            // User gave 3 correct answers. Go to GoalFragment
                            view.findNavController()
                                .navigate(R.id.action_quizFragment_to_goalFragment)
                        }, ANIMATION_DURATION)
                    }
                } else {
                    animateBall()
                    // User gave 3 incorrect answer. Go to MissFragment
                    Handler().postDelayed({
                        view.findNavController().navigate(R.id.action_quizFragment_to_missFragment)
                    }, ANIMATION_DURATION)
                }
            }

        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getRandomQuizItem() {
        quizItems.shuffle()
        quizItemIndex = 0
        setQuizItem()
    }

    private fun setQuizItem() {
        currentQuizItem = quizItems[quizItemIndex]
        answers = currentQuizItem.answerList.toMutableList()
        answers.shuffle()
    }

    fun animateBall() {
        val width = Resources.getSystem().displayMetrics.widthPixels
        val destination = width / 2 + binding.ball.width / 2

        binding.ball.animate().translationXBy(destination.toFloat()).rotation(360f).duration =
            ANIMATION_DURATION
    }
}