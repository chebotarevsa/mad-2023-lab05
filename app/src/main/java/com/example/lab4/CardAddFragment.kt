package com.example.lab4

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lab4.databinding.CardaddBinding

class CardAddFragment : Fragment() {
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private var image: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = CardaddBinding.inflate(layoutInflater) //Надувание из хмл в вью
        setContentView(binding.root)

        binding.cardImage.setOnClickListener { //Имеется id - cardImage в XML, запускается при нажатии на картинку
            getSystemContent.launch("image/*") //Открытие системного окна с выбором картиночки
        }

        binding.addButton.setOnClickListener {
            val question = when { //Определение строк, где идёт считывание введённых данных, или установка своих, если пусто (и так на каждой)
                binding.enterQuestion.text.toString()
                    .isNotEmpty() -> binding.enterQuestion.text.toString()

                else -> "Поле вопроса отсутствует"
            }
            val example = when {
                binding.enterExample.text.toString()
                    .isNotEmpty() -> binding.enterExample.text.toString()

                else -> "Поле примера отсутствует"
            }
            val answer = when {
                binding.enterAnswer.text.toString()
                    .isNotEmpty() -> binding.enterAnswer.text.toString()

                else -> "Поле ответа отсутствует"
            }
            val translation = when {
                binding.enterTranslation.text.toString()
                    .isNotEmpty() -> binding.enterTranslation.text.toString()

                else -> "Поле перевода отсутствует"
            }
            val newCard = Model.createNewCard( //Создание нового объекта - элемента списка
                question, example, answer, translation, imageUri
            )
            Model.addCard(newCard) //Добавление только что созданного объекта
            Intent(this, CardListActivity::class.java).also {//Intent - намерение, запуск другой активности, отправка или получение чего-то
                startActivity(it) //Конкретно этот случай - открытие списка карточек после инициализации новой карточки и её добавления в список
            }
        }
    }

}