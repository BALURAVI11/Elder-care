package com.explore.eldercare.ui.medicine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.explore.eldercare.databinding.FragmentMedicine2Binding

class MedicineFragment : Fragment() {

    companion object {
        fun newInstance() = MedicineFragment()
    }

    private lateinit var adapter: MedicineAdapter
    private lateinit var viewModel: MedicineViewModel
    private var _binding: FragmentMedicine2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicine2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[MedicineViewModel::class.java]
        binding.rvMedicine.setHasFixedSize(true)
        adapter = MedicineAdapter(emptyList())
        binding.rvMedicine.adapter = adapter

        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                Toast.makeText(context,"Loading....", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Done", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        adapter.overdueListener {
            val url = "paytmmp://pay?pa=7780301451&am=${it.price}&tn=${it.name}".toUri()
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url.toString())
            )
            startActivity(urlIntent)
        }

        viewModel.getList()
    }

}
