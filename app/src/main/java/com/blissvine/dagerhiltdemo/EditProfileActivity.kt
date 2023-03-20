package com.blissvine.dagerhiltdemo

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.blissvine.dagerhiltdemo.model.User
import com.blissvine.dagerhiltdemo.viewModel.DataStoreViewModel
import com.blissvine.dagerhiltdemo.viewModel.UserViewModel
import com.bumptech.glide.Glide
import com.blissvine.dagerhiltdemo.util.InternalStoragePhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private var filePath: Uri? = null
    private lateinit var bitmap: Bitmap


    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
           filePath = result!!
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(applicationContext.contentResolver!!, filePath!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            saveImageToInternalStorage("profile", bitmap)
            Glide.with(applicationContext).load(filePath).circleCrop().into(edit_profile)

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    //    handleClick()
        checkIfUserHasSavedDetails()
        deleteUserRecord()
//       edit_image.setOnClickListener {
//            takePhoto.launch("image/*")
//        }
        getUserDetails()
    }

    private fun handleClick(){

        //on click of button save
      btnSave.setOnClickListener {
          when {
              et_name.text.isNullOrEmpty() -> {
                  Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show()
              }
              et_email.text.isNullOrEmpty() -> {
                  Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
              }
              filePath == null -> {
                  Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
              }
              else -> {

              //get details entered
                  val name = et_name.text.toString()
                  val email = et_email.text.toString()
                  val profileImageFilePath = filePath.toString()

                  val user = User(
                  id = 1,
                  name = name,
                  email = email,
                  profileImageFilePath = profileImageFilePath
              )
              //save the details to room database
              userViewModel.insertUserDetails(user)

                      userViewModel.response.observe (this) {

                          Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                          //success, save key so on next visit user goes to details screen
                          dataStoreViewModel.setSavedKey(true)

                          et_name.text.clear()
                          et_email.text.clear()

                          //show toast message
                          Toast.makeText(this, "Record Saved", Toast.LENGTH_LONG).show()
                          startActivity(Intent(this, EditProfileActivity::class.java))
                          finish()
                      }
                 }
             }

         }

    }

    private fun checkIfUserHasSavedDetails(){
        dataStoreViewModel.savedKey.observe(this){
            if (it != true){
                tv_name.visibility = View.GONE
                tv_email.visibility = View.GONE
                btnClear.visibility = View.GONE
                btnSave.visibility = View.VISIBLE
                tv_fill_your_info.visibility = View.VISIBLE
                tv_your_details.visibility = View.GONE
                textInputLayout.visibility = View.VISIBLE
                textInputLayout2.visibility = View.VISIBLE
                edit_profile.setOnClickListener {
                    takePhoto.launch("image/*")
                }
                handleClick()
            }else if(it == true){
                tv_name.visibility = View.VISIBLE
                tv_email.visibility = View.VISIBLE
                btnClear.visibility = View.VISIBLE
                btnSave.visibility = View.GONE
                btnClear.visibility = View.VISIBLE
                tv_fill_your_info.visibility = View.GONE
                tv_your_details.visibility = View.VISIBLE
                textInputLayout.visibility = View.GONE
                textInputLayout2.visibility = View.GONE
            }
        }
    }

    private fun deleteUserRecord(){

      btnClear.setOnClickListener {

            //clear record from room database
            userViewModel.doDeleteSingleUserRecord()

            //remove the datastorage key
            dataStoreViewModel.setSavedKey(false)
            tv_name.text = null
            tv_email.text = null
         edit_profile.setImageBitmap(null)
        edit_profile.setImageDrawable(null)
          clearMyFiles()
        }

    }


    private fun saveImageToInternalStorage(fileName: String, bitmap: Bitmap): Boolean {
        return try {
            applicationContext.openFileOutput("$fileName.jpg", MODE_PRIVATE).use { outputSream ->
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputSream)) {
                    throw IOException("Could not save Bitmap")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    private suspend fun loadImageFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = applicationContext.filesDir.listFiles()
            files.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            }
        }
    }

    private fun getUserDetails(){

        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){

                userViewModel.doGetUserDetails()
                userViewModel.userDetails.collect { users->
                    val listOfImage = loadImageFromInternalStorage()
                    for (user in users){
                        //set data into view
                        tv_name.text = user.name
                        tv_email.text = user.email

                        for(i in listOfImage){
                            if (i.name.contains("profile")){
                                Glide.with(applicationContext).load(i.bitmap).circleCrop().into(edit_profile)
                            }
                        }

                    }
                }
            }
        }

    }

    fun clearMyFiles(){
        val files: Array<File> = applicationContext.getFilesDir().listFiles()
        if (files != null) for (file in files) {
            file.delete()
        }
    }



}