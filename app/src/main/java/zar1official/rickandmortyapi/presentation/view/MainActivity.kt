package zar1official.rickandmortyapi.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import zar1official.rickandmortyapi.databinding.ActivityMainBinding
import zar1official.rickandmortyapi.presentation.adapter.CustomAdapter
import zar1official.rickandmortyapi.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var vm: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rcview.adapter = adapter
        vm = ViewModelProvider(this).get(MainViewModel::class.java)
        vm.data.observe(this) { data ->
            Log.d("data", "changed")
            if (data != null) {
                adapter.updateData(data)
                binding.progressBar.visibility = View.GONE
            } else {
                val snackbar = Snackbar.make(
                    this,
                    binding.root,
                    "Проверьте подключение к интернету",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Обновить") {
                    vm.update()
                    snackbar.dismiss()
                }.show()
            }
        }

        vm.filteredData.observe(this) { data ->
            adapter.updateData(data)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                vm.filterData(newText)
                return true
            }
        })
    }
}