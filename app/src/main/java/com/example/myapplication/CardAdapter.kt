package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.RecyclerItemBinding

class CardAdapter(
    private var cardList: List<Card>,
    private val context: Context,
    private val yesButtonColorResId: Int,
    private val cancelButtonColorResId: Int
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(private val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val thumbnail: ImageView = binding.thumbnail
        val answerText: TextView = binding.answer
        val translateText: TextView = binding.translate
        val delete: ImageView = binding.deleteIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cardList[position]

        holder.thumbnail.setImageURI(card.image)
        holder.answerText.text = card.answer
        holder.translateText.text = card.translate

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewCardActivity::class.java)
            intent.putExtra("position", position)
            ContextCompat.startActivity(it.context, intent, Bundle())
        }

        holder.delete.setOnClickListener {
            deleteDialog(card)
        }
    }

    private fun deleteDialog(card: Card) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.delete_card_title))
        builder.setMessage(context.getString(R.string.delete_card_message))

        builder.setPositiveButton(context.getString(R.string.yes)) { dialog, _ ->
            Model.removeCard(card.id)
            setCards(Model.cards)
        }

        builder.setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.setButtonColors(yesButtonColorResId, cancelButtonColorResId)
        dialog.show()
    }

    private fun AlertDialog.setButtonColors(yesButtonColorResId: Int, cancelButtonColorResId: Int) {
        val yesButtonColor = ContextCompat.getColor(context, yesButtonColorResId)
        val cancelButtonColor = ContextCompat.getColor(context, cancelButtonColorResId)

        setOnShowListener {
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(yesButtonColor)
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(cancelButtonColor)
        }
    }

    fun setCards(cardList: List<Card>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }
}