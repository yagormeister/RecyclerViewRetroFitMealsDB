import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.retromeal.FavoriteMeal

@Dao
interface FavoriteMealDao {
    @Insert
    suspend fun addFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Delete
    suspend fun removeFavoriteMeal(favoriteMeal: FavoriteMeal)

    @Query("SELECT * FROM favorite_meals WHERE userId = :userId")
    suspend fun getFavoriteMealsByUser(userId: String): List<FavoriteMeal>
}
