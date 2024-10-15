package com.example.imajery_v4.ui.users.perkembangan

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import com.example.imajery_v4.R
import com.example.imajery_v4.supports.APIService
import com.example.imajery_v4.supports.retrofitClient



class PerkembanganFragment : Fragment() {



    companion object {
        fun newInstance() = PerkembanganFragment()
    }

    private val viewModel: PerkembanganViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_perkembangan, container, false)

        val sharedRef = requireActivity().getSharedPreferences("Data-IMAJERY", Context.MODE_PRIVATE)
        val refUserID = sharedRef.getInt("userID",0)
        val myWebView: WebView = view.findViewById(R.id.graph_perkembangan)
        myWebView.getSettings().setJavaScriptEnabled(true)
        myWebView.loadUrl("https://palevioletred-dragonfly-972749.hostingersite.com/perkembangan/1")
        //Toast.makeText(this.context, "User ID : $refUserID", Toast.LENGTH_SHORT).show()

        return view
    }
}