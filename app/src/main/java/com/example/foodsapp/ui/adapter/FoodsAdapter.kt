package com.example.foodsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.databinding.FoodItemLayoutBinding
import com.example.foodsapp.ui.viewmodel.HomeViewModel

class FoodsAdapter (private var foodsList: List<Foods>, var viewModel: HomeViewModel): RecyclerView.Adapter<FoodsAdapter.FoodsViewHolder>() {

    inner class FoodsViewHolder(var binding: FoodItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val binding = FoodItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodsList.size
    }
    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val foods = foodsList[position]
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${foods.foodImageName}"
        holder.binding.apply {
            textViewFoodName.text = foods.foodName
            textViewPrice.text = "₺ ${foods.foodPrice}"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFood)
        }

       /* holder.binding.personCV.setOnClickListener {
            val actions = HomeFragmentDirections.actionHomeFragmentToDetailPersonFragment(contacts)
            Navigation.actions(it, actions)
        }*/

       /* holder.binding.deleteIV.setOnClickListener {
            viewModel.delete(contacts.personId)
        }*/
    }
}