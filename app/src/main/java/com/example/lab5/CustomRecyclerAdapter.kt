package com.example.lab5


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class CustomRecyclerAdapter(
   // private var cards: List<Card>,
   // private val onClick: (pos: Int) -> Unit
    private val action: ActionInterface,
) :
    RecyclerView.Adapter<CustomRecyclerAdapter.CardHolder>() {
    class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnail)
        val largeTextView: TextView = itemView.findViewById(R.id.textViewLarge)
        val smallTextView: TextView = itemView.findViewById(R.id.textViewSmall)
        val deleteImage: ImageView = itemView.findViewById(R.id.deleter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)
        return CardHolder(itemView)
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val card = cards[position]
        if (card.imageURI != null) {
            holder.thumbnailImage.setImageURI(cards[position].imageURI)
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.wallpapericon)
        }
        holder.largeTextView.text = card.answer
        holder.smallTextView.text = card.translation
        holder.itemView.setOnClickListener {
            action.onItemClick(card.id)
        }
        holder.deleteImage.setOnClickListener {
           action.onDeleteCard(card.id)
        }
    }

    var cards:List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        get() = field
}

interface ActionInterface {
    fun onItemClick(cardId:Int)
    fun onDeleteCard(cardId: Int)
}
