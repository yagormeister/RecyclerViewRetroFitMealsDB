import retrofit2.http.GET
import retrofit2.http.Query

interface TheMealDBApi {

    @GET("search.php")
    suspend fun searchMeals(@Query("f") query: String): MealResponse
}
