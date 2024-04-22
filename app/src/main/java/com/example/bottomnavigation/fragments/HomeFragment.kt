package com.example.bottomnavigation.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavigation.Constants
import com.example.bottomnavigation.EventsAdapter
import com.example.bottomnavigation.HomeAdapter
import com.example.bottomnavigation.R
import com.example.bottomnavigation.databinding.FragmentAddBinding
import com.example.bottomnavigation.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    var binding:FragmentHomeBinding? = null
    private val adapter by lazy { HomeAdapter (requireContext()) }
    private val handler = Handler(Looper.getMainLooper())

    private val updateTimeTask = object : Runnable {
        override fun run() {
            updateClock()
            handler.postDelayed(this, 1000) // Обновляем время каждую секунду
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    handler.post(updateTimeTask)
        setAdapter()
    }
    private fun updateClock(){
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val formatedDate = dateFormat.format(currentTime)
        binding?.clock?.text = formatedDate
    }
    private fun setAdapter(){
        Constants.events.let{adapter.setList(it)}
        binding?.recyclerHome?.adapter=adapter
        binding?.recyclerHome?.invalidate()

    }
}