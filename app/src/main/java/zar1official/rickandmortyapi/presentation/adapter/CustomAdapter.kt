package zar1official.rickandmortyapi.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zar1official.rickandmortyapi.R
import zar1official.rickandmortyapi.databinding.ListItemBinding
import zar1official.rickandmortyapi.domain.Character

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomHolder>() {

    private var charactersList =  ArrayList<Character>()

    inner class CustomHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ListItemBinding.bind(item)
        fun bind(character: Character) = with(binding) {
            Glide.with(root).load(character.imageURL).into(imageview)
            textView.text = character.name
        }
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(charactersList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomHolder(view)
    }

    override fun getItemCount(): Int = charactersList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: ArrayList<Character>) {
        charactersList= newList
        notifyDataSetChanged()
    }

}