package com.example.retromeal

import MealRepository
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

        // Inicializando MealRepository aquí para asegurar que el contexto de la aplicación está disponible
        mealRepository = MealRepository(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Implementación del Listener dentro de MealAdapter
        mealAdapter = MealAdapter(mutableListOf(), object : MealAdapter.Listener {
            override fun onMealClick(favoriteMeal: FavoriteMeal) {
                // Navegar a DetailActivity con los detalles de la comida seleccionada
                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("EXTRA_MEAL_ID", favoriteMeal.idMeal)
                    putExtra("EXTRA_MEAL_NAME", favoriteMeal.mealName)
                    // Agrega otros extras según sea necesario
                }
                startActivity(intent)
            }

            override fun onFavoriteClicked(favoriteMeal: FavoriteMeal) {
                CoroutineScope(Dispatchers.IO).launch {
                    // Supongamos que tienes una función en MealRepository que maneja la lógica de favoritos
                    // Esta función podría ser algo como toggleFavoriteStatus que agrega o elimina la comida de los favoritos
                    val isFavoriteNow = mealRepository.toggleFavoriteStatus(favoriteMeal)

                    withContext(Dispatchers.Main) {
                        // Actualiza la UI basándote en si la comida es ahora un favorito o no
                        // Por ejemplo, podrías mostrar un Toast o actualizar el icono de favoritos
                        if (isFavoriteNow) {
                            Toast.makeText(this@MainActivity, "${favoriteMeal.mealName} agregado a favoritos", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "${favoriteMeal.mealName} eliminado de favoritos", Toast.LENGTH_SHORT).show()
                        }

                        // Opcional: Si tu lista necesita reflejar inmediatamente los cambios de estado de favoritos,
                        // podrías necesitar recargar los datos o actualizar el ítem específico en el adaptador.
                    }
                }
            }

        })
        recyclerView.adapter = mealAdapter

        searchEditText = findViewById(R.id.etBusqueda)
        searchButton = findViewById(R.id.btBusqueda)

        // Establecer el OnClickListener para el botón de búsqueda
        searchButton.setOnClickListener {
            val query = searchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                loadMeals(query)
            }
        }
    }

    private fun loadMeals(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            // Suponiendo un 'userId' estático para este ejemplo
            val userId = "staticUserId"
            val favoriteMeals = mealRepository.getAndStoreFavoriteMeals(query, userId)
            withContext(Dispatchers.Main) {
                // Actualizar el adaptador con las comidas favoritas recuperadas
                mealAdapter.updateMeals(favoriteMeals)
            }
        }
    }
}
