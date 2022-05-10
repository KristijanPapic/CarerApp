package com.example.pazitelj.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentAvailableAdsBinding
import com.example.pazitelj.models.AdFilter
import okhttp3.internal.notify
import okhttp3.internal.notifyAll


class AvailableAdsFragment : Fragment(),IAdListAdapter {

    private var _binding: FragmentAvailableAdsBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAvailableAdsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        Log.d("created","crated")

        return root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.homeModel = homeViewModel
        Log.d("adsava",homeViewModel.ads.value.toString())


        binding.petRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.petRecyclerView.adapter = PetAdAdapter(this)

        val filterObserver = Observer<Boolean> { newFilter -> changeAdapter(newFilter)  }
        homeViewModel.filter.observe(viewLifecycleOwner, filterObserver)
        //homeViewModel.filter.observe(viewLifecycleOwner, Observer { changeAdapter() })
    }

    override fun onResume() {
        super.onResume()
        Log.d("resume","resume")
        Log.d("fragads",binding.homeModel.toString())
        Log.d("adapterdata",binding.petRecyclerView.adapter.toString())
        Log.d("adapter",binding.petRecyclerView.adapter.toString())
        homeViewModel.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        Log.d("des","des")
    }

    fun changeAdapter(filter: Boolean){
        if(filter){
            homeViewModel.getModelAds(AdFilter((1)))
            binding.petRecyclerView.adapter = PetAdAdapter(this)
            //homeViewModel.reset()
        }
        else{
            homeViewModel.getModelAds(AdFilter((0)))
            binding.petRecyclerView.adapter = CarerAdAdapter(this)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.more -> {
                Log.d("notify","notify")
                return true
            }
            else -> super.onOptionsItemSelected(item)
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
}