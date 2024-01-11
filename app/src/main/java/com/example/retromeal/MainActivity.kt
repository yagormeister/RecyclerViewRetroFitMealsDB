package com.example.retromeal
import Meal
import MealAdapter
import MealRepository
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter
    private val mealRepository = MealRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mealAdapter = MealAdapter(emptyList(), this)
        recyclerView.adapter = mealAdapter

        loadMeals()
    }

    private fun loadMeals() {
        CoroutineScope(Dispatchers.IO).launch {
            val meals = mealRepository.getMeals("c")
            withContext(Dispatchers.Main) {
                mealAdapter = MealAdapter(meals, this@MainActivity)
                recyclerView.adapter = mealAdapter

                // Envía un broadcast una vez que los datos están cargados
                sendBroadcast(Intent("com.example.retromeal.DATA_LOADED"))
            }
        }
    }

    fun onMealClick(meal: Meal) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("EXTRA_MEAL_ID", meal.idMeal)
            putExtra("EXTRA_MEAL_NAME", meal.strMeal)
            putExtra("EXTRA_MEAL_CATEGORY", meal.strCategory)
            putExtra("EXTRA_MEAL_AREA", meal.strArea)
            putExtra("EXTRA_MEAL_INSTRUCTIONS", meal.strInstructions)
            putExtra("EXTRA_MEAL_IMAGE", meal.strMealThumb)
            // Añade más extras según sea necesario
        }
        startActivity(intent)
    }
}
