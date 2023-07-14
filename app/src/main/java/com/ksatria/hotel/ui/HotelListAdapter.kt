package com.ksatria.hotel.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ksatria.hotel.R
import com.ksatria.hotel.model.Hotel

// untuk menghubungkan data hotel dengan tampilan item di dalam RecyclerView
class HotelListAdapter(
    // agar item bisa di klik
    private val onItemClickListener: (Hotel) -> Unit
): ListAdapter<Hotel,HotelListAdapter.HotelViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        return HotelViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.bind(hotel)
        holder.itemView.setOnClickListener{
            onItemClickListener(hotel)
        }
    }

    class HotelViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        private val tvName : TextView = itemView.findViewById(R.id.tvName)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvNumberOfRoom: TextView = itemView.findViewById(R.id.tvNumberOfRooms)

        // nilai-nilai dari objek Hotel diambil dan ditampilkan di dalam elemen tampilan yang sesuai.
        fun bind(hotel: Hotel?){
            tvName.text= hotel?.name
            tvAddress.text= hotel?.address
            tvNumberOfRoom.text= hotel?.numberOfRooms.toString()

        }
        companion object {
            fun create(parent: ViewGroup): HotelListAdapter.HotelViewHolder {

                val view :View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_hotel, parent,false)
                return HotelViewHolder(view)
            }
        }

    }

    companion object{
        private val WORDS_COMPARATOR = object :DiffUtil.ItemCallback<Hotel>(){
            override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel): Boolean {
                return oldItem.id==newItem.id
            }
        }
    }

}

