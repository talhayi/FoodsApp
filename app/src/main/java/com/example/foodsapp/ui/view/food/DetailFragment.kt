package com.example.foodsapp.ui.view.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.foodsapp.R
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.databinding.FragmentDetailBinding
import com.example.foodsapp.ui.adapter.IngredientsAdapter
import com.example.foodsapp.ui.viewmodel.DetailViewModel
import com.example.foodsapp.ui.viewmodel.FavoriteViewModel
import com.example.foodsapp.util.USERNAME
import com.example.foodsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private var quantity = 1
    private var totalAmount = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()
        val food = bundle.food
        showDetail(food)
        onDecreaseQuantityButton(food)
        onIncreaseQuantityButton(food)
        onAddCartButton(food)
        ingredientListObserve()
        onAddFavoriteButton(food)
        favoriteListObserve(food)
        onBackButton()
        return binding.root
    }

    private fun showDetail(food: Foods){
        binding.apply {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFoodDetail)
            textViewPriceDetail.text = "${food.foodPrice.toString()} ₺"
            textViewFoodNameDetail.text = food.foodName
            textViewAmount.text = "1"
            textViewTotalAmount.text = "${food.foodPrice.toString()} ₺"
        }
    }

    private fun onIncreaseQuantityButton(food: Foods){
        binding.buttonIncrease.setOnClickListener {
            quantity++
            totalAmount = food.foodPrice!! * quantity
            binding.textViewTotalAmount.text = "${totalAmount} ₺"
            binding.textViewAmount.text = quantity.toString()
        }
    }
    private fun onDecreaseQuantityButton(food: Foods){
        binding.buttonDecrease.setOnClickListener {
            if (quantity != 1) {
                quantity--
            }
            totalAmount = food.foodPrice!! * quantity
            binding.textViewTotalAmount.text = "${totalAmount} ₺"
            binding.textViewAmount.text = quantity.toString()
        }
    }

    private fun onAddCartButton(food: Foods){
        binding.buttonAddCart.setOnClickListener {
            if (quantity!=0){
                viewModel.addFoodCart(food.foodName!!, food.foodImageName!!, food.foodPrice!!,quantity,USERNAME)
                toast("Sepete $quantity adet ${food.foodName} eklenmiştir")
            }else{
                toast("Lütfen adet giriniz")
            }

        }
    }
    private fun ingredientListObserve(){
        viewModel.ingredientList.observe(viewLifecycleOwner){ingredientsList->
            ingredientsAdapter = IngredientsAdapter(ingredientsList, requireContext())
            binding.recyclerViewIngredients.adapter = ingredientsAdapter
            binding.recyclerViewIngredients.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        }
    }
    private fun onAddFavoriteButton(food: Foods){
        binding.imageViewFavoriteDetail.setOnClickListener {
            binding.imageViewFavoriteDetail.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
            favoriteViewModel.addFavorite(food)
        }
    }
    private fun favoriteListObserve(food: Foods){
        favoriteViewModel.getFavoriteList().observe(viewLifecycleOwner){favoriteList->
            for (favorite in favoriteList){
                if (food.foodId == favorite.foodId){
                    binding.imageViewFavoriteDetail.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
        }
    }
    private fun onBackButton(){
        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }
}