import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retromeal.MainActivity
import com.example.retromeal.R
import com.squareup.picasso.Picasso
/*La clase MealAdapter en el contexto de tu aplicación es un adaptador personalizado para un
RecyclerView. Esta clase es una parte crucial para mostrar una lista de
 elementos en una interfaz de usuario en aplicaciones Android*/
class MealAdapter(private val meals: List<Meal>, private val listener: MainActivity) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = meals[position]
        holder.bind(meal)
    }

    override fun getItemCount(): Int = meals.size


    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewMeal: ImageView = itemView.findViewById(R.id.imageViewMeal)
        private val textViewMealName: TextView = itemView.findViewById(R.id.textViewMealName)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMealClick(meals[position])
                }
            }
        }

        fun bind(meal: Meal) {
            textViewMealName.text = meal.strMeal
            Picasso.get().load(meal.strMealThumb).into(imageViewMeal)
        }


    }
}
