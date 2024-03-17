package com.example.foodsapp.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.foodsapp.databinding.FragmentCartBinding
import com.example.foodsapp.ui.adapter.CartAdapter
import com.example.foodsapp.ui.adapter.FoodsAdapter
import com.example.foodsapp.ui.viewmodel.CartViewModel
import com.example.foodsapp.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        viewModel.cartList.observe(viewLifecycleOwner){cartList->
            cartAdapter = CartAdapter(cartList, viewModel)
            binding.recyclerView.adapter = cartAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            var foodTotalPrice = 0
            var cartTotalPrice = 0
            for (cart in cartList){
                foodTotalPrice = cart.foodPrice!! * cart.foodOrderQuantity!!
                cartTotalPrice += foodTotalPrice
            }
            binding.TextViewCartTotal.text = cartTotalPrice.toString()
        }
        viewModel.cartList("talhayi")
        return binding.root
    }
}