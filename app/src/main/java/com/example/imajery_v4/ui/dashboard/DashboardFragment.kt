package com.example.imajery_v4.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imajery_v4.R
import com.example.imajery_v4.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val sharedRef = requireActivity().getSharedPreferences("Data-IMAJERY", Context.MODE_PRIVATE)
        val refUserID = sharedRef.getInt("userID",0)
        val refUserName = sharedRef.getString("username","def usenname")

        val tvd: TextView = view.findViewById(R.id.text_dashboard)
        val btn_logout : Button = view.findViewById(R.id.btn_dashboard_logout)
        tvd.text = "Selamat Datang $refUserName di Aplikasi IMAGERY"

        btn_logout.setOnClickListener{
            val srEdit = sharedRef.edit()
            srEdit.putInt("login_status",0)
            srEdit.putInt("splash_status",0)
            srEdit.putInt("userID",0)
            srEdit.apply()
            requireActivity().finish()
            requireActivity().startActivity(requireActivity().intent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}