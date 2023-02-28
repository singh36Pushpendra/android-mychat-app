package com.app.mychats.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.app.mychats.R
import com.app.mychats.model.User
import com.app.mychats.util.MyChatUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_profile_dialog.*
import kotlinx.android.synthetic.main.fragment_profile_dialog.view.*
import java.util.*

class ProfileDialogFragment : DialogFragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var imgViewUser: CircleImageView
    private lateinit var storageReference: StorageReference

    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_dialog, container, false)

        imgViewUser = view.imgViewUser
        database = FirebaseDatabase.getInstance()
        database.getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                user = it.getValue(User::class.java)!!
                Glide.with(this).load(user.profilePic).into(imgViewUser)

                view.tvName.text = user!!.name
                view.tvPhoneNumber.text = user!!.phoneNumber
            }.addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }


        imgViewUser.setOnClickListener {
            val intentGallery = Intent(Intent.ACTION_PICK)
            intentGallery.type = "image/*"
            intentGallery.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startActivityForResult(intentGallery, GALLERY_REQ_CODE)
        }
        view.imgViewClose.setOnClickListener {
            dismiss()
        }

        view.btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            dismiss()
            MyChatUtil.replaceFragment(activity, SignInFragment())
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        var generatedFilePath: String? = null
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                // For gallery.
                val imageUri = data!!.data
                imgViewUser.setImageURI(imageUri)
                Log.d("Toofan Party", "Pushpendra Singh Songara")
                storageReference = FirebaseStorage.getInstance().reference
                    .child("profile")
                    .child(Date().time.toString())
                Log.d("Toofan Party", "Pushpendra Singh Songara $imageUri")
                storageReference.putFile(imageUri!!)
                    .addOnSuccessListener { snapshot ->
                        Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT)
                            .show()
                        snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener {
                            Log.d("generated path", it.toString())
                            updateImageString(it.toString())
                            generatedFilePath = it.toString()
                        }
//                        snapshot.storage.downloadUrl.addOnSuccessListener { uri ->
//                            generatedFilePath = uri.toString()
//                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("exception", it.message!!)
                    }

                generatedFilePath?.let { Log.d("generated file path", it) }
            }
        }
    }

    private fun updateImageString(generatedFilePath: String?) {
//        user.profilePic = generatedFilePath!!
        database.getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child("profilePic").setValue(generatedFilePath).addOnSuccessListener {

                Log.d("updated", "image string")
            }

    }

    companion object {
        private const val GALLERY_REQ_CODE: Int = 1000
    }
}