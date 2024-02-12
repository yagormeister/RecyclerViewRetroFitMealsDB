package com.example.retromeal

import FavoriteMealDao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retromeal.DDBB.MealDAO

@Database(entities = [Meal::class, FavoriteMeal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDAO
    abstract fun favoriteMealDao(): FavoriteMealDao
}
