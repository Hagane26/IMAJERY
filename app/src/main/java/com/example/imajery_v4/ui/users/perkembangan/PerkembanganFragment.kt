package com.example.imajery_v4.ui.users.perkembangan

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imajery_v4.R

class PerkembanganFragment : Fragment() {

    companion object {
        fun newInstance() = PerkembanganFragment()
    }

    private val viewModel: PerkembanganViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_perkembangan, container, false)
    }
}