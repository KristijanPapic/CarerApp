package com.example.pazitelj.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentActiveAdsBinding
import com.example.pazitelj.models.AdFilter
import com.example.pazitelj.ui.CurrentUserViewModel
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

class ActiveAdsFragment : Fragment(),IAdListAdapter {
    private var _binding: FragmentActiveAdsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val activeViewModel: ActiveAdsViewModel by activityViewModels()
    private val currentUser: CurrentUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveAdsBinding.inflate(inflater, container, false)
        val root: View = binding.root




        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            activeViewModel.getOwAds(AdFilter(Type = 1, UserId = currentUser.user.value!!.Id, ShowAppliedCount = true))
            activeViewModel.getAppAds(AdFilter(Type = 1, CurrentUser = currentUser.user.value!!.Id, ShowStatus = true))
            binding.lifecycleOwner = this
            binding.activeModel = activeViewModel

            binding.yourAdsList.adapter = OwnerAdActiveOwAdapter(this)
            binding.appliedToAdsList.adapter = OwnerAdActiveAppAdapter(this)

            val filterObserver = Observer<Boolean> { newFilter -> changeAdapter(newFilter)  }
            homeViewModel.filter.observe(viewLifecycleOwner, filterObserver)
    }

    fun changeAdapter(filter: Boolean){
        if(filter){
            activeViewModel.getOwAds(AdFilter(Type = 1, UserId = currentUser.user.value!!.Id, ShowAppliedCount = true))
            activeViewModel.getAppAds(AdFilter(Type = 1, CurrentUser = currentUser.user.value!!.Id, ShowStatus = true))
            binding.yourAdsList.adapter = OwnerAdActiveOwAdapter(this)
            binding.appliedToAdsList.adapter = OwnerAdActiveAppAdapter(this)
            //homeViewModel.reset()
        }
        else{
            activeViewModel.getOwAds(AdFilter(Type = 0, UserId = currentUser.user.value!!.Id, ShowAppliedCount = true))
            activeViewModel.getAppAds(AdFilter(Type = 0, CurrentUser = currentUser.user.value!!.Id, ShowStatus = true))
            binding.yourAdsList.adapter = CarerAdActiveOwAdapter(this)
            binding.appliedToAdsList.adapter = CarerAdActiveAppAdapter(this)
        }

    }

    override fun openAd(adId: String, type: Int) {
        if(type == 1){
            val action = HomeFragmentDirections.actionFragmentHomeToFullOwnerAdFragment(id = adId)
            findNavController().navigate(action)
        }
        else{
            val action = HomeFragmentDirections.actionFragmentHomeToFullCarerAdFragment(id = adId)
            findNavController().navigate(action)
        }
    }

    override fun deleteAd(id: String) {
        TODO("Not yet implemented")
    }


}