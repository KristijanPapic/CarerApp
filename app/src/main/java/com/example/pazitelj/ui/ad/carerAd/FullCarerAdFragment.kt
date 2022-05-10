package com.example.pazitelj.ui.ad.carerAd

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentFullCarerAdBinding
import com.example.pazitelj.databinding.FragmentFullOwnerAdBinding
import com.example.pazitelj.models.Ad
import com.example.pazitelj.ui.ad.ownerAd.FullOwnerAdViewModel
import com.example.pazitelj.ui.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FullCarerAdFragment : Fragment() {

    private var _binding: FragmentFullCarerAdBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullCarerAdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var adId: String? = null
        arguments?.let {
            adId = it.getString("id")
        }
        var ad: Ad? = Ad()
        CoroutineScope(Dispatchers.Main).launch {
            ad = adId?.let { homeViewModel.getAd(adId!!) }
            binding.ad = ad
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}