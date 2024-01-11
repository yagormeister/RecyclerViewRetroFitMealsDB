import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
/*
* bjeto singleton en Kotlin, lo que significa que se crea una única instancia de este objeto
* en toda la aplicación. Este objeto se utiliza para configurar y proporcionar una instancia del cliente Retrofit,
* que es una biblioteca HTTP muy utilizada en Android y Java para realizar llamadas de red, especialmente API REST.*/
object RetrofitService {

    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val theMealDBApi: TheMealDBApi = getRetrofit().create(TheMealDBApi::class.java)
}
