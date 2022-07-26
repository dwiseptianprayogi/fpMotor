package com.example.fpmotor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateFingerprintFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateFingerprintFragment : DialogFragment() {

    private lateinit var database: DatabaseReference
    private lateinit var etPass: TextInputEditText

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        var rootview : View = inflater.inflate(R.layout.fragment_update_fingerprint, container, false)
        rootview.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            database = Firebase.database.reference
            database.child("fingerprintStatus").setValue("update1")
                .addOnSuccessListener {
                    Toast.makeText(context, "Fingerprint Update Cancel", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed Send Data", Toast.LENGTH_SHORT).show()
                }
//            dismiss()
        }

        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
//            val etUpdateFingerprint = view.findViewById<TextInputEditText>(R.id.etPassUpdateFingerprint)
            etPass = requireView().findViewById(R.id.etPassUpdateFingerprintVal)
            val etUpdate = etPass.text.toString()

            if (etUpdate.isEmpty()){
                Toast.makeText(context, "Password Empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                database = FirebaseDatabase.getInstance().getReference("fingeprintSecurityUpdate")
                database.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val value = snapshot.value.toString()
//                        val tvStat = view!!.findViewById<TextView>(R.id.tvStatusKeamananVal)
//                        tvStat.text = value
                        if(etUpdate == value){
                            database = Firebase.database.reference
                            database.child("fingerprintStatus").setValue("update")
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Fingerprint Update Avaliable", Toast.LENGTH_SHORT).show()
                                    dismiss()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Fingerprint Update Not Avaliable", Toast.LENGTH_SHORT).show()
                                }
                        } else{
                            Toast.makeText(context, "Password Incorrect Please Try Again", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, "Failed To Get Data", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateFingerprintFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateFingerprintFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}