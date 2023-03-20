package com.blissvine.dagerhiltdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blissvine.dagerhiltdemo.viewModel.DataStoreViewModel
import com.blissvine.dagerhiltdemo.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        btnEditProfile.setOnClickListener{
//             startActivity(Intent(this@MainActivity, EditProfileActivity::class.java))
//            finish()
//        }
//        getUserDetails()
    }

//    private fun getUserDetails(){
//
//        this.lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
//
//                userViewModel.doGetUserDetails()
//                userViewModel.userDetails.collect { users->
//
//                    for (user in users){
//                        //set data into view
//                        tv_name.text = user.name
//                        tv_email.text = user.email
//
//                    }
//
//                }
//            }
//        }
//
//    }
}