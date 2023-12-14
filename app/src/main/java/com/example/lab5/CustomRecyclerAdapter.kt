package com.example.lab5

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerAdapter(private var cards: List<Card>) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnail)
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
        val deleteImage: ImageView = itemView.findViewById(R.id.deleter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards[position]
        if (card.imageURI != null) {
            holder.thumbnailImage.setImageURI(cards[position].imageURI)
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.icon)
        }
        holder.largeTextView.text = card.answer
        holder.smallTextView.text = card.translation
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, SeeCardActivity::class.java)
            intent.putExtra("position", position)
            ContextCompat.startActivity(it.context, intent, Bundle())
        }
        holder.deleteImage.setOnClickListener {
            AlertDialog.Builder(it.context).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    Model.removeCard(card.id)
                    refreshCardsViewWith(Model.cards)
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        it.context, "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show()
        }
    }

    fun refreshCardsViewWith(cards: List<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }
}