package com.example.foodsapp.ui.view

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
import com.example.foodsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var ingredientsAdapter: IngredientsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val ingredientsList = ArrayList<Foods>()
        val f1 = Foods(1,"Peynir","cheese")
        val f2 = Foods(2,"Soğan","onion")
        val f3 = Foods(3,"Domates","tomato")
        val f4 = Foods(4,"Biber","capsicum")
        val f5 = Foods(5,"Mantar","mushroom")
        ingredientsList.add(f1)
        ingredientsList.add(f2)
        ingredientsList.add(f3)
        ingredientsList.add(f4)
        ingredientsList.add(f5)
        val bundle: DetailFragmentArgs by navArgs()
        val food = bundle.food
        var quantity = 0
        var totalAmount = 0
        binding.apply {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}"
            //Glide.with(root).load(url).override(1000, 500).into(imageViewFoodDetail)
            textViewPriceDetail.text = "₺ ${food.foodPrice.toString()}"
            textViewFoodNameDetail.text = food.foodName
            binding.textViewAmount.text = "0"
            buttonIncrease.setOnClickListener {
                quantity++
                totalAmount = food.foodPrice!! * quantity
                binding.textViewTotalAmount.text = totalAmount.toString()
                binding.textViewAmount.text = quantity.toString()
            }
            buttonDecrease.setOnClickListener {
                if (quantity != 0) {
                    quantity--
                }
                totalAmount = food.foodPrice!! * quantity
                binding.textViewTotalAmount.text = totalAmount.toString()
                binding.textViewAmount.text = quantity.toString()
            }
            buttonAddCart.setOnClickListener {
                if (quantity!=0){
                    viewModel.addFoodCart(food.foodName!!, food.foodImageName!!, food.foodPrice!!,quantity,"talhayi")
                    toast("Sepete $quantity adet ${food.foodName} eklenmiştir")
                }else{
                    toast("Lütfen adet giriniz")
                }

            }

            ingredientsAdapter = IngredientsAdapter(ingredientsList, requireContext())
            binding.recyclerViewIngredients.adapter = ingredientsAdapter
            binding.recyclerViewIngredients.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

            imageViewFavoriteDetail.setOnClickListener {
                imageViewFavoriteDetail.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                favoriteViewModel.addFavorite(food)
            }

            favoriteViewModel.getFavoriteList().observe(viewLifecycleOwner){favoriteList->
                for (favorite in favoriteList){
                    if (food.foodId == favorite.foodId){
                        imageViewFavoriteDetail.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                }
            }
        }

        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailFragment_to_homeFragment)
        }

        return binding.root
    }
}