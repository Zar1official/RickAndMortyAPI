package zar1official.rickandmortyapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
        binding.rcview.layoutManager = LinearLayoutManager(this@MainActivity)
        getJsonData()
    }

    private fun getJsonData() {
        val queue = Volley.newRequestQueue(this)
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            { response ->
                val jsonArray: JSONArray = response.getJSONArray("results")
                for (i in 0 until jsonArray.length()) {
                    addData(jsonArray.getJSONObject(i))
                }
            },
            { Toast.makeText(this, "Ошибка!", Toast.LENGTH_LONG).show() })

        queue.add(jsonRequest)
    }

    private fun addData(response: JSONObject){
        binding.rcview.adapter = adapter
        val name = response.get("name").toString()
        val imageURL = response.get("image").toString()
        adapter.addCharacter(CharacterModel(name, imageURL))
    }
}