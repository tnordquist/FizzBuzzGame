package edu.cnm.deepdive.fizzbuzzgame

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivityKt : AppCompatActivity() {
    private val rng: Random = Random()
    private var value: Int = 0;
    private var activeCorrect: Int = 0;
    private var passiveCorrect: Int = 0;
    private var incorrect: Int = 0;

    private val test: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) { // ? after Bundle makes it null.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startTimer()

        val list: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        list.forEachIndexed { index, i ->
        }

        val list2 = list.filter {
            it > 4
        }

    }

    private fun startTimer() {
        Timer().apply {
            start()
        }

    }
        private fun updateTally() {
            val isFizz = (value % 3 == 0)
            val isBuzz = (value % 5 == 0)
            val fizzCorrect = (isFizz == fizz_toggle?.isChecked) // ? says do this if fizz_toggle not null
            val buzzCorrect = (isBuzz == buzz_toggle?.isChecked)
            if (!(fizzCorrect && buzzCorrect)) {
            } else if (isFizz || isBuzz) {
                activeCorrect++;
            } else {
                passiveCorrect++;
            }
        }


        private fun updateTallyDisplay() {
            active_correct_view?.text=getString(R.string.active_correct, activeCorrect))
            passive_correct_view?.text = getString(R.string.passive_correct, passiveCorrect)
            incorrect_view?.text = getString(R.string.incorrect, incorrect)
        }

        private fun updateView() {
            value = 1 + rng.nextInt(UPPER_BOUND)
            runOnUiThread {
                updateTally()
                number_view?.text = value.toString()
                fizz_toggle?.isChecked = false
                buzz_toggle?.isChecked = false
            }
        }

        inner class Timer : Thread() {
            override fun run() {
                try {
                    updateView()
                    Thread.sleep(TIMEOUT_INTERVAL.toLong())
                } catch (e: InterruptedException) {

                }
            }
        }

        companion object {  // anything in here is a static method accessible inside or outside
            private const val UPPER_BOUND = 99 // No type had to be declared
            private const val TIMEOUT_INTERVAL = 5000
        }
    }
