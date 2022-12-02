package com.sheniv.duel.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.sheniv.duel.models.UserFirebase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var REF_STORAGE_ROOT: StorageReference
lateinit var USER_FIREBASE: UserFirebase
lateinit var CURRENT_UID: String

//Node
const val NODE_USER = "users"
const val NODE_PLAYERS_ONLINE = "players"

//User
const val CHILD_ID = "id"
const val CHILD_EMAIL = "email"
const val CHILD_NAME = "name"
const val CHILD_PHOTO_URL = "photoUrl"

//Player Online
const val CHILD_STOPWATCH = "stopwatch"
const val CHILD_STOPWATCH_GAMES = "stopwatch_games"
const val CHILD_STOPWATCH_BEST = "stopwatch_best"
const val CHILD_TIMER = "timer"
const val CHILD_TIMER_GAMES = "timer_games"
const val CHILD_TIMER_BEST = "timer_best"

fun initFirebase() {
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    REF_STORAGE_ROOT = FirebaseStorage.getInstance().reference
}