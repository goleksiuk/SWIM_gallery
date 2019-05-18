package com.example.gallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val manager = supportFragmentManager
    private var isImageFragmentLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val uriString: String = intent.getStringExtra("uriString")
        val date: String = intent.getStringExtra("date")
        val tags: ArrayList<String> = intent.getStringArrayListExtra("tags")
        val similarPictures: ArrayList<String> = intent.getStringArrayListExtra("similarPictures")

        showImageFragment(uriString)

        details_BT.setOnClickListener {
            if(isImageFragmentLoaded) {
                showDetailsFragment(date, tags, similarPictures)
            }
            else {
                showImageFragment(uriString)
            }
        }
    }

    private fun showImageFragment(uriString: String) {
        val transaction = manager.beginTransaction()
        val fragment = ImageFragment()

        val bundle = Bundle()
        bundle.putString("uriString", uriString)
        fragment.arguments = bundle

        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
        isImageFragmentLoaded = true
    }

    private fun showDetailsFragment(date: String, tags: ArrayList<String>, similarPictures: ArrayList<String>) {
        val transaction = manager.beginTransaction()
        val fragment = DetailsFragment()

        val bundle = Bundle()
        bundle.putString("date", date)
        bundle.putStringArrayList("tags", tags)
        bundle.putStringArrayList("similarPictures", similarPictures)
        fragment.arguments = bundle

        transaction.replace(R.id.fragment_holder, fragment)
        transaction.commit()
        isImageFragmentLoaded = false
    }
}