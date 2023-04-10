package com.perinze.merge.ui.gov_header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.databinding.FragmentGovHeaderBinding
import com.perinze.merge.ui.gov_header.GovHeaderAdapter

class GovHeaderFragment : Fragment() {

    private var _binding: FragmentGovHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var govHeaderViewModel: GovHeaderViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        govHeaderViewModel =
            ViewModelProvider(this)[GovHeaderViewModel::class.java]
        govHeaderViewModel.sync()

        _binding = FragmentGovHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.govHeaderRecyclerView

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = GovHeaderAdapter(requireActivity(), requireActivity(), govHeaderViewModel.data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}