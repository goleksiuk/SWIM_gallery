package com.example.gallery

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val date  = arguments!!.getString("date")
        val tags = arguments!!.getStringArrayList("tags")
        val similarPictures = arguments!!.getStringArrayList("similarPictures")

        val listViewAdapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, tags)
        val similarPicturesUri = ArrayList<Uri>()

        for(uriString in similarPictures!!) {
            similarPicturesUri.add(Uri.parse(uriString))
        }

        details_date_TV.text = date
        details_tags_LV.adapter = listViewAdapter

        for (i in similarPicturesUri.indices) {
            val id = this.resources.getIdentifier("details_similar_IV${i+1}", "id", activity!!.packageName)
            val imageView: ImageView = view!!.findViewById(id)
            imageView.visibility = View.VISIBLE
            imageView.setImageURI(similarPicturesUri[i])
        }
    }
}