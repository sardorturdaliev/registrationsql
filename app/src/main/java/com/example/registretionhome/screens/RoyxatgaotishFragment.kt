package com.example.registretionhome.screens

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registretionhome.R
import com.example.registretionhome.databinding.FragmentRoyxatgaotishBinding
import com.example.registretionhome.db.MyDatabase
import com.example.registretionhome.model.UserData


class RoyxatgaotishFragment : Fragment() {
    private val binding by lazy { FragmentRoyxatgaotishBinding.inflate(layoutInflater) }
    private lateinit var arraydavlatAdapter: ArrayAdapter<String>
    private lateinit var arratdavlar: ArrayList<String>
    private lateinit var myDatabase: MyDatabase
    private lateinit var bitmap: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initData()



        return binding.root
    }

    private fun initData() {
        myDatabase = MyDatabase.getInstance()

        initSpinner()

        clickImg()


        binding.btnRoyxatanotish.setOnClickListener {
            //save Data User
            val fullname = binding.edtIsmFam.text.toString()
            val telnumber = binding.edtTelRaqam.text.toString()
            val davlat = binding.spinnerDavlat.text.toString()
            val manzil = binding.edtManzil.text.toString()
            val parol = binding.edtParol.text.toString()




            if (fullname.isNotEmpty() && telnumber.isNotEmpty() && davlat.isNotEmpty() && manzil.isNotEmpty() && parol.isNotEmpty()) {
                if (::bitmap.isInitialized){
                    myDatabase.addUser(
                        UserData(
                            0,
                            bitmap,
                            binding.edtIsmFam.text.toString(),
                            binding.edtTelRaqam.text.toString(),
                            binding.spinnerDavlat.text.toString(),
                            binding.edtManzil.text.toString(),
                            binding.edtParol.text.toString()
                        )
                    )
                    allnull()
                findNavController().navigate(R.id.royxatanotganlarFragment)
                Toast.makeText(requireContext(), "Saved Data ", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Photo ", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please Enter ", Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val selection = data.data
            bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selection)
            binding.profileImage.setImageBitmap(bitmap)

        }
    }

    private fun initSpinner() {
        arratdavlar = ArrayList()
        arratdavlar.add("Tashkent")
        arratdavlar.add("Fergana")
        arratdavlar.add("Buxara")
        arratdavlar.add("Samarqand")
        arratdavlar.add("Nukus")


        arraydavlatAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arratdavlar)


        binding.spinnerDavlat.setAdapter(arraydavlatAdapter)

    }

    private fun clickImg() {
        binding.profileImage.setOnClickListener {
            val pick = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pick, 100)
        }
    }

 private  fun allnull(){
     binding.edtIsmFam.text.clear()
     binding.edtTelRaqam.text.clear()
     binding.spinnerDavlat.text.clear()
     binding.edtManzil.text.clear()
     binding.edtParol.text.clear()
 }
}