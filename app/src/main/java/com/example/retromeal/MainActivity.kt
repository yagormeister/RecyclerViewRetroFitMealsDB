package com.example.retromeal

import MealRepository
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    private lateinit var mealRepository: MealRepository // Inicialización posterior con lateinit
    private lateinit var searchButton: Button
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        mealRepository = MealRepository(this) // Asegúrate de que el contexto esté disponible

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mealAdapter = MealAdapter(this, mutableListOf(), object : MealAdapter.Listener {
            override fun onMealClick(favoriteMeal: FavoriteMeal) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("EXTRA_MEAL_ID", favoriteMeal.idMeal)
                    putExtra("EXTRA_MEAL_NAME", favoriteMeal.mealName)
                    // Incluye extras adicionales según sea necesario
                }
                startActivity(intent)
            }

            override fun onFavoriteClicked(favoriteMeal: FavoriteMeal) {
                CoroutineScope(Dispatchers.IO).launch {
                    val isFavoriteNow = mealRepository.toggleFavoriteStatus(favoriteMeal)
                    withContext(Dispatchers.Main) {
                        if (isFavoriteNow) {
                            Toast.makeText(this@MainActivity, "${favoriteMeal.mealName} agregado a favoritos", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "${favoriteMeal.mealName} eliminado de favoritos", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })

        recyclerView.adapter = mealAdapter

        searchEditText = findViewById(R.id.etBusqueda)
        searchButton = findViewById(R.id.btBusqueda)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                loadMeals(query)
            }
        }
    }

    private fun loadMeals(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userId = "staticUserId" // Reemplaza esto según tu lógica de autenticación
            val favoriteMeals = mealRepository.getAndStoreFavoriteMeals(query, userId)
            withContext(Dispatchers.Main) {
                mealAdapter.updateMeals(favoriteMeals)
            }
        }
    }
}
