package com.example.bottomnavigation.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.example.bottomnavigation.Constants.Companion.counterId
import com.example.bottomnavigation.Constants.Companion.events
import com.example.bottomnavigation.databinding.FragmentAddBinding
import com.example.bottomnavigation.model.Events
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddFragment : Fragment() {
    var binding: FragmentAddBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentAddBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.date?.setOnClickListener {
            showDatePickerDialog()
        }
        binding?.adding?.setOnClickListener {
            var title = binding?.TitleEd?.text.toString()
            var description = binding?.EventEd?.text.toString()
            var date = binding?.date?.text.toString()
            var images = binding?.imageEd?.text.toString()
            var dateText: Date? = null
            try {
                // Преобразуем строку даты в объект Date
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateText = sdf.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            if (binding?.date?.text.isNullOrEmpty() && binding?.EventEd?.text.isNullOrEmpty() && binding?.TitleEd?.text.isNullOrEmpty()){
                binding?.date?.error = "Укажите дату"
                binding?.EventEd?.error = "Укажите событие"
                binding?.TitleEd?.error = "Укажите название"
                binding?.imageEd?.error = "Вставьте ссылку на картинку"
            }
            else {
                events.add(Events(counterId, title, description, dateText!!,images, flag = false))
                counterId++
                binding?.date?.text?.clear()
                binding?.EventEd?.text?.clear()
                binding?.TitleEd?.text?.clear()
                binding?.imageEd?.text?.clear()
                Log.d("SSS", events.toString())
            }
        }

    }
    private fun showDatePickerDialog() {
        // Создание экземпляра Calendar для получения текущей даты и времени
        val calendar: Calendar = Calendar.getInstance()
        // Извлечение текущего года, месяца и дня из Calendar
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        // Создание экземпляра DatePickerDialog с текущей датой и временем
        val datePickerDialog = DatePickerDialog(requireContext(),
            { view: DatePicker?, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Когда пользователь выбирает дату в диалоговом окне, этот код выполняется
                // Форматирование выбранной даты в строку в формате "yyyy-MM-dd"
                val selectedDate: String = java.lang.String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d",
                    selectedYear,
                    selectedMonth + 1,
                    selectedDay
                )
                // Установка выбранной даты в поле ввода даты (binding.date)
                binding?.date?.setText(selectedDate)
            }, year, month, day
        )
        // Отображение диалогового окна выбора даты
        datePickerDialog.show()
    }
}