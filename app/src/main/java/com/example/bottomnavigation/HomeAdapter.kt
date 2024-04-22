package com.example.bottomnavigation

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomnavigation.databinding.ItemEventsBinding
import com.example.bottomnavigation.model.Events
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class HomeAdapter(private var context:Context) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var list = mutableListOf<Events>()
    private var onButtonListener : ((position:Int)->Unit?)? = null
    fun setList(list: List<Events>)
    {
        this.list = list.toMutableList()
    }
    inner class ViewHolder(val binding: ItemEventsBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.done.setOnClickListener {
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION){
                    val currentItem = list[position]
                    currentItem.flag = true
                }
                notifyItemChanged(position)
                notifyDataSetChanged()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEventsBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = list[position]
        val tomorrowDate = Calendar.getInstance()
        tomorrowDate.add(Calendar.DAY_OF_YEAR, 1)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val formattedTomorrowDate = dateFormat.format(tomorrowDate.time)
        val eventDate = dateFormat.format(current.date)

        if (formattedTomorrowDate == eventDate ) {
            holder.binding.titleText.text = current.title
            holder.binding.descriptionText.text = current.description
            holder.binding.dateText.text = formatDate(current.date)
            val card = holder.binding.cardView
            Glide.with(holder.itemView).load(current.image).into(holder.binding.img)
            holder.binding.done.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Сделано")
                builder.setMessage("Вы действительно хотите отметить сделанным?")
                builder.setPositiveButton("Да") { dialog,which ->
                    current.flag = true
                    notifyDataSetChanged()
                }
                builder.setNegativeButton("Нет"){ dialog, which ->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
            Log.d("HHH",current.flag.toString())
        }
    }

    private fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun TextView.strikeThrought(){
        this.paintFlags = this.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
    }
    private fun TextView.removestrikeThrought(){
        this.paintFlags = this.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    override fun getItemCount() = list.size
    private fun showAlert(){


    }
}

