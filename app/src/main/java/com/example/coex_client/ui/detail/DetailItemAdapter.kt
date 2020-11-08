package com.example.coex_client.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coex_client.R
import com.example.coex_client.model.map.RoomModel

class DetailItemAdapter : RecyclerView.Adapter<DetailItemAdapter.ViewItemViewHolder>() {
    private lateinit var data: List<RoomModel>
    private lateinit var inflater: LayoutInflater
    private lateinit var itemClickListener: OnItemClickListener
    private var type : Int = 0

    fun setType(type: Int) {
        this.type = type
    }

    fun setData(temp: List<RoomModel>) {
        data = temp!!
        notifyDataSetChanged()
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        if (itemClickListener != null) {
            this.itemClickListener = itemClickListener
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewItemViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.item_style_room, parent, false)
        return ViewItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewItemViewHolder, position: Int) {
        holder.bindData(data[position])
        if (type == 1) {
            holder.itemView.setOnClickListener { itemClickListener.onItemClick(position) }
        }
    }

    override fun getItemCount(): Int {
        return if (data == null || data.isEmpty()) 0 else data.size
    }

    class ViewItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val txtTitle: TextView
        private val txtDes: TextView
        private val txtCost: TextView
        fun bindData(room: RoomModel) {
            txtCost.setText(room.price.toString() + " VND/1 hour/1 person")
            txtDes.setText(room.about)
            txtTitle.setText(room.name)
        }

        init {
            txtTitle = itemView.findViewById(R.id.style_room_txt_title)
            txtDes = itemView.findViewById(R.id.style_room_txt_description)
            txtCost = itemView.findViewById(R.id.style_room_txt_cost)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Int)
    }
}