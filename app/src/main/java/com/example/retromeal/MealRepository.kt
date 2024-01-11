class MealRepository {

    /*
La clase MealRepository en tu código actúa como una capa de abstracción entre la fuente
 de datos de tu aplicación (en este caso, una API web) y la lógica de tu aplicación.
 Esta clase utiliza Retrofit para realizar llamadas a la API y
 manejar los datos recibidos. Veamos cada componente de esta clase:*/
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
