package com.example.fpmotor.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fpmotor.*
import com.example.fpmotor.R
import com.example.fpmotor.databinding.FragmentHomeBinding
import com.google.firebase.database.*


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var dbRef:DatabaseReference
    private lateinit var fpArray:ArrayList<fp>
    private lateinit var fpRecyclerView: RecyclerView

    lateinit var btnImage:Button

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fpRecyclerView = view.findViewById(R.id.FpList)
        fpRecyclerView.layoutManager = LinearLayoutManager(context)
        fpRecyclerView.setHasFixedSize(true)

        fpArray = arrayListOf<fp>()
        getData()


        btnImage = view.findViewById(R.id.btnUpdateFingerPrint)
        btnImage.setOnClickListener(this)
    }

    private fun getData() {
        dbRef = FirebaseDatabase.getInstance().getReference("fingerprint")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
                    for (fpSnapshot in snapshot.children){
                        val fp = fpSnapshot.getValue(fp::class.java)
                        fpArray.add(fp!!)
                    }
                var adapter = MyAdapter(fpArray)
                fpRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }

        })
        dbRef = FirebaseDatabase.getInstance().getReference("fingeprintSecurity")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value.toString()
                val tvStat = view!!.findViewById<TextView>(R.id.tvStatusKeamananVal)
                tvStat.text = value
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }
        })
//        dbRef = FirebaseDatabase.getInstance().getReference("fingerprintAccess")
//        dbRef.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.value.toString()
////                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
//                if (value == "false"){
//                    val notificationHelper = NotificationHelper(context!!)
//                    notificationHelper.sendHighPriorityNotification("Fingerprint Access Failed Check Your Motorcycle!",
//                        "", HomeFragment::class.java)
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
//            }
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(p0: View?) {
        val updateFingerprint = UpdateFingerprintFragment()
        val fragmenManager = childFragmentManager
        updateFingerprint.show(fragmenManager, UpdateFingerprintFragment::class.java.simpleName)
    }
    companion object {
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}