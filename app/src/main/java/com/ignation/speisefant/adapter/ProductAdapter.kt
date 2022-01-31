package com.ignation.speisefant.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ignation.speisefant.R
import com.ignation.speisefant.databinding.ProductLayoutBinding
import com.ignation.speisefant.domain.Product

class ProductAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var dataset: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ProductLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = dataset[position]
        with(holder) {
            with(item) {
                binding.productTitle.text = name
                binding.productDate.text = context.getString(R.string.date, startDate, endDate)
                binding.productPriceNew.text = context.getString(R.string.new_price, newPrice.toFloat() / 100)
                binding.productPriceOld.text = context.getString(R.string.old_price, oldPrice.toFloat() / 100)
            }
        }
    }

    override fun getItemCount(): Int = dataset.size

    class ProductViewHolder(val binding: ProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}