import retrofit2.http.GET
import retrofit2.http.Query
/*
La interfaz TheMealDBApi en tu código es una definición para hacer
 llamadas de red en una aplicación Android, utilizando la biblioteca Retrofit. Retrofit
  es una biblioteca tipo cliente HTTP que se utiliza para realizar solicitudes a APIs web de manera eficiente
y sencilla en aplicaciones Android y Java. Veamos qué significa cada parte de tu interfaz:*/
interface TheMealDBApi {

    @GET("search.php")
    suspend fun searchMeals(@Query("f") query: String): MealResponse
}
