package com.example.myapplication.recipescreen.data.datasource.remote

import com.example.myapplication.recipescreen.common.RecipeScreenConstants.FIREBASE_COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * This class is the implementation of the [FirebaseRemoteDataSource] interface.
 * For testing purpose we just have one method to get the recipe details.
 * @param fireStore The instance of the [FirebaseFirestore] to interact with the Firebase.
 */
class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : FirebaseRemoteDataSource {

    override suspend fun getRecipeSampleQuestionsFromFirebase(recipeId: String): List<String> {

        return suspendCoroutine { continuation ->
            fireStore.collection(FIREBASE_COLLECTION_NAME)
                .document(recipeId)
                .get()
                .addOnSuccessListener { document ->
                    continuation.resume(document.data?.values?.mapNotNull { it as? String }
                        ?: emptyList())
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }
}