package com.example.bottomnavigation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.os.persistableBundleOf
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavigation.databinding.ItemListBinding
import com.example.bottomnavigation.model.Events
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date
import java.util.Locale

class EventsAdapter : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    private var list = mutableListOf<Events>()
    private var onCheckboxClickListener : ((position:Int)->Unit?)? = null

    fun setList(list: List<Events>)
    {
    this.list = list.toMutableList()
    }
  inner class ViewHolder(val binding:ItemListBinding) : RecyclerView.ViewHolder(binding.root){
      init {
            binding.checkbox.setOnClickListener {
                onCheckboxClickListener?.invoke(adapterPosition)
                notifyItemChanged(position)
                notifyDataSetChanged()
            }
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current  = list[position]
        holder.binding.title.text = current.title
        holder.binding.date.text = formatDate(current.date)
        holder.binding.checkbox.isChecked = current.flag
        if(holder.binding.checkbox.isChecked){
            holder.binding.title.strikeThrought()
            holder.binding.card.setCardBackgroundColor(android.graphics.Color.GREEN)
        }
        holder.binding.checkbox.setOnCheckedChangeListener{ _, isChecked->
            current.flag = isChecked
            if (isChecked){
                holder.binding.title.strikeThrought()
            Log.d("AAAA",current.flag.toString())
            }
            else holder.binding.title.removestrikeThrought()
        }

    }
private fun formatDate(date: Date):String {
    var sdf = SimpleDateFormat("dd-MM-YYYY", Locale.getDefault())
    return sdf.format(date)
}
    private fun TextView.strikeThrought(){
        this.paintFlags = this.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
    }
    private fun TextView.removestrikeThrought(){
        this.paintFlags = this.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
    override fun getItemCount() = list.size
}