package com.example.foodsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsapp.R
import com.example.foodsapp.databinding.FragmentFavoriteBinding
import com.example.foodsapp.ui.adapter.FavoriteAdapter
import com.example.foodsapp.ui.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteListObserve()
        onBackButton()
        return binding.root
    }

    private fun favoriteListObserve(){
        viewModel.getFavoriteList().observe(viewLifecycleOwner){favoriteList->
            favoriteAdapter = FavoriteAdapter(favoriteList, viewModel)
            binding.recyclerView.adapter = favoriteAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onBackButton(){
        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_favoriteFragment_to_homeFragment)
        }
    }
}