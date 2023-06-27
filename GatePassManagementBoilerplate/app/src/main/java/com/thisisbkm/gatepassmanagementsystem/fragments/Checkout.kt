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
import com.thisisbkm.gatepassmanagementsystem.databinding.FragmentCheckoutBinding

class Checkout : Fragment() {
    private lateinit var binding:FragmentCheckoutBinding
    private lateinit var listView:ListView
    private lateinit var tdb:TinyDB
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)
        listView = binding.list
        tdb = TinyDB(context)
        val list = tdb.getListInt("MyList")
        val arr = ArrayAdapter(requireContext(), R.layout.textviewcurrout, list)
        listView.adapter = arr
        binding.progressBar.visibility = View.GONE
        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        activity?.runOnUiThread {
            if(context!=null){
                val list = tdb.getListInt("MyList")
                val arr = ArrayAdapter(requireContext(), R.layout.textviewcurrout, list)
                listView.adapter = arr
                binding.progressBar.visibility = View.GONE
                super.setUserVisibleHint(isVisibleToUser)
            }
        }

    }
}