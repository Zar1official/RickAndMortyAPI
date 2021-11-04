package zar1official.rickandmortyapi

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONObject
import zar1official.rickandmortyapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val URL = "https://rickandmortyapi.com/api/character"
    }

    lateinit var binding: ActivityMainBinding
    private var adapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcview.adapter = adapter

        getJsonData()
    }

    private fun getJsonData() {
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            { response ->
                val jsonArray: JSONArray = response.getJSONArray("results")
                addCharacters(jsonArray)
                binding.progressBar.visibility = View.GONE
            },
            {
                val snackbar = Snackbar.make(
                    this,
                    binding.root,
                    "Проверьте подключение к интернету",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Обновить") {
                    getJsonData()
                    snackbar.dismiss()
                }.show()
            })

        queue.add(jsonRequest)

    }

    private fun addCharacters(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.length()) {
            val currentCharacter = jsonArray.getJSONObject(i).getCharacter()
            adapter.addCharacter(currentCharacter)
        }
    }

    private fun JSONObject.getCharacter(): CharacterModel {
        val name = this.get("name").toString()
        val imageURL = this.get("image").toString()
        return CharacterModel(name, imageURL)
    }

}