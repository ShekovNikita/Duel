package com.sheniv.duel.fragments.online

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentOnlineStopwatchBinding
import com.sheniv.duel.extantion.AppValueEventListener
import com.sheniv.duel.extantion.beGone
import com.sheniv.duel.extantion.beVisible
import com.sheniv.duel.extantion.bottomNavigationView
import com.sheniv.duel.firebase.*
import com.sheniv.duel.models.UserOnline

class OnlineStopwatchFragment : BaseFragment<FragmentOnlineStopwatchBinding>() {

    var timer: CountDownTimer? = null
    var counter = 0

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnlineStopwatchBinding.inflate(inflater, container, false)

    override fun FragmentOnlineStopwatchBinding.onBindView(savedInstanceState: Bundle?) {
        bottomNavigationView.beGone()

        var currentUser = UserOnline()
        val fullWay = REF_DATABASE_ROOT.child(NODE_PLAYERS_ONLINE).child(USER_FIREBASE.id)

        fullWay.addListenerForSingleValueEvent(AppValueEventListener {
                currentUser = it.getValue(UserOnline::class.java) ?: UserOnline()
            })

        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(seconds: Long) {
                timerText.text = String.format("%1d:%03d", seconds / 1000, seconds % 1000)
            }

            override fun onFinish() {
                playerCounter.isClickable = false
                timerText.setText(R.string.time_is_over)

                if (currentUser.stopwatch_best < counter)
                    fullWay.updateChildren(
                        mapOf(
                            CHILD_STOPWATCH_LAST to counter,
                            CHILD_STOPWATCH_GAMES to currentUser.stopwatch_games + 1,
                            CHILD_STOPWATCH_BEST to counter
                        )
                    )
                else fullWay.updateChildren(
                    mapOf(
                        CHILD_STOPWATCH_LAST to counter,
                        CHILD_STOPWATCH_GAMES to currentUser.stopwatch_games + 1
                    )
                )

                btnStart.beVisible()
                btnStart.setText(R.string.continued)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        counter = 0
        with(binding) {
            playerCounter.textSize = 100f
            timerText.beVisible()
            timerText.text = "5:000"
            btnStart.beVisible()
            playerCounter.text = counter.toString()
        }
        binding.btnStart.setOnClickListener {
            binding.playerCounter.isClickable = true
            timer?.start()
            binding.btnStart.beGone()
            binding.playerCounter.setOnClickListener {
                counter++
                binding.playerCounter.text = counter.toString()

                binding.btnStart.setOnClickListener { navController.popBackStack() }
            }
        }
    }

    override fun onDestroyView() {
        bottomNavigationView.beVisible()
        super.onDestroyView()
    }
}