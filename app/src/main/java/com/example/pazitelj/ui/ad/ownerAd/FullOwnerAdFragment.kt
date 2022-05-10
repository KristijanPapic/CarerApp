package com.example.pazitelj.ui.ad.ownerAd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentAvailableAdsBinding
import com.example.pazitelj.databinding.FragmentFullOwnerAdBinding
import com.example.pazitelj.models.Ad
import com.example.pazitelj.models.AppliedUserInput
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FullOwnerAdFragment : Fragment() {
    private var _binding: FragmentFullOwnerAdBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    var ad: Ad? = Ad()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullOwnerAdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var adId: String? = null
        arguments?.let {
            adId = it.getString("id")
        }
        CoroutineScope(Dispatchers.Main).launch {
            ad = adId?.let { homeViewModel.getAd(adId!!) }
            binding.ad = ad
        }
        binding.applyBtn.setOnClickListener {
            applyToAd()
        }
        return root
    }

    private fun applyToAd() {
        homeViewModel.postAppliedUser(AppliedUserInput(ad!!.Id,currentUserViewModel.user.value!!.Id))
        val message = Snackbar.make(binding.root,"Applied to ad",Snackbar.LENGTH_SHORT)
        message.show()
        binding.applyBtn.isEnabled = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}