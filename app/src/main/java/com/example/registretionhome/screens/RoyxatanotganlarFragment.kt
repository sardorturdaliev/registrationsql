package com.example.registretionhome.screens

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registretionhome.adapter.UserAdapter
import com.example.registretionhome.databinding.CustomDialogBinding
import com.example.registretionhome.databinding.FragmentRoyxatanotganlarBinding
import com.example.registretionhome.db.MyDatabase
import com.example.registretionhome.model.UserData


class RoyxatanotganlarFragment : Fragment(), UserAdapter.ClickListenerItems {
    private val binding by lazy { FragmentRoyxatanotganlarBinding.inflate(layoutInflater) }
    private lateinit var userAdapter: UserAdapter
    private lateinit var myDatabase: MyDatabase
    private lateinit var userList: ArrayList<UserData>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDatabase = MyDatabase.getInstance()

        userList = ArrayList()

        userList = myDatabase.getImages() as ArrayList<UserData>

        userAdapter = UserAdapter(userList, this)

        binding.rvusers.adapter = userAdapter
        binding.rvusers.layoutManager = LinearLayoutManager(requireContext())






        return binding.root
    }

    override fun clickitem(userData: UserData) {
        val dialog = Dialog(requireContext())
        val item = CustomDialogBinding.inflate(layoutInflater)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(item.root)

        item.tvTelUser.text = userData.telnumber
        item.tvUserFullname.text = userData.fullname
        item.profileImage.setImageBitmap(userData.bitmap)
        item.btnPhone.setOnClickListener {
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:${userData.telnumber}")).apply {
                startActivity(this)
            }
        }

        item.btnSms.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("sms:${userData.telnumber}")).apply {
                startActivity(this)
            }
        }


        dialog.show()
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setGravity(Gravity.BOTTOM)


    }
}