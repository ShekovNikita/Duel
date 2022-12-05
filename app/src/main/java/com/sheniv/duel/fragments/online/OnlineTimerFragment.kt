package com.sheniv.duel.fragments.online

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.database.room.Player
import com.sheniv.duel.databinding.FragmentOnlineTimerBinding
import com.sheniv.duel.extantion.*
import com.sheniv.duel.firebase.*
import com.sheniv.duel.models.UserOnline

class OnlineTimerFragment : BaseFragment<FragmentOnlineTimerBinding>() {

    var timer: CountDownTimer? = null
    var counter = 0

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentOnlineTimerBinding.inflate(inflater, container, false)

    override fun FragmentOnlineTimerBinding.onBindView(savedInstanceState: Bundle?) {
        bottomNavigationView.beGone()

        var currentUser = UserOnline()

        REF_DATABASE_ROOT.child(NODE_PLAYERS_ONLINE).child(USER_FIREBASE.id)
            .addListenerForSingleValueEvent(AppValueEventListener {
                currentUser = it.getValue(UserOnline::class.java) ?: UserOnline()
            })

        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(seconds: Long) {
                timerText.text = String.format("%1d:%03d", seconds / 1000, seconds % 1000)
                playerName.text = getString(R.string.stop)
                playerName.isClickable = true
                playerName.setOnClickListener {
                    this.cancel()
                    counter = seconds.toInt()
                    timerText.text = String.format("%1d:%03d", counter / 1000, counter % 1000)

                    REF_DATABASE_ROOT.child(NODE_PLAYERS_ONLINE)
                        .child(USER_FIREBASE.id)
                        .updateChildren(mapOf(
                            CHILD_TIMER to counter,
                            CHILD_TIMER_GAMES to currentUser.timer_games + 1
                        ))

                    returnToChampionship()
                }
            }

            override fun onFinish() {
                timerText.text = getString(R.string.time_is_over)
                playerName.isClickable = false

                REF_DATABASE_ROOT.child(NODE_PLAYERS_ONLINE)
                    .child(USER_FIREBASE.id)
                    .updateChildren(mapOf(
                        CHILD_TIMER to 5000,
                        CHILD_TIMER_GAMES to currentUser.timer_games + 1
                    ))

                returnToChampionship()
            }
        }

        btnStart.setOnClickListener {
            playerName.isClickable = true
            timer?.start()
            btnStart.beGone()
        }
    }

    private fun returnToChampionship() {
        with(binding) {
            btnStart.beVisible()
            btnStart.setText(R.string.continued)
            btnStart.setOnClickListener { navController.popBackStack() }
        }
    }

    override fun onDestroyView() {
        bottomNavigationView.beVisible()
        super.onDestroyView()
    }
}