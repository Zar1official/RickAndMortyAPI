package zar1official.rickandmortyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import zar1official.rickandmortyapi.databinding.ListItemBinding

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.CustomHolder>() {
    val charactersList = ArrayList<CharacterModel>()

    class CustomHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ListItemBinding.bind(item)
        fun bind(character: CharacterModel) = with(binding) {
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

    fun addCharacter(character: CharacterModel) {
        charactersList.add(character)
    }

}