package com.example.retromeal
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.retromeal.R
import com.squareup.picasso.Picasso
/*La clase DetailActivity en tu código es una actividad en Android, que forma parte
de la interfaz de usuario de tu aplicación. En el contexto de una aplicación que muestra
 información sobre comidas, esta actividad se utiliza para mostrar los detalles
de una comida seleccionada. Aquí te explico cada parte de su funcionalidad:*/
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val mealName = intent.getStringExtra("EXTRA_MEAL_NAME")
        val mealCategory = intent.getStringExtra("EXTRA_MEAL_CATEGORY")
        val mealArea = intent.getStringExtra("EXTRA_MEAL_AREA")
        val mealInstructions = intent.getStringExtra("EXTRA_MEAL_INSTRUCTIONS")
        val mealImage = intent.getStringExtra("EXTRA_MEAL_IMAGE")

        findViewById<TextView>(R.id.textViewMealName).text = mealName
        findViewById<TextView>(R.id.textViewCategory).text = mealCategory
        findViewById<TextView>(R.id.textViewArea).text = mealArea
        findViewById<TextView>(R.id.textViewInstructions).text = mealInstructions
        Picasso.get().load(mealImage).into(findViewById<ImageView>(R.id.imageViewDetail))
    }
}
