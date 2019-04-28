package com.mhr.shirts.ui.units.basket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mhr.shirts.R
import com.mhr.shirts.data.data_models.Shirt

class BasketAdapter(val shirts: List<Shirt>, val interactionListener: BasketItemInteractionsListener) :
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    //region Initializer
    init {
        /*
        It helps item to retain their position after data change and just applies the changes to the content,
        which avoids flashy ui updates
         */
        setHasStableIds(true)
    }
    //endregion

    //region Fields
    //endregion

    //region Overridden Functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false))
    }

    override fun getItemCount(): Int {
        return shirts.size
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemId(position: Int): Long {
        return if (position != -1 && position < shirts.size) {
            (shirts[position].id ?: 0).toLong()
        } else {
            super.getItemId(position)
        }

    }
    //endregion

    //region ViewHolder
    inner class BasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //region Views
        private val imageView: AppCompatImageView = itemView.findViewById(R.id.item_basket_image_view)
        private val nameTextView: AppCompatTextView = itemView.findViewById(R.id.item_basket_name_text_view)
        private val addButton: AppCompatImageButton = itemView.findViewById(R.id.item_basket_add_image_button)
        private val removeButton: AppCompatImageButton = itemView.findViewById(R.id.item_basket_remove_image_button)
        private val deleteButton: AppCompatImageButton = itemView.findViewById(R.id.item_basket_delete_image_button)
        //endregion

        //region Initializer
        init {

            addButton.setOnClickListener {
                if (validatePosition(adapterPosition)) {
                    interactionListener.onAddButtonClickedFor(shirts[adapterPosition])
                }
            }

            removeButton.setOnClickListener {
                if (validatePosition(adapterPosition)) {
                    interactionListener.onRemoveButtonClickedFor(shirts[adapterPosition])
                }
            }

            deleteButton.setOnClickListener {
                if (validatePosition(adapterPosition)) {
                    interactionListener.onDeleteButtonClickedFor(shirts[adapterPosition])
                }
            }

        }
        //endregion

        //region Functions
        fun bindView(position: Int) {
            if (validatePosition(position)) {
                val shirt = shirts[position]

                imageView.setImageResource(R.color.colorAccent)
                if (shirt.picture != null) {
                    Glide.with(imageView).load(shirt.picture).into(imageView)
                }

                nameTextView.text = "${shirt.name}\nCount: ${shirt.quantity}"

            } else {
                imageView.setImageResource(R.color.colorAccent)
                nameTextView.text = ""
            }
        }

        private fun validatePosition(position: Int): Boolean {
            return position != -1 && position < shirts.size
        }
        //endregion
    }
    //endregion

    //region Callbacks
    interface BasketItemInteractionsListener {
        fun onAddButtonClickedFor(shirt: Shirt)
        fun onRemoveButtonClickedFor(shirt: Shirt)
        fun onDeleteButtonClickedFor(shirt: Shirt)
    }
    //endregion

}