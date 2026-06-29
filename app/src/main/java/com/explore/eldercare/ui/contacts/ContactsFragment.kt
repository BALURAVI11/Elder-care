package com.explore.eldercare.ui.contacts

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.explore.eldercare.MainActivity
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentContactsBinding
import com.explore.eldercare.ui.contacts.retrofit.DoctorList
import com.google.gson.Gson
import java.io.InputStream

class ContactsFragment : Fragment() {


private lateinit var binding : FragmentContactsBinding


    private var dataList : ArrayList<DoctorList> = ArrayList<DoctorList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        getData()

//        binding.floatingActionButton.setOnClickListener{
//            findNavController().navigate(R.id.navigation_medicine)
//        }

        val itemAdapter = DoctorListAdapter(dataList)
        binding.recycler.adapter = itemAdapter
        binding.recycler.setHasFixedSize(true)




        itemAdapter.setOnItemClickListener(object: DoctorListAdapter.onItemClickListener{

            override fun onItemClick(contactNo: String) {
                val call: Uri = Uri.parse("tel:$contactNo")
                val intent = Intent(Intent.ACTION_DIAL, call)
                startActivity(intent)
            }

        })



    }

    private fun getData() {
        val inputStream : InputStream = requireContext().assets.open("doctor_list.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)

        val gson = Gson()
        val doctors = gson.fromJson(json, Array<DoctorList>::class.java)
        dataList.addAll(doctors)
    }

}