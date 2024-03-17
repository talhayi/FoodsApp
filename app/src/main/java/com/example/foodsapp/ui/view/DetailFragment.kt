package com.example.foodsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodsapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()
        val food = bundle.food

        binding.apply {
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/${food.foodImageName}"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFoodDetail)
            textViewPriceDetail.text = food.foodPrice.toString()
            textViewFoodNameDetail.text = food.foodName
        }

        return binding.root
    }
}