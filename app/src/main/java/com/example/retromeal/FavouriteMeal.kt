package com.example.retromeal
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_meals")
data class FavoriteMeal(
    @PrimaryKey val idMeal: String,
    val mealName: String,
    val userId: String,  // Suponiendo que tienes un sistema de usuarios
    val mealThumb: String,
    val mealCategory: String,
    val mealArea: String,
    val mealInstructions: String
)
