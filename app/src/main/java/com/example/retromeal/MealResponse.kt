import com.example.retromeal.Meal
import com.google.gson.annotations.SerializedName
//representar la estructura de la respuesta JSON de una solicitud de red,
// específicamente en el contexto de trabajar con Retrofit y Gson en una aplicación Android.
data class MealResponse(
    @SerializedName("meals") val meals: List<Meal>
)
