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
import com.example.foodsapp.databinding.FragmentCartBinding
import com.example.foodsapp.ui.adapter.CartAdapter
import com.example.foodsapp.ui.viewmodel.CartViewModel
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
            for (cart in cartList) {
                foodTotalPrice = cart.foodPrice!! * cart.foodOrderQuantity!!
                cartTotalPrice += foodTotalPrice
            }
            val taxRate = cartTotalPrice * 0.08
            binding.textViewSubtotal.text = String.format("%.2f", cartTotalPrice - taxRate)
            binding.textViewTax.text = "$taxRate ₺"
            binding.TextViewCartTotal.text = "${cartTotalPrice + 10} ₺"
        }
        viewModel.cartList("talhayi")
        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_homeFragment)
        }
        return binding.root
    }
}