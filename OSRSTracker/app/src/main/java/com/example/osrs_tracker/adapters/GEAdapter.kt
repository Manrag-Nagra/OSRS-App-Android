package com.example.osrs_tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osrs_tracker.R
import com.example.osrs_tracker.models.Item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ge_row.view.*

class GEAdapter(private val result: List<Item>) : RecyclerView.Adapter<CustomViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ge_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return result.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = result[position]
        val itemCurrent = result[position].current
        val itemToday = result[position].today

        holder.view.geItemName.text = "%s".format(item.name)
        holder.view.geItemDesc.text = "%s".format(item.description)
        holder.view.geCurrentPriceText.text = "Current Price: %s".format(itemCurrent.currentPrice)
        holder.view.geTodayPriceText.text = "Today: %s".format(itemToday.todayPrice)

        //load images
        val iconImage = holder.view.geImageIcon
        Picasso.get().load(item.icon_large).into(iconImage);


    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}