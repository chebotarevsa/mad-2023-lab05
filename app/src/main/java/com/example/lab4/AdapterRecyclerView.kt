package com.example.lab4

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterRecyclerView(private var cards: List<Card>, private val context: Context) :
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage: ImageView = itemView.findViewById(R.id.pictureSmall)
        val largeTextView: TextView = itemView.findViewById(R.id.textAbove)
        val smallTextView: TextView = itemView.findViewById(R.id.textBelow)
        val deleteImage: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val card = cards[position]
        if (card.imageURI != null) {
            holder.thumbnailImage.setImageURI(cards[position].imageURI)
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.empty)
        }
        holder.largeTextView.text = card.answer
        holder.smallTextView.text = card.translation
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CardView::class.java)
            intent.putExtra("position", position)
            ContextCompat.startActivity(context, intent, null)
        }
        holder.deleteImage.setOnClickListener {
            showDeleteConfirmationDialog(card)
        }
    }

    private fun showDeleteConfirmationDialog(card: Card) {
        AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(context.getString(R.string.deleteWarning))
            .setMessage(context.getString(R.string.deletionMessage))
            .setPositiveButton(context.getString(R.string.Yes)) { _, _ ->
                (context as CardList).removeCard(card)
            }.setNegativeButton(context.getString(R.string.No)) { _, _ ->
            }.show()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun refreshCardsViewWith(cards: List<Card>) {
        this.cards = cards
        notifyDataSetChanged()
    }
}
