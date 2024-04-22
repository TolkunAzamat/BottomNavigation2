package com.example.bottomnavigation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomnavigation.Constants.Companion.events
import com.example.bottomnavigation.EventsAdapter
import com.example.bottomnavigation.databinding.FragmentListBinding
import com.example.bottomnavigation.model.Events

class ListFragment : Fragment() {
    var binding: FragmentListBinding? = null
    private val adapter by lazy { EventsAdapter () }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
    }
    private fun setAdapter(){
        events.let{adapter.setList(it)}
        binding?.recycler?.adapter=  adapter
        binding?.recycler?.invalidate()

    }
}