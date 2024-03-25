package com.example.foodsapp.ui.view.food

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodsapp.R
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.databinding.FragmentCartBinding
import com.example.foodsapp.ui.adapter.CartAdapter
import com.example.foodsapp.ui.viewmodel.CartViewModel
import com.example.foodsapp.util.USERNAME
import com.example.foodsapp.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private var cartTotalPrice = 0
    private var consolidatedCartList = mutableListOf<Cart>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        cartListObserve()
        onBackButton()
        viewModel.cartList(USERNAME)
        return binding.root
    }

    private fun cartListObserve(){
        viewModel.cartList.observe(viewLifecycleOwner){cartList->//List<Cart>
            cartAdapter = CartAdapter(viewModel.consolidateCartList(cartList), viewModel)
            binding.recyclerView.adapter = cartAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            foodTotalPrice(cartList)
            cartApprove(cartList)
        }
    }
    private fun foodTotalPrice(cartList: List<Cart>){
        cartTotalPrice = viewModel.foodTotalPrice()
        val taxRateCal = cartTotalPrice * 0.08
        val subtotal = String.format("%.1f", cartTotalPrice - taxRateCal)
        val taxRate = String.format("%.1f", taxRateCal)

        binding.textViewSubtotal.text = "$subtotal ₺"
        if (cartList.isNotEmpty()){
            binding.textViewDelivery.text = "10,0 ₺"
            binding.TextViewCartTotal.text = "${cartTotalPrice + 10},0 ₺"
        } else{
            binding.textViewDelivery.text = "0,0 ₺"
            binding.TextViewCartTotal.text = "${cartTotalPrice},0 ₺"
        }
        binding.textViewTax.text = "$taxRate ₺"
    }

    private fun cartApprove(cartList: List<Cart>){
        binding.buttonApprove.setOnClickListener {
            viewModel.cartApprove()
            if (cartList.isNotEmpty()){
                toast("Sepetiniz onaylanmıştır")
            }
            binding.textViewDelivery.text = "0,0 ₺"
            binding.TextViewCartTotal.text = "0,0 ₺"
        }
    }

    private fun onBackButton(){
        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_cartFragment_to_homeFragment)
        }
    }
}