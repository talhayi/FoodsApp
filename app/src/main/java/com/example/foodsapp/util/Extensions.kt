package com.example.foodsapp.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?, observer: (T) -> Unit) {
    liveData?.observe(this, Observer(observer))
}

fun Fragment.toast(message: String?) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.showSnackbarWithAction(
    view: View,
    message: String,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
        setAction(actionText) { action() }
        show()
    }
}

fun Fragment.showSnackBar(
    view: View,
    message: String,
) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}

fun Navigation.actions(it: View, id: Int){
    findNavController(it).navigate(id)
}

fun Navigation.actions(it: View, id: NavDirections){
    findNavController(it).navigate(id)
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}
