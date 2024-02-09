package com.example.retromeal
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/*
La clase Meal en tu código es una clase de datos (data class) en Kotlin, que se utiliza para modelar
 la estructura de una comida como se recibe desde una API, en este caso, probablemente una API que
 proporciona información sobre recetas o comidas. Esta clase se utiliza en conjunto con Gson,
 una biblioteca de serialización/deserialización de JSON, para convertir automáticamente los datos JSON de la API en objetos de Kotlin.*/
data class Meal(
    @PrimaryKey @SerializedName("idMeal") val idMeal: String,
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strDrinkAlternate") val strDrinkAlternate: String?,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strArea") val strArea: String,
    @SerializedName("strInstructions") val strInstructions: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("strTags") val strTags: String?,
    @SerializedName("strYoutube") val strYoutube: String?,
    @SerializedName("strIngredient1") val strIngredient1: String?,

    )
