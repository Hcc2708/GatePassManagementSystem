package com.thisisbkm.gatepassmanagementsystem.fragments

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.thisisbkm.gatepassmanagementsystem.TinyDB
import com.thisisbkm.gatepassmanagementsystem.databinding.FragmentCheckinBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Checkin : Fragment() {
    private lateinit var binding: FragmentCheckinBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tinydb = TinyDB(context)

        // Inflate the layout for this fragment
        binding = FragmentCheckinBinding.inflate(layoutInflater, container, false)
        binding.checkout.setOnClickListener {
            if(checkIsEmpty()) {
                Toast.makeText(context, "Field Value is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val al = HashSet(tinydb.getListInt("MyList"))
            val ll = tinydb.getListString("MyListLogs")
            val reg = binding.etreg.text.toString().toInt()
            if(al.contains(reg)) Toast.makeText(context, "User Already Checked Out", Toast.LENGTH_SHORT).show()
            else {
                al.add(reg)
                clearEt()
                ll.add(0, "OUT : $reg checked out at " + getDateTime())
                tinydb.putListInt("MyList", ArrayList(al))
                tinydb.putListString("MyListLogs", ll)
            }
        }
        binding.checkin.setOnClickListener {
            if(checkIsEmpty()) {
                Toast.makeText(context, "Field Value is Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val ll = tinydb.getListString("MyListLogs")
            val al = tinydb.getListInt("MyList")
            val reg = binding.etreg.text.toString().toInt()
            if(!al.contains(reg)) Toast.makeText(context, "User didn't checked Out", Toast.LENGTH_SHORT).show()
            else {
                al.remove(reg)
                clearEt()
                ll.add(0, "IN : $reg checked in at "+getDateTime())
                tinydb.putListInt("MyList", al)
                tinydb.putListString("MyListLogs", ll)
            }
        }
        return binding.root
    }
    private fun getDateTime(): String? {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(("dd-MM-yy HH:mm")))
    }
    private fun clearEt(){
        binding.etreg.text.clear()
    }
    private fun checkIsEmpty(): Boolean {
        return binding.etreg.text.isEmpty()
    }
}