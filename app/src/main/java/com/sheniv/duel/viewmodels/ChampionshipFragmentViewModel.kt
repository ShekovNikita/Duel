package com.sheniv.duel.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sheniv.duel.firebase.NODE_PLAYERS_ONLINE
import com.sheniv.duel.firebase.REF_DATABASE_ROOT
import com.sheniv.duel.firebase.initFirebase
import com.sheniv.duel.models.UserOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChampionshipFragmentViewModel: ViewModel() {

    val allPlayers = arrayListOf<UserOnline>()

    private val _playersLiveData = MutableLiveData<List<UserOnline>>()
    val playersLiveData: LiveData<List<UserOnline>> = _playersLiveData

    init {

        viewModelScope.launch(Dispatchers.Main) {
            REF_DATABASE_ROOT.child(NODE_PLAYERS_ONLINE)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        allPlayers.clear()
                        if (snapshot.exists()) {
                            for (favorite in snapshot.children) {
                                allPlayers.add(
                                    favorite.getValue(UserOnline::class.java) ?: UserOnline()
                                )
                            }
                        }
                        _playersLiveData.postValue(allPlayers)
                        Log.e("flowers", "$allPlayers")
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }

    }
}