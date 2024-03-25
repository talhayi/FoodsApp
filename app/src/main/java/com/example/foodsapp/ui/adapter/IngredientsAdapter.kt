package com.example.foodsapp.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.databinding.IngredientsItemLayoutBinding

class IngredientsAdapter (private var ingredientsList: List<Foods>, private var mContext: Context): RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    inner class IngredientsViewHolder(var binding: IngredientsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding = IngredientsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }
    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredients = ingredientsList[position]
        holder.binding.apply {
            holder.binding.imageViewIngredients.setImageResource(mContext.resources.getIdentifier(ingredients.foodImageName, "drawable", mContext.packageName))
            textViewIngredients.text = ingredients.foodName
        }
    }
}