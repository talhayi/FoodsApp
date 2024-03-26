package com.example.foodsapp.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.databinding.CartItemLayoutBinding
import com.example.foodsapp.ui.viewmodel.CartViewModel

class CartAdapter (private var cartsList: List<Cart>, var viewModel: CartViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(var binding: CartItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartsList.size
    }
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val carts = cartsList[position]
        var totalAmount = 0
        val userName = viewModel.currentUser()?.email
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${carts.foodImageName}"
        holder.binding.apply {
            textViewFoodNameCart.text = carts.foodName
            textViewPriceCart.text = "${carts.foodPrice} ₺"
            textViewQuantity.text = carts.foodOrderQuantity.toString()
            val price = carts.foodPrice
            var quantity = carts.foodOrderQuantity
            val totalPrice = price!! * quantity!!
            textViewTotalPrice.text = "$totalPrice ₺"
            Glide.with(root).load(url).override(1000, 500).into(imageViewFood)

            buttonIncrease.setOnClickListener {
                quantity++
                totalAmount = carts.foodPrice!! * quantity
                textViewTotalPrice.text = "$totalAmount ₺"
                textViewQuantity.text = quantity.toString()
                viewModel.addFoodCart(carts.foodName!!,carts.foodImageName!!,carts.foodPrice!!,1, userName!!)
            }
            buttonDecrease.setOnClickListener {
                if (quantity != 1) {
                    quantity--
                }
                totalAmount = carts.foodPrice!! * quantity
                textViewTotalPrice.text = "$totalAmount ₺"
                textViewQuantity.text = quantity.toString()
                viewModel.addFoodCart(carts.foodName!!,carts.foodImageName!!,carts.foodPrice!!,-1, userName!!)
            }
        }

        holder.binding.imageViewDelete.setOnClickListener {
            viewModel.deleteFoodCart(carts.cartFoodId!!, carts.userName!!)
        }
    }
}