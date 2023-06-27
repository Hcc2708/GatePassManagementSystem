package com.thisisbkm.gatepassmanagementsystem.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.thisisbkm.gatepassmanagementsystem.R
import com.thisisbkm.gatepassmanagementsystem.TinyDB
import com.thisisbkm.gatepassmanagementsystem.databinding.FragmentLogsBinding
import java.util.*

class Logs : Fragment() {
    private lateinit var binding:FragmentLogsBinding
    private lateinit var listView:ListView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogsBinding.inflate(layoutInflater, container, false)
        listView = binding.list
//        binding.tv.text = TinyDB(context).getListString("MyListLogs").toString()
        binding.clearLogs.setOnClickListener {
            val al = arrayListOf<String>()
            TinyDB(context).putListString("MyListLogs", al)
            listView.adapter = ArrayAdapter(requireContext(), R.layout.textviewout, al)
        }
        return binding.root
    }
    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        activity?.runOnUiThread {
            if(context!=null){
                val tdb = TinyDB(context)
                val list = tdb.getListString("MyListLogs")
                val arr = ArrayAdapter(requireContext(), R.layout.textviewout, list)
                listView.adapter = arr
                super.setUserVisibleHint(isVisibleToUser)
            }
        }
    }
}