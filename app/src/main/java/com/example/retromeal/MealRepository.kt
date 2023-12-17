class MealRepository {

    private val apiService = RetrofitService.theMealDBApi

    suspend fun getMeals(letter: String): List<Meal> {
        return try {
            val response = apiService.searchMeals(letter)
            response.meals ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
