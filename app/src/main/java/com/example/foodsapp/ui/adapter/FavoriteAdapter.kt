package com.example.foodsapp.ui.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsapp.R
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.databinding.FavoriteItemLayoutBinding
import com.example.foodsapp.ui.view.food.FavoriteFragmentDirections
import com.example.foodsapp.ui.viewmodel.FavoriteViewModel
import com.example.foodsapp.util.actions
import com.google.android.material.snackbar.Snackbar


class FavoriteAdapter (private var favoriteList: List<Foods>, var viewModel: FavoriteViewModel): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(var binding: FavoriteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = FavoriteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val foods = favoriteList[position]
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${foods.foodImageName}"
        holder.binding.apply {
            textViewFoodNameFavorite.text = foods.foodName
            textViewPriceFavorite.text = "${foods.foodPrice} ₺"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFood)
        }

      holder.binding.cardViewRestaurant.setOnClickListener {
            val actions = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(foods)
            Navigation.actions(it, actions)
        }

        holder.binding.imageViewDelete.setOnClickListener {
            viewModel.deleteFavorite(foods)
            val snackbar = Snackbar.make(it, "${foods.foodName} favorilerden kaldırıldı", Snackbar.LENGTH_INDEFINITE)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.setBackgroundTint(ContextCompat.getColor(holder.binding.root.context, R.color.primary_color))
            snackbar.setAction("Geri al") {
                viewModel.addFavorite(foods)
            }.show()
            val params = snackbar.view.layoutParams as CoordinatorLayout.LayoutParams
            params.anchorId = R.id.navigationBottomBar //id of the bottom navigation view
            params.gravity = Gravity.TOP
            params.anchorGravity = Gravity.TOP
            snackbar.view.layoutParams = params
        }
    }
}