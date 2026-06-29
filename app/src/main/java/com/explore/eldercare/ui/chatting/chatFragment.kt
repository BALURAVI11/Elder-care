package com.explore.eldercare.ui.chatting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.explore.eldercare.R
import com.explore.eldercare.databinding.FragmentChatBinding
import com.explore.eldercare.databinding.FragmentMedicine2Binding
import com.explore.eldercare.ui.chatting.adapter.chatAdapter
import com.explore.eldercare.ui.medicine.MedicineAdapter
import com.explore.eldercare.ui.medicine.MedicineViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [chatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class chatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: chatAdapter
    private var _binding: FragmentChatBinding? =null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(layoutInflater)

        getData()
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment chatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            chatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getData(){

        val currentId = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase.getInstance().getReference("chats").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var list = arrayListOf<String>()
                var newlist= arrayListOf<String>()
                for(data in snapshot.children){
                    if(data.key!!.contains(currentId!!)){
                        list.add(data.key!!.replace(currentId!!,""))
                        newlist.add(data.key!!)
                    }
                }
                try {
                    binding.recyclerView.adapter =
                        chatAdapter(requireContext(), list, newlist)
                }catch (e: Exception){

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}