package com.example.pazitelj.ui.post

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentCarerPostBinding
import com.example.pazitelj.databinding.FragmentOwnerPostBinding
import com.example.pazitelj.models.AdInput
import com.example.pazitelj.ui.CurrentUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CarerPostFragment : Fragment() {
    private var _binding: FragmentCarerPostBinding? = null
    private val binding get() = _binding!!
    private val currentUser: CurrentUserViewModel by activityViewModels()
    private val jobList = arrayOf(
        "Dog Walking",
        "Dog Feeding at Owner's home",
        "Dog Feeding and care at Carer's home"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarerPostBinding.inflate(inflater, container, false)

        val jobListAdapter = ArrayAdapter(requireContext(), R.layout.list_item, jobList)
        binding.jobSelectDropdown.setAdapter(jobListAdapter)

        binding.carerPostButton.setOnClickListener {
            postAd()
        }

        return binding.root
    }

    private fun postAd() {
        val ad = AdInput(
            0,
            binding.descInput.text.toString(),
            binding.jobSelectDropdown.text.toString(),
            "",
            currentUser.user.value!!.Id
        )
        CoroutineScope(Dispatchers.Main).launch {
            currentUser.postAd(ad)
            Log.d("ad", ad.toString())
        }


    }
}