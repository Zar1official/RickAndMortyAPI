package zar1official.rickandmortyapi.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import zar1official.rickandmortyapi.domain.Character
import zar1official.rickandmortyapi.domain.Constant.API_URL

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    val data = MutableLiveData<ArrayList<Character>?>()
    val filteredData = MutableLiveData<ArrayList<Character>>()

    init {
        update()
    }

    fun update() {
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, API_URL, null,
            { response ->
                val jsonArray: JSONArray = response.getJSONArray("results")
                addCharacters(jsonArray)
            },
            {
                data.value = null
            })
        val queue = Volley.newRequestQueue(app)
        queue.add(jsonRequest)
    }

    private fun addCharacters(jsonArray: JSONArray) {
        val resultList = ArrayList<Character>()
        for (i in 0 until jsonArray.length()) {
            val currentCharacter = jsonArray.getJSONObject(i).getCharacter()
            resultList.add(currentCharacter)
        }
        data.value = resultList
    }

    private fun JSONObject.getCharacter(): Character {
        val name = this.get("name").toString()
        val imageURL = this.get("image").toString()
        return Character(name, imageURL)
    }

    fun filterData(filter: String?) {
        val resultList = ArrayList<Character>()
        data.value?.forEach {
            if (it.name.lowercase().contains(filter!!.lowercase()))
                resultList.add(it)
        }
        filteredData.value = resultList
    }
}
