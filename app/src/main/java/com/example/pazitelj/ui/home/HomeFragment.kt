package com.example.pazitelj.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pazitelj.MainActivity
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentHomeBinding
import com.example.pazitelj.ui.CurrentUserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isCarerRole = true
    private val availableAds = AvailableAdsFragment()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val currentUser: CurrentUserViewModel by activityViewModels()

    companion object {
        var isCarerRole = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.role.text = "CARER"
        binding.userModel = currentUser
        binding.roleButton.setOnClickListener {
            chooseRole()
        }
        setUpTabs()


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Log.d("maincurrentuser",currentUser.user.value.toString())

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("deshome","deshome")
    }


    private fun chooseRole() {
        isCarerRole = !isCarerRole
        homeViewModel.filter.value = !homeViewModel.filter.value!!
        Log.d("filter", homeViewModel.filter.value.toString())
        if(isCarerRole){
            binding.role.text = "CARER"
        }
        else{
            binding.role.text = "OWNER"
        }
    }


    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(AvailableAdsFragment(),"AVAILABLE")
        adapter.addFragment(ActiveAdsFragment(),"ACTIVE")
        binding.viewPager.adapter = adapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }


}

