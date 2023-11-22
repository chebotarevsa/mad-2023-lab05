package com.example.lab4

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

class AdapterRecyclerView(private var cards: List<Card>) : //Параметр - список
    RecyclerView.Adapter<AdapterRecyclerView.MyViewHolder>() {
    //RecyclerView.Adapter - базовый класс для создания адаптеров, RecyclerView - для структуры отображения списка элементов,
    //CustomRecyclerAdapter определяет, как данные будут связываться в элементе
    //С помощью ecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() идёт расширение и переопределение RecyclerView.Adapter
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) { //Тут лежат элементы интерфейса карточки, которая лежит в списке
        val thumbnailImage: ImageView = itemView.findViewById(R.id.pictureSmall)
        val largeTextView: TextView = itemView.findViewById(R.id.textAbove)
        val smallTextView: TextView = itemView.findViewById(R.id.textBelow)
        val deleteImage: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Вызывается при создании нового элемента списка - карточки
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false) //Вкратце - создание элемента списка
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = cards.size //Кол-во элементов

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Заполнение созданного элемента списка
        val card = cards[position] //Вытягиваем карту из списка
        if (card.imageURI != null) { //Если картинка имеется
            holder.thumbnailImage.setImageURI(cards[position].imageURI) //Установить картинку по ссылке, что лежит в этой карточке
        } else {
            holder.thumbnailImage.setImageResource(R.drawable.empty) //Иначе установить стандартную пустую
        }
        holder.largeTextView.text = card.answer
        holder.smallTextView.text = card.translation
        holder.itemView.setOnClickListener { //Если нажмётся на элемент
            val intent = Intent(it.context, CardSee::class.java) //Откроется просмотр карточки, что описан в классе "SeeCardActivity"
            intent.putExtra("position", position) //Так же передаётся id карточки
            ContextCompat.startActivity(it.context, intent, Bundle()) //Bundle() - для передачи данных
        }
        holder.deleteImage.setOnClickListener {//При нажатии на элемент корзинки
            AlertDialog.Builder(it.context).setIcon(android.R.drawable.ic_dialog_alert) //Запуск всплывающего окошка
                .setTitle("Вы действительно хотите удалить карточку?")
                .setMessage("Будет удалена карточка:\n ${card.answer} / ${card.translation}")
                .setPositiveButton("Да") { _, _ ->
                    Model.removeCard(card.id)
                    refreshCardsViewWith(Model.cards)
                }.setNegativeButton("Нет") { _, _ ->
                    Toast.makeText(
                        it.context, "Удаление отменено", Toast.LENGTH_LONG
                    ).show()
                }.show() //Очень интересная настройка всплывающего окна
        }
    }

    fun refreshCardsViewWith(cards: List<Card>) { //Обновление списка
        this.cards = cards
        notifyDataSetChanged()
    }
    }