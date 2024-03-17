package com.example.foodsapp.ui.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodsapp.data.model.Cart
import com.example.foodsapp.data.model.Foods
import com.example.foodsapp.databinding.CartItemLayoutBinding
import com.example.foodsapp.databinding.FoodItemLayoutBinding
import com.example.foodsapp.ui.view.HomeFragmentDirections
import com.example.foodsapp.ui.viewmodel.CartViewModel
import com.example.foodsapp.ui.viewmodel.HomeViewModel
import com.example.foodsapp.util.actions

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
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${carts.foodImageName}"
        holder.binding.apply {
            textViewFoodNameCart.text = carts.foodName
            textViewPriceCart.text = "₺ ${carts.foodPrice}"
            textViewQuantity.text = carts.foodOrderQuantity.toString()
            val price = carts.foodPrice
            val quantity = carts.foodOrderQuantity
            val totalPrice = price!! * quantity!!
            textViewTotalPrice.text = totalPrice.toString()
            Glide.with(root).load(url).override(1000, 500).into(imageViewFood)
        }


        holder.binding.imageViewDelete.setOnClickListener {
            viewModel.deleteFoodCart(carts.cartFoodId!!, carts.userName!!)
        }
       /* holder.binding.cardViewRestaurant.setOnClickListener {
            val actions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(foods)
            Navigation.actions(it, actions)
        }

        holder.binding.imageViewAdd.setOnClickListener {
            viewModel.addFoodCart(foods.foodName!!, foods.foodImageName!!, foods.foodPrice!!,2,"talhayi")
        }

        holder.binding.deleteIV.setOnClickListener {
             viewModel.delete(contacts.personId)
         }*/
    }
}