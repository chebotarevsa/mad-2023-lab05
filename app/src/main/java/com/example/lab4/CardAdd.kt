package com.example.lab4

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lab4.databinding.CardaddBinding

class CardAdd : AppCompatActivity() {
    lateinit var binding: CardaddBinding //Будет инициализированно позже - lateinit
    private var imageUri: Uri? = null //? - разрешение на нулевое значение - может позднее инициализировать, а может вообще нет

    override fun onCreate(savedInstanceState: Bundle?) {
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
            Intent(this, CardList::class.java).also {//Intent - намерение, запуск другой активности, отправка или получение чего-то
                startActivity(it) //Конкретно этот случай - открытие списка карточек после инициализации новой карточки и её добавления в список
            }
        }
    }

    private val getSystemContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it //Тут выбранная картиночка будет лежать
        val name = this.packageName //Тут берётся какое-то "пакетное имя" приложения, куда картиночка будет вставляться (наше приложение)
        this.grantUriPermission(name, it, Intent.FLAG_GRANT_READ_URI_PERMISSION) //Получение разрешения на чтение картиночки для нашего приложения
        binding.cardImage.setImageURI(it) //Собственно, вставка картиночки
    }
}