package com.explore.eldercare.ui.healthcare

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentHealthCareBinding
import com.explore.eldercare.ui.chatting.ChatActivity


class HealthCareFragment : Fragment(), RecyclerViewListener {

    private lateinit var adapter: HealthCareAdapter
    private lateinit var viewModel: HealthCareViewModel
    private var _binding: FragmentHealthCareBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHealthCareBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[HealthCareViewModel::class.java]
        binding.rvHealthcare.layoutManager = LinearLayoutManager(context)
        binding.rvHealthcare.setHasFixedSize(true)
        adapter = HealthCareAdapter(emptyList(),this)
        binding.rvHealthcare.adapter = adapter

        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            if (showLoading) {
                Toast.makeText(context,"Loading....",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Done",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        viewModel.getList()


    }

    override fun onRecyclerViewItemClick(view: View, user: HealthcareData, x :String) {
        when(view.id){
            R.id.button4->{
                val intent = Intent(this@HealthCareFragment.requireContext(),ChatActivity::class.java)
                intent.putExtra("name", user.name)
                intent.putExtra("age",user.age)
                intent.putExtra("image", user.experience)
                intent.putExtra("address",user.address)
                intent.putExtra("email", user.email)
                intent.putExtra("image", user.image)
                intent.putExtra("uid",x)
                startActivity(intent)
            }
        }
    }


}