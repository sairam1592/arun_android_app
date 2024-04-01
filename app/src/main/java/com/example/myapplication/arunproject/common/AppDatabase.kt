package com.example.myapplication.arunproject.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.arunproject.data.model.db.RecipeDao
import com.example.myapplication.arunproject.data.model.db.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}