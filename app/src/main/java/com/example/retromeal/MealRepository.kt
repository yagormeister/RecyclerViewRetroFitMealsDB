import android.content.Context
import androidx.room.Room
import com.example.retromeal.AppDatabase
import com.example.retromeal.FavoriteMeal
import com.example.retromeal.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MealRepository(context: Context) {

    private val db: AppDatabase by lazy {
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").fallbackToDestructiveMigration().build()
    }

    private val favoriteMealDao = db.favoriteMealDao()

    private val apiService = RetrofitService.theMealDBApi

    suspend fun getAndStoreFavoriteMeals(letter: String, userId: String): List<FavoriteMeal> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchMeals(letter)
            val mealsFromApi = response.meals ?: emptyList()

            mealsFromApi.forEach { meal ->
                val favoriteMeal = FavoriteMeal(
                    idMeal = meal.idMeal,
                    mealName = meal.strMeal,
                    userId = userId,
                    mealThumb = meal.strMealThumb,
                    mealCategory = meal.strCategory,
                    mealArea = meal.strArea,
                    mealInstructions = meal.strInstructions
                )
                favoriteMealDao.addFavoriteMeal(favoriteMeal)
            }

            favoriteMealDao.getFavoriteMealsByUser(userId)
        } catch (e: Exception) {
            favoriteMealDao.getFavoriteMealsByUser(userId)
        }
    }

    // Métodos para manejar los favoritos
    suspend fun addFavoriteMeal(favoriteMeal: FavoriteMeal) = withContext(Dispatchers.IO) {
        favoriteMealDao.addFavoriteMeal(favoriteMeal)
    }

    suspend fun removeFavoriteMeal(favoriteMeal: FavoriteMeal) = withContext(Dispatchers.IO) {
        favoriteMealDao.removeFavoriteMeal(favoriteMeal)
    }
    suspend fun isFavorite(favoriteMeal: FavoriteMeal, userId: String): Boolean = withContext(Dispatchers.IO) {
        val result = favoriteMealDao.findFavoriteMealByIdAndUser(favoriteMeal.idMeal, userId)
        return@withContext result != null
    }
    suspend fun toggleFavoriteStatus(favoriteMeal: FavoriteMeal): Boolean = withContext(Dispatchers.IO) {
        // Primero, verifica si la comida ya está en la lista de favoritos
        val existingMeal = favoriteMealDao.findFavoriteMealByIdAndUser(favoriteMeal.idMeal, favoriteMeal.userId)

        if (existingMeal == null) {
            // Si la comida no está en favoritos, agrégala
            favoriteMealDao.addFavoriteMeal(favoriteMeal)
            true // Retorna true indicando que la comida ahora es favorita
        } else {
            // Si la comida ya está en favoritos, elimínala
            favoriteMealDao.removeFavoriteMeal(favoriteMeal)
            false // Retorna false indicando que la comida ya no es favorita
        }
    }
}
