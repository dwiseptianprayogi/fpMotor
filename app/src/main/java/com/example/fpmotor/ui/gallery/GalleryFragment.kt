package com.example.fpmotor.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fpmotor.R
import com.example.fpmotor.databinding.FragmentGalleryBinding
import com.example.fpmotor.history.MyAdapterHistoryFp
import com.example.fpmotor.history.itemHistoryFp
import com.google.firebase.database.*


class GalleryFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var fpArray:ArrayList<itemHistoryFp>
    private lateinit var fpRecyclerView: RecyclerView
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    private var _binding: FragmentGalleryBinding? = null

//    private val MyAdapterHistoryFp by lazy { MyAdapterHistoryFp() }

//    var data: List<itemHistoryFp>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fpRecyclerView = view.findViewById(R.id.FpListHistory)
        fpRecyclerView.layoutManager = LinearLayoutManager(context)
        fpRecyclerView.setHasFixedSize(true)

        fpArray = arrayListOf<itemHistoryFp>()

//        swipeRefreshLayout?.isRefreshing = true
        dbRef = FirebaseDatabase.getInstance().getReference("fingerprintHistory2")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists())
//                fpArray = ArrayList()
                    for (fpSnapshot in snapshot.children){
                        val fp = fpSnapshot.getValue(itemHistoryFp::class.java)
                        fpArray.add(fp!!)
                    }
                var adapter = MyAdapterHistoryFp(fpArray)
                fpRecyclerView.adapter = adapter
//                adapter.setItems(fpArray)
//                adapter.notifyDataSetChanged()
//                swipeRefreshLayout?.isRefreshing = false
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}