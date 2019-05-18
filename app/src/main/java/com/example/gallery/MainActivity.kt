package com.example.gallery

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var arrayList: ArrayList<Picture>? = null
    private var adapter: RecyclerViewAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadPicturesList()
        adapter = RecyclerViewAdapter(arrayList!!, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                adapter!!.removeItem(viewHolder)
                savePicturesList()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        main_add_new_picture_BT.setOnClickListener{
            val intent = Intent(this, AddNewPictureActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK) {
            val uriString = data!!.getStringExtra("uriString")
            val tags = data.getStringArrayListExtra("tags")
            val picture = Picture(uriString, tags)

            arrayList!!.add(picture)
            adapter!!.notifyDataSetChanged()
            savePicturesList()
        }
    }

    private fun savePicturesList() {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("PICTURES_LIST", Gson().toJson(arrayList))
        editor.apply()
    }

    private fun loadPicturesList() {
        val sharedPreferences = getSharedPreferences("SHARED_PREFERENCES", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("PICTURES_LIST", null)
        val type = object: TypeToken<ArrayList<Picture>>(){}.type
        arrayList = Gson().fromJson<ArrayList<Picture>>(json, type)

        arrayList = arrayList ?: ArrayList()
    }
}