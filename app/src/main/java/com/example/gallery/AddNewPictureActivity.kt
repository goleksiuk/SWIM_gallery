package com.example.gallery

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_picture.*

class AddNewPictureActivity : AppCompatActivity() {

    private var uri: Uri? = null
    private val tags = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_picture)

        val listView = item_tags_LV
        val listViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tags)

        listView.adapter = listViewAdapter

        item_add_image_BT.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent, 1)
        }

        item_add_tag_BT.setOnClickListener {
            val editText = item_new_tag_ET
            if( editText.text.isNotEmpty()) {
                tags.add(editText.text.toString())
                listViewAdapter.notifyDataSetChanged()
                editText.text.clear()
            } else {
                editText.error = "New tag can't be empty!"
            }
        }

        item_save_BT.setOnClickListener {
            if(uri != null) {
                val intent = Intent()
                intent.putExtra("uriString", uri.toString())
                intent.putStringArrayListExtra("tags", tags)
                setResult(RESULT_OK, intent)
                this.finish()
            } else {
                Toast.makeText(this, "Please add image first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            item_add_image_BT.visibility = View.INVISIBLE

            uri = data!!.data
            val image: ImageView = item_image_IV
            image.setImageURI(uri)
            image.visibility = View.VISIBLE
        }
    }
}