package com.example.iq180.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
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
import com.example.iq180.lobby.LobbyFragment
import kotlinx.android.synthetic.main.fragment_game.*
import net.objecthunter.exp4j.ExpressionBuilder

class GameMultiplayFragment : Fragment(), GameContract.View {

    // TODO: can set time

    private lateinit var roomName: String
    private lateinit var name: String
    private lateinit var name2: String

    private var opponentTimeLeft = ""

    private lateinit var countDownTimer: CountDownTimer
    private var timeLeftInMilliseconds: Long = 60000 // 1 min
    //    private var timeLeftInMilliseconds: Long = 10000 // 10 s
    private var timerRunning: Boolean = false

    private var checkNoSlot = intArrayOf(0, 0, 0, 0, 0)
    private var correctAnswer = -1

    private var alertDialog: AlertDialog? = null

    private lateinit var presenter: GameContract.Presenter

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
        tvPlayerName2.text = name2

        tvScore2.text = "${0}"

        presenter.loadGameInfo(roomName, name)

    }

    override fun getmActivity(): FragmentActivity? {
        return activity
    }

    private fun startTimer() {
        if (timerRunning) countDownTimer.cancel()

        countDownTimer = object : CountDownTimer(timeLeftInMilliseconds, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMilliseconds = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                Log.e("onFinish", LobbyFragment.player.turn.toString())
                when (LobbyFragment.player.turn) {
                    1 -> presenter.changeTurnNotAnswer(roomName)

                    2 -> {
                        if (opponentTimeLeft == "") presenter.sendRoundResult(roomName, "")
                        else {
                            if (LobbyFragment.player.name == name) presenter.sendRoundResult(roomName, name2)
                            else presenter.sendRoundResult(roomName, name)
                        }
                    }
                }
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

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

    override fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
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
        resetQuestion()
        if (alertDialog != null) alertDialog!!.dismiss()
        timeLeftInMilliseconds = 20000
        startTimer()

        btnPauseTime.setOnClickListener {
            stopTimer()
            btnPauseTime.isEnabled = false
            Handler().postDelayed({
                startTimer()
            },5000)
        }
    }

    override fun showWaiting() {
        waitingScreen.visibility = View.VISIBLE
        stopTimer()
    }

    override fun hideWaiting(opponentTimeLeft: String) {
        this.opponentTimeLeft = opponentTimeLeft
        waitingScreen.visibility = View.GONE
        startTimer()
    }

    override fun showRoundResult(winner: String, score1: Int, score2: Int) {
        showRoundResultDialog(winner, score1, score2)

        if (waitingScreen.visibility == View.VISIBLE) waitingScreen.visibility = View.GONE

        tvScore.text = "$score1"
        tvScore2.text = "$score2"
    }

    override fun leaveGame() {
        alertDialog?.dismiss()
        activity?.finish()
    }

    override fun setScore(score1: Int, score2: Int) {
        tvScore.text = "$score1"
        tvScore2.text = "$score2"
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

    private fun onRestoreInstanceState(arguments: Bundle?) {
        name = arguments!!.getString("name")
        name2 = arguments.getString("name2")
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
                stopTimer()
                when (LobbyFragment.player.turn) {
                    1 -> {
                        presenter.changeTurnAnswer(roomName, timeLeftInMilliseconds.toString())
                    }

                    2 -> {
                        if (opponentTimeLeft == "") presenter.sendRoundResult(roomName,
                                LobbyFragment.player.name)
                        else {
                            Log.e("onCheckBtn", "$opponentTimeLeft : $timeLeftInMilliseconds")
                            if (opponentTimeLeft.toInt() < timeLeftInMilliseconds.toInt()) presenter.sendRoundResult(roomName,
                                    LobbyFragment.player.name)
                            else {
                                if (LobbyFragment.player.name == name) presenter.sendRoundResult(roomName, name2)
                                else presenter.sendRoundResult(roomName, name)
                            }
                        }
                    }
                }
                Toast.makeText(activity, "CORRECT $timeLeftInMilliseconds", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(activity, "WRONG", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Your equation is wrong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showRoundResultDialog(winner: String, score1: Int, score2: Int) {
        var title = ""
        title = if (winner == "") "Draw this round"
        else "$winner win this round"

        val alertDialogBuilder = activity?.let {
            AlertDialog.Builder(it)
                    .setTitle(title)
                    .setCancelable(true)
                    .setMessage("Current score $score1 : $score2")
                    .setPositiveButton("Next Round", { dialog, which ->
                        presenter.loadNewRoundInfo(roomName, false)
                    })
                    .setNeutralButton("Rematch", { dialog, which ->
                        presenter.loadNewRoundInfo(roomName, true)
                    })
                    .setNegativeButton("End Game", { dialog, which ->
                        presenter.quitGame(roomName)
                        // TODO : need only one client to end game for both side
                    })
        }
        alertDialog = alertDialogBuilder?.create()
        alertDialog?.show()
    }

    companion object {

        private val gameMultiplayFragment = GameMultiplayFragment()

        fun newInstance(roomName: String, name: String, name2: String): GameMultiplayFragment {
            val arg = Bundle()
            arg.putString("name", name)
            arg.putString("name2", name2)
            arg.putString("roomName", roomName)
            gameMultiplayFragment.arguments = arg
            return gameMultiplayFragment
        }

    }

}