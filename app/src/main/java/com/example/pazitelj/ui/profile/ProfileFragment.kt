package com.example.pazitelj.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import coil.load
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import com.example.pazitelj.PetListAdapter
import com.example.pazitelj.PetListAdapterViewOnly
import com.example.pazitelj.R
import com.example.pazitelj.databinding.FragmentProfileBinding
import com.example.pazitelj.signup.SignupActivity
import com.example.pazitelj.ui.CurrentUserViewModel
import com.example.pazitelj.ui.home.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var isNewUser: Int = 0
    private val currentUser: CurrentUserViewModel by activityViewModels()

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //_binding = FragmentProfileBinding.inflate(inflater,container,false)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile,container,false)
        binding.profilePhoto.load(currentUser.user.value?.PictureURL)

        binding.signoutBtn.setOnClickListener{
            signOut()
        }
        binding.editProfileButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionFragmentProfileToEditProfileFragment()
            view?.findNavController()?.navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("currentuser",currentUser.user.value.toString())
        binding.apply {
            user = currentUser
        }
        binding.petList.adapter = PetListAdapterViewOnly(currentUser.user.value!!.Pets)

    }

    override fun onPause() {
        super.onPause()
        Log.d("pause","pause")
    }

    override fun onResume() {
        super.onResume()
        Log.d("currentuser",currentUser.user.value.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun signOut(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val gsc = activity?.let { GoogleSignIn.getClient(it.applicationContext,gso) }
        gsc!!.signOut().addOnCompleteListener {
            moveToSignUp()
        }


    }
    fun moveToSignUp(){
        val intent = Intent(activity, SignupActivity::class.java)
        startActivity(intent)
    }

}