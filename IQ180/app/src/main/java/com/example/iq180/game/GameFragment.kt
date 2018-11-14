package com.example.iq180.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.iq180.R
import com.example.iq180.di.Injection
import kotlinx.android.synthetic.main.fragment_game.*
import net.objecthunter.exp4j.ExpressionBuilder

class GameFragment : Fragment(), GameContract.View {

    private lateinit var presenter: GameContract.Presenter

    private lateinit var roomName: String
    private lateinit var name: String
//    private lateinit var name2: String

    private var score = 0
//    private var score2 = 0

//    private var requestCode: Int = -1

    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMilliseconds: Long = 60000 // 1 min
    private var timerRunning: Boolean = false

    private var checkNoSlot = intArrayOf(0, 0, 0, 0, 0)
    private var correctAnswer = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = Injection.getGamePresenter(this)
        if (savedInstanceState == null) {
            onRestoreInstanceState(arguments)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvPlayerName1.text = name
//        tvPlayerName2.text = name2

        presenter.loadGameInfo(roomName)
        startTimer()
    }

    private fun startTimer() {
        if (timerRunning) countDownTimer.cancel()
        timeLeftInMilliseconds = 60000
        countDownTimer = object : CountDownTimer(timeLeftInMilliseconds, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMilliseconds = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                alertBoxSingle("GAME OVER", "Do you want to continue ?? \n Your score is $score")
            }
        }.start()

        timerRunning = true
    }

    private fun updateTimer() {
        var minutes = (timeLeftInMilliseconds / 60000).toInt()
        var seconds = (timeLeftInMilliseconds % 60000 / 1000).toInt()

        var timeLeftText: String = "" + minutes
        timeLeftText += ":"

        if (seconds < 10) timeLeftText += "0"
        timeLeftText += seconds

        gameTimer.text = timeLeftText
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        timerRunning = false
    }

    private fun alertBoxSingle(title: String, msg: String) {
        val alertDialog = activity!!.let { AlertDialog.Builder(it) }
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Play Again", { dialog, which ->
                    dialog.dismiss()
                    presenter.loadGameInfo(roomName)
                    score = 0
                    tvScore.text = "$score"
                    startTimer()
                })
                .setNegativeButton("End Game", { dialog, which ->
                    dialog.dismiss()
                    activity?.finish()
                })

        alertDialog.show()

    }
    override fun setScore(score1: Int, score2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showWaiting() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideWaiting(opponentTimeLeft: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRoundResult(winner: String, score1: Int, score2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun leaveGame() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setGameEnvironment(arrNum: ArrayList<String>, result: Int) {
        num1.text = arrNum[0]
        num2.text = arrNum[1]
        num3.text = arrNum[2]
        num4.text = arrNum[3]
        num5.text = arrNum[4]
        tvResult.text = "=  $result"
        correctAnswer = result
        num1.setOnClickListener(onClickNumBtn)
        num2.setOnClickListener(onClickNumBtn)
        num3.setOnClickListener(onClickNumBtn)
        num4.setOnClickListener(onClickNumBtn)
        num5.setOnClickListener(onClickNumBtn)

        btnPlus.setOnClickListener(onOperatorClickListener)
        btnMinus.setOnClickListener(onOperatorClickListener)
        btnDivide.setOnClickListener(onOperatorClickListener)
        btnMultiply.setOnClickListener(onOperatorClickListener)
        btnBracketLeft.setOnClickListener(onOperatorClickListener)
        btnBracketRight.setOnClickListener(onOperatorClickListener)

        btnClear.setOnClickListener(onClearBtn)
        btnCheck.setOnClickListener(onCheckBtn)

        btnQuitGame.setOnClickListener {
            activity?.finish()
        }
    }

    private fun playAgain() {
        presenter.loadGameInfo(roomName)
        resetQuestion()
        startTimer()
        tvInputAnswer.text = ""
    }

    private fun resetQuestion() {
        tvInputAnswer.text = ""
        for (i in 0 until 5) {
            checkNoSlot[i] = 0
        }
        num1.isEnabled = true
        num2.isEnabled = true
        num3.isEnabled = true
        num4.isEnabled = true
        num5.isEnabled = true
    }
    override fun getmActivity(): FragmentActivity? {
        return activity
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    private fun onRestoreInstanceState(arguments: Bundle?) {
        name = arguments!!.getString("name")
//        name2 = arguments.getString("name2")
//        requestCode = arguments.getInt("requestCode")
        roomName = arguments.getString("roomName")
    }

    /***
     *
     * Listener
     *
     */

    private val onClickNumBtn = View.OnClickListener { v ->
        when (v.id) {
            R.id.num1 -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + num1.text.toString()
                checkNoSlot[0] = 1
            }
            R.id.num2 -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + num2.text.toString()
                checkNoSlot[1] = 1
            }
            R.id.num3 -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + num3.text.toString()
                checkNoSlot[2] = 1
            }
            R.id.num4 -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + num4.text.toString()
                checkNoSlot[3] = 1
            }
            R.id.num5 -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + num5.text.toString()
                checkNoSlot[4] = 1
            }
        }

        num1.isEnabled = false
        num2.isEnabled = false
        num3.isEnabled = false
        num4.isEnabled = false
        num5.isEnabled = false
    }

    @SuppressLint("SetTextI18n")
    private val onOperatorClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btnPlus -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnPlus.text.toString()
            }
            R.id.btnMinus -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnMinus.text.toString()
            }
            R.id.btnMultiply -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnMultiply.text.toString()
            }
            R.id.btnDivide -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnDivide.text.toString()
            }
            R.id.btnBracketLeft -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnBracketLeft.text.toString()
            }
            R.id.btnBracketRight -> {
                tvInputAnswer.text = tvInputAnswer.text.toString() + btnBracketRight.text.toString()
            }
        }

        for (i in 0 until 5) {
            if (checkNoSlot[i] == 0) openButton(i)
        }
    }

    private fun openButton(x: Int) {
        when (x) {
            0 -> num1.isEnabled = true
            1 -> num2.isEnabled = true
            2 -> num3.isEnabled = true
            3 -> num4.isEnabled = true
            4 -> num5.isEnabled = true
        }
    }

    private val onClearBtn = View.OnClickListener {
        resetQuestion()
    }

    private val onCheckBtn = View.OnClickListener {
        try {
            var calc = ExpressionBuilder(tvInputAnswer.text.toString()).build()
            Log.e("onCheckBtn", tvInputAnswer.text.toString())
            var result: Int = calc.evaluate().toInt()
            if (result == correctAnswer) {
                Toast.makeText(activity, "CORRECT", Toast.LENGTH_SHORT).show()
                playAgain()
                tvScore.text = "${++score}"

            } else {
                Toast.makeText(activity, "WRONG", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Your equation is wrong", Toast.LENGTH_SHORT).show()
        }


    }

    companion object {

        private val gameFragment = GameFragment()

        fun newInstance(roomName: String, name: String, name2: String, requestCode: Int): GameFragment {
            val arg = Bundle()
            arg.putString("name", name)
//            arg.putString("name2", name2)
            arg.putString("roomName", roomName)
//            arg.putInt("requestCode", requestCode)
            gameFragment.arguments = arg
            return gameFragment
        }
    }

}