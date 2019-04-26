package com.mhr.shirts.ui.units.shirts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mhr.shirts.R
import com.mhr.shirts.data.data_models.Shirt

class ShirtsAdapter(private val shirts: List<Shirt>): RecyclerView.Adapter<ShirtsAdapter.ShirtViewHolder>() {

    //region Overridden Functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        return ShirtViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shirt, parent, false))
    }

    override fun getItemCount(): Int {
        return shirts.size
    }

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.bindView(position)
    }
    //endregion

    //region ViewHolder
    inner class ShirtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bindView(position: Int)
        {
            if (position != -1 && position < shirts.size)
            {
                val imageView = itemView.findViewById<AppCompatImageView>(R.id.item_shirt_image_view)
                if (shirts[position].picture != null)
                {
                    Glide.with(imageView).load(shirts[position].picture).into(imageView)
                }
                else
                {
                    imageView.setImageResource(R.color.colorAccent)
                }

                val textView = itemView.findViewById<AppCompatTextView>(R.id.item_shirt_name_text_view)
                textView.text = shirts[position].name

            }
        }
    }
    //endregion

}