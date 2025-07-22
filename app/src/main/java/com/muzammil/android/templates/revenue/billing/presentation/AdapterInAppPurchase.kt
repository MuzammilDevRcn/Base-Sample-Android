package com.muzammil.android.templates.revenue.billing.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.muzammil.android.templates.databinding.ItemInAppPurchaseBinding
import com.muzammil.android.templates.revenue.billing.domain.model.InAppPurchase
import com.muzammil.android.templates.revenue.billing.presentation.mvi.ProductType

class AdapterInAppPurchase(private val onPlanSelected: (List<InAppPurchase>, ProductType) -> Unit) :
    ListAdapter<InAppPurchase, AdapterInAppPurchase.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInAppPurchaseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemInAppPurchaseBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: InAppPurchase) = with(binding) {
            title.setText(item.title)
            description.setText(item.description)
            price.text = item.price
            selectedItem.isChecked = item.isSelected

            val strokeAttr = if (item.isSelected) com.google.android.material.R.attr.colorPrimary else com.google.android.material.R.attr.colorOutline
            binding.root.strokeColor = MaterialColors.getColor(root, strokeAttr)

            root.setOnClickListener {
                val updatedList = currentList.map { it.copy(isSelected = it.selectedProduct == item.selectedProduct) }
                onPlanSelected(updatedList, item.selectedProduct)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<InAppPurchase>() {
            override fun areItemsTheSame(oldItem: InAppPurchase, newItem: InAppPurchase): Boolean {
                return oldItem.selectedProduct == newItem.selectedProduct
            }

            override fun areContentsTheSame(oldItem: InAppPurchase, newItem: InAppPurchase): Boolean {
                return oldItem == newItem
            }
        }
    }
}