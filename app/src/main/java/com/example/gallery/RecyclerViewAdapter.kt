package com.example.gallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.layout_recycler_view_item.view.*

class RecyclerViewAdapter(private val items: ArrayList<Picture>, private val context: Context): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount()= items.size

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        val picture: Picture = items[position]

        holder.image.setImageURI(Uri.parse(picture.uriString))
        holder.date.text = picture.date

        for(i in picture.tags.indices) {
            if(i<3) {
                val textView: TextView = holder.tags[i]
                textView.visibility = View.VISIBLE
                textView.text = picture.tags[i]
            }
        }

        holder.itemView.setOnClickListener {
            getSimilarPictures(picture)

            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("uriString", picture.uriString)
            intent.putExtra("date", picture.date)
            intent.putStringArrayListExtra("tags", picture.tags)
            intent.putStringArrayListExtra("similarPictures", picture.similarPictures)
            context.startActivity(intent)
        }
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        items.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.itemIV
        val date: TextView = itemView.itemDateTV
        val tags = arrayListOf<TextView>(itemView.item_tag_TV1, itemView.item_tag_TV2, itemView.item_tag_TV3)
    }

    private fun getSimilarPictures(picture: Picture) {
        picture.similarPictures.clear()
        picture.tags.forEach { pt ->
            items.forEach { i ->
                if(i != picture) {
                    if(i.tags.contains(pt)) {
                        if(!picture.similarPictures.contains(i.uriString)){
                            picture.similarPictures.add(i.uriString)
                        }
                    }
                }
            }
        }
    }
}