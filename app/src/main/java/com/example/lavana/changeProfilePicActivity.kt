package com.example.lavana

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ActionMenuView
import android.widget.Button
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.activity_change_profile_pic.*
import kotlinx.android.synthetic.main.progress_dialog.*
import java.util.jar.Manifest

class changeProfilePicActivity : AppCompatActivity() {


    lateinit var browseBtn : Button
    lateinit var uploadBtn : Button
    lateinit var mImageUri : Uri
    lateinit var mStorageReg : StorageReference
    lateinit var firebase : DatabaseReference
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_profile_pic)

        setTitle("Change Profile Picture")

        var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
        var saveProPic = sharedPreferences.getString("SESSION_PROPIC", "No Pic")

        //if no pro pic, will put android icon as profile pic
        if(saveProPic.equals("No Pic") || saveProPic.equals(""))
        {
            profilePic.setImageResource(R.drawable.ic_launcher_round)
        }
        else
        {
            Glide
                .with(this)
                .load(Uri.parse(saveProPic.toString()))
                .apply(RequestOptions.circleCropTransform()).into(profilePic);
        }



        //Initialize Progress Dialog
        progressDialog = ProgressDialog(this);

        mStorageReg = FirebaseStorage.getInstance().getReference("uploads")
        firebase = FirebaseDatabase.getInstance().getReference("uploads")



        browseBtn = findViewById(R.id.browseButton)
        uploadBtn = findViewById(R.id.uploadBtn)

        //Button browse click
        browseBtn.setOnClickListener{
            //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                {
                    //permission denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)

                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                }
                else
                {
                    //permission already granted
                    pickImageFromGallery()
                }
            }
            else
            {
                //System OS is < Marshallow
            }
        }

        uploadBtn.setOnClickListener{
            uploadFile()
        }




        /*val storage = FirebaseDatabase.getInstance()

        // Create a storage reference from our app
        val storageRef = storage.reference

        var imageRef  = storageRef.child("images")*/


    }

    private fun pickImageFromGallery()
    {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    //get file extension, if jpeg image, will return jpg
    private fun getFileExtension(uri : Uri ) : String
    {
        val cr : ContentResolver = contentResolver
        val mime : MimeTypeMap = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cr.getType(uri)).toString()
    }

    fun uploadFile()
    {


        if(mImageUri != null)
        {


            val fileReference : StorageReference = mStorageReg.child((System.currentTimeMillis()).toString() + "." + getFileExtension(mImageUri))



            fileReference.putFile(mImageUri).addOnSuccessListener {taskSnapshot ->
                Log.i("hi", "eh hallo")
                fileReference.downloadUrl.addOnCompleteListener(){taskSnapshot ->
                    Log.i("hi", "eh hallo1")
                    var url = (taskSnapshot.result).toString()
                    Log.i("hi", url.toString())


                    var sharedPreferences = getSharedPreferences("com.example.lavana", Context.MODE_PRIVATE)
                    var saveUsername = sharedPreferences.getString("SESSION_ID", getString(R.string.sharedPreUsername))
                    var saveEmail = sharedPreferences.getString("SESSION_EMAIL", getString(R.string.sharedPreEmail))
                    var savePass = sharedPreferences.getString("SESSION_PASS", "No Pass")
                    var saveUserKey = sharedPreferences.getString("SESSION_USERKEY", "No Key")
                    var saveAccType = sharedPreferences.getString("SESSION_ACCTYPE", "No Type")
                    var saveProPic = url//sharedPreferences.getString("SESSION_PROPIC", "No Pic")

                    var saveName = sharedPreferences.getString("SESSION_NAME", "No Name")
                    var saveGender = sharedPreferences.getString("SESSION_GENDER", "No Gender")
                    var savePhone = sharedPreferences.getString("SESSION_PHONE", "No Phone")
                    var saveFee = sharedPreferences.getString("SESSION_FEE", "No Fee")
                    var saveBio = sharedPreferences.getString("SESSION_BIO", "No Bio")
                    var saveCoachSport = sharedPreferences.getString("SESSION_COACHSPORT", "No CoachSport")
                    var saveTeachDay = sharedPreferences.getString("SESSION_TEACHDAY", "No TeachDay")
                    var saveTeachTime = sharedPreferences.getString("SESSION_TEACHTIME", "No TeachTime")

                    val user = User(saveUserKey!!, saveUsername!!, savePass!!, saveEmail!!, saveAccType!!, saveProPic!!,
                                    saveName!!, saveGender!!, savePhone!!, saveFee!!, saveBio!!, saveCoachSport!!, saveTeachDay!!, saveTeachTime!!)

                    firebase = FirebaseDatabase.getInstance().getReference("users").child(saveUserKey!!)
                    firebase.setValue(user)

                    Toast.makeText(this, "Profile Picture Changes Successfully", Toast.LENGTH_LONG)
                        .show()

                    progressDialog.dismiss()

                    var latestProPic = sharedPreferences.getString("SESSION_PROPIC", "No Pic")?:return@addOnCompleteListener

                    with(sharedPreferences.edit())
                    {
                        putString("SESSION_PROPIC", url)
                        apply()
                    }


                        /////////////
                    ///////////////////
                    //////////////////////

                }


                Toast.makeText(this, "Profile Picture Changes Successfully", Toast.LENGTH_SHORT)
                    .show()



            }


                .addOnFailureListener{
                        Toast.makeText(this, "Error upload file", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener {


                    //Show Dialog
                    progressDialog.show()
                    //Set content View
                    progressDialog.setContentView(R.layout.progress_dialog)
                    //Set Transparent Background
                    progressDialog.window?.setBackgroundDrawable(
                        ColorDrawable(Color.TRANSPARENT)
                    );

                }

        }
        else
        {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    companion object
    {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //permission code
        private val PERMISSION_CODE = 1001;

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        when(requestCode)
        {
            PERMISSION_CODE ->
            {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else
                {
                    //permission form popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
            profilePic.setImageURI(data?.data)
            mImageUri = data?.data!!
        }

    }
}
