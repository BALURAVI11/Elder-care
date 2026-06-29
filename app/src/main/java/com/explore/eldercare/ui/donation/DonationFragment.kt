package com.explore.eldercare.ui.donation

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentDonationBinding
import com.explore.eldercare.databinding.FragmentHealthCareBinding
import com.explore.eldercare.ui.healthcare.HealthCareAdapter
import com.explore.eldercare.ui.healthcare.HealthCareViewModel

class DonationFragment : Fragment() {

    private lateinit var adapter: DonationAdapter
    private lateinit var viewModel: DonationViewModel
    private var _binding: FragmentDonationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[DonationViewModel::class.java]
        binding.rvDonation.layoutManager = LinearLayoutManager(context)
        binding.rvDonation.setHasFixedSize(true)
        adapter = DonationAdapter(emptyList())
        binding.rvDonation.adapter = adapter

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
            val url = "paytmmp://pay?pa=$it".toUri()
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url.toString())
            )
            startActivity(urlIntent)
        }

        viewModel.getList()
    }
}