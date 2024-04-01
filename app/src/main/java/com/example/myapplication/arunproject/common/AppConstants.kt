package com.example.myapplication.arunproject.common

object AppConstants {
    const val DATE_FORMAT = "dd MMM yyyy"

    //API Constants
    const val BASE_URL = "https://hf-android-app.s3-eu-west-1.amazonaws.com/android-test/"
    const val API_GET_PARAM =
        "recipes.json"

    //Error Messages
    const val NO_INTERNET_ERROR_MESSAGE = "Check your Internet Connection and try again."
    const val ERROR_API_FAILED = "Failed to fetch recipes, please try again later."
    const val RETRY = "Retry"

    const val CHOOSE_TEXT = "Choose a Recipe"
    const val ADD_TO_CART = "Add To Cart"
    const val VIEW_INFO = "View Info"
    const val RECIPE_DETAIL_TITLE = "Recipe Details"
    const val RECIPE_ROW = "Recipe Row"
    const val RECIPE_COLUMN = "Recipe Column"

    const val DB_NAME = "my_recipe_database.db"
    const val TABLE_NAME = "recipes"

    const val MY_INTRO =
        "Welcome to Recipe Realm. This app is built entirely using ->" +
                " Kotlin, Jetpack Compose, Kotlin Coroutines, StateFlow, Hilt, Retrofit, Room etc." +
                " following the best practices and design patterns such as " +
                " MVVM, Clean Architecture, Clean Code, SOLID principles.\n" +
                " Coming Soon: Jetpack Navigation, Firebase FireStore DB and Open-AI API integration, Stay Tuned!!"
}