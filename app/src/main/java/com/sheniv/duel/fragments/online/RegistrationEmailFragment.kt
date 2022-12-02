package com.sheniv.duel.fragments.online

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.sheniv.duel.R
import com.sheniv.duel.base.BaseFragment
import com.sheniv.duel.databinding.FragmentRegistrationEmailBinding
import com.sheniv.duel.extantion.showToast
import com.sheniv.duel.firebase.*

const val RC_SIGN_IN = 100

class RegistrationEmailFragment : BaseFragment<FragmentRegistrationEmailBinding>() {
    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentRegistrationEmailBinding.inflate(inflater, container, false)

    override fun FragmentRegistrationEmailBinding.onBindView(savedInstanceState: Bundle?) {

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSingInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)
        googleSingInClient.revokeAccess()

        signInButton.setOnClickListener {
            val intent = googleSingInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
            }
        }
    }

    fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        val credental = GoogleAuthProvider.getCredential(account!!.idToken, null)
        AUTH.signInWithCredential(credental)
            .addOnSuccessListener { authResult ->
                val email = AUTH.currentUser?.email.toString()
                val uid = AUTH.currentUser?.uid.toString()
                REF_DATABASE_ROOT.child(NODE_USER).child(uid)
                    .updateChildren(mapOf(
                        CHILD_EMAIL to email,
                        CHILD_ID to uid,
                        CHILD_NAME to AUTH.currentUser?.displayName.toString(),
                        CHILD_PHOTO_URL to AUTH.currentUser?.photoUrl.toString()
                    ))
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.e("email", "---------$email")
                        }
                    }
                /*if (authResult.additionalUserInfo!!.isNewUser) {
                    showToast("Created\n$email")
                } else {
                    showToast("LoggedIn\n$email")
                }*/
                navController.navigate(R.id.navigation_championship)
                //requireActivity().finish()
            }
            .addOnFailureListener { e ->
                showToast("LoggedIn\n${e.message}")
            }
    }
}