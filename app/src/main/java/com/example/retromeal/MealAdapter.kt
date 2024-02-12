package com.example.retromeal

import MealRepository
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealAdapter(private val context: Context, private val favoriteMeals: MutableList<FavoriteMeal>, private val listener: Listener) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    private val mealRepository = MealRepository(context)

    interface Listener {
        fun onMealClick(favoriteMeal: FavoriteMeal)
        fun onFavoriteClicked(favoriteMeal: FavoriteMeal) // Asegúrate de que este método esté definido en la interfaz
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val favoriteMeal = favoriteMeals[position]
        holder.bind(favoriteMeal)
    }

    override fun getItemCount(): Int = favoriteMeals.size

    fun updateMeals(newFavoriteMeals: List<FavoriteMeal>) {
        this.favoriteMeals.clear()
        this.favoriteMeals.addAll(newFavoriteMeals)
        notifyDataSetChanged()
    }

    inner class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewMeal: ImageView = itemView.findViewById(R.id.imageViewMeal)
        private val textViewMealName: TextView = itemView.findViewById(R.id.textViewMealName)
        private val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon) // ImageView para el icono de favoritos

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onMealClick(favoriteMeals[position])
                }
            }

            favoriteIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val favoriteMeal = favoriteMeals[position]
                    CoroutineScope(Dispatchers.IO).launch {
                        // Asume que has pasado userId a MealAdapter o lo tienes disponible de alguna manera
                        val userId = "staticUserId" // Reemplaza esto por la lógica real para obtener el userId
                        val isFavorite = mealRepository.isFavorite(favoriteMeal, userId)
                        if (isFavorite) {
                            mealRepository.removeFavoriteMeal(favoriteMeal)
                        } else {
                            mealRepository.addFavoriteMeal(favoriteMeal)
                        }
                        // Puedes elegir actualizar la UI aquí, como cambiar el icono de favoritos
                    }
                }
            }
        }

        fun bind(favoriteMeal: FavoriteMeal) {
            textViewMealName.text = favoriteMeal.mealName
            Picasso.get().load(favoriteMeal.mealThumb).into(imageViewMeal)
            // Configura el icono de favoritos según si la comida es favorita o no
        }
    }
}
