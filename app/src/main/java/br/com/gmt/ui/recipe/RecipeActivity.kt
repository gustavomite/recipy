package br.com.gmt.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import android.widget.TextView
import br.com.gmt.R
import br.com.gmt.data.IngredientList
import br.com.gmt.data.Recipe
import java.util.*

class RecipeActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        val bundle = intent.extras
        val recipe: Recipe? = bundle!!.getBundle("bundle")!!.getParcelable("data")

        tts = TextToSpeech(this, this)

        if (recipe != null) {
            val textTitle: TextView = findViewById(R.id.textRecipeTitle)
            val textDuration: TextView = findViewById(R.id.textRecipeDurationTime)
            val textIngredients: TextView = findViewById(R.id.textRecipeIngredientsList)
            val textDescription: TextView = findViewById(R.id.textRecipeDescriptionDetails)

            textTitle.text = recipe.name
            textDuration.text = String.format(resources.getString(R.string.main_text_list_minutes), recipe.time)

            var ingredients = String()
            recipe.ingredient.forEach {
                ingredients += "- ${IngredientList.ingredientNameFromId(it.key)} (${it.value})\n"
            }
            if (ingredients.isNotEmpty())
                ingredients = ingredients.subSequence(0, ingredients.length-1).toString()
            textIngredients.text = ingredients
            textDescription.text = recipe.description

            textDescription.setOnClickListener {
                speak(textDescription.text.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun speak(text: String) {
        if (tts.isSpeaking)
            tts.stop()
        else
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            }
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()

        super.onDestroy()
    }
}