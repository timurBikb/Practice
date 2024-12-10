package com.example.fitappnew

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

object UserSession {
    private var currentUser: FirebaseUser? = null
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun logout() {
        auth.signOut()
        currentUser = null
    }

    fun getUserData(onSuccess: (UserData) -> Unit, onError: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val userData = UserData(
                        id = userId,
                        name = document.getString("name") ?: "",
                        email = document.getString("email") ?: "",
                        createdAt = document.getLong("createdAt") ?: 0
                    )
                    onSuccess(userData)
                }
            }
            .addOnFailureListener { e ->
                onError(e)
            }
    }
}

data class UserData(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: Long
)