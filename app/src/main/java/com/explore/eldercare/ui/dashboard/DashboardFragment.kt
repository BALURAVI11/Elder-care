package com.explore.eldercare.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentDashboardBinding
import com.explore.eldercare.ui.activities.ProfileActivity
import com.explore.eldercare.ui.activities.RegisterHealthcare
import com.explore.eldercare.ui.activities.RegisterOldageHome
import com.explore.eldercare.ui.models.Users
import com.explore.eldercare.ui.notifications.NotificationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val auth = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                if(it.exists()){
                    val data= it.getValue(Users::class.java)

                    binding.name.setText(data!!.name.toString())

                    Glide.with(this).load(data.image).placeholder(R.drawable.images)
                        .into(binding.pfp)
                }
            }

        binding.profile.setOnClickListener{
            startActivity(Intent(requireContext(),ProfileActivity::class.java))
        }

        binding.register.setOnClickListener {
            startActivity(Intent(requireContext(),RegisterHealthcare::class.java))
        }

        binding.oldage.setOnClickListener {
            startActivity(Intent(requireContext(),RegisterOldageHome::class.java))
        }

        binding.acceptrequest.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }

        binding.settings.setOnClickListener{
            findNavController().navigate(R.id.navigation_medicine)
        }

        binding.chats.setOnClickListener{
            findNavController().navigate(R.id.navigation_chat)

        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}