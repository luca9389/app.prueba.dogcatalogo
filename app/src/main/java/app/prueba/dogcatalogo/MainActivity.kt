@file:Suppress("PLUGIN_WARNING")

package app.prueba.dogcatalogo

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofitexampleapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.patalla_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import app.prueba.dogcatalogo.RecyclerDogAdapter as RecyclerDogAdapter1

/*
Ejercicio con Retrofit2 y Gson para parceo
Coroutines
RecyclerView
SearchView
Coil
Api usada https://dog.ceo/dog-api/documentation/
 */

//implementamos la interface del SearchView
class MainActivity : AppCompatActivity(),
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    lateinit var imagesPuppies: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Le decimos que somos los escuchadores

        searchBreed.setOnQueryTextListener(this)


    }



    //Creamos la funcion que nos devuelve un objeto Retrofit
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    //Corremos la coRutina para el llamado en Backgroud
    private fun searchByName(query: String) = GlobalScope.launch {
        val call = getRetrofit().create(ApiService::class.java).getCharacterByName("$query/images")
            .execute()
       // val puppies = call.body() as DogsResponse
        launch(Dispatchers.Main) {
            if (call.body()!= null) {
                val puppies = call.body() as DogsResponse
                initCharacter(puppies.images)
            } else {
                showErrorDialog()
            }
            hideKeyboard()
        }
    }

    private fun initCharacter(images: List<String>) {
        if (images.isNotEmpty()) {
            imagesPuppies = images
        }


        perrolista.layoutManager = GridLayoutManager(this, 2)
        perrolista.adapter = RecyclerDogAdapter1(imagesPuppies)
    }

    //es el que usamos porque queremos que se busque cuando se escribe
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchByName(query.toLowerCase())
        }
        return true


    }

    //No lo usamos ya que mira cuando cambia el texto cada vez
    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun showErrorDialog() {
        Toast.makeText(this, "Lo que a ingresado no ha sido encontrado", Toast.LENGTH_LONG).show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewRoot.windowToken, 0)
    }
}
