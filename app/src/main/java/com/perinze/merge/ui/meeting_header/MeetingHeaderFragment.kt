package com.perinze.merge.ui.meeting_header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perinze.merge.databinding.FragmentMeetingHeaderBinding

class MeetingHeaderFragment : Fragment() {

    private var _binding: FragmentMeetingHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var meetingHeaderViewModel: MeetingHeaderViewModel
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        meetingHeaderViewModel =
            ViewModelProvider(this)[MeetingHeaderViewModel::class.java]
        meetingHeaderViewModel.sync()

        _binding = FragmentMeetingHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.meetingHeaderRecyclerView

        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView?.layoutManager = linearLayoutManager

        recyclerView?.adapter = MeetingHeaderAdapter(requireActivity(), requireActivity(), meetingHeaderViewModel.data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}