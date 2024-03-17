package com.example.foodsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodsapp.databinding.FragmentDetailBinding
import com.example.foodsapp.ui.viewmodel.DetailViewModel
import com.example.foodsapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()
        val food = bundle.food
        var quantity = 0
        var totalAmount = 0
        binding.apply {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFoodDetail)
            textViewPriceDetail.text = food.foodPrice.toString()
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
                viewModel.addFoodCart(food.foodName!!, food.foodImageName!!, food.foodPrice!!,quantity,"talhayi")
            }
        }

        return binding.root
    }
}