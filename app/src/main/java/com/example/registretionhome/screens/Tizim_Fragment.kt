package com.example.registretionhome.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registretionhome.R
import com.example.registretionhome.databinding.FragmentTizimBinding
import com.example.registretionhome.db.MyDatabase


class Tizim_Fragment : Fragment() {
    private val binding by lazy { FragmentTizimBinding.inflate(layoutInflater) }
    private lateinit var myDatabase: MyDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDatabase = MyDatabase.getInstance()

        binding.tvRoyxatotish.setOnClickListener {
            findNavController().navigate(R.id.royxatgaotishFragment)
        }


        binding.btnTizimga.setOnClickListener {
            checkValidate()
        }






        return binding.root
    }

    private fun checkValidate() {
        val result = myDatabase.checkUser(
            binding.edtTelNumber.text.toString(),
            binding.edtPassword.text.toString()
        )

        if (result) {
            Toast.makeText(requireContext(), "Bor", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.royxatanotganlarFragment)
        } else {
            Toast.makeText(requireContext(), "Yoq", Toast.LENGTH_SHORT).show()
        }

    }

}