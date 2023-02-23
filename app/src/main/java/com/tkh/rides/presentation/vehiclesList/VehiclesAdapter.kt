package com.tkh.rides.presentation.vehiclesList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tkh.rides.databinding.ItemVehicleBinding
import com.tkh.rides.domain.model.Vehicle

class VehiclesAdapter : RecyclerView.Adapter<VehiclesAdapter.VehicleViewHolder>() {

    inner class VehicleViewHolder(val binding: ItemVehicleBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Vehicle>() {
        override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binding =
            ItemVehicleBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VehicleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        with(holder) {
            val vehicle = differ.currentList[position]
            with(vehicle) {
                binding.tvMakeModel.text = "Make: ${this.make_and_model}"
                binding.tvVin.text = "Vin: ${this.vin}"

                itemView.setOnClickListener {
                    onItemClickListener?.let { it(vehicle) }
                }
            }

        }
    }

    private var onItemClickListener: ((Vehicle) -> Unit)? = null

    fun setOnItemClickListener(listener: (Vehicle) -> Unit) {
        onItemClickListener = listener
    }

}













