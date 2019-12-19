package com.example.osrs_tracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osrs_tracker.R
import com.example.osrs_tracker.models.Stats
import kotlinx.android.synthetic.main.hiscore_row.view.*

class HiscoreAdapter(private val hiscore: List<Stats>): RecyclerView.Adapter<CustomHiScoreViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHiScoreViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.hiscore_row, parent, false)
        return CustomHiScoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hiscore.size
    }

    override fun onBindViewHolder(holder: CustomHiScoreViewHolder, position: Int) {
        val hiscores = hiscore[position]
        holder.view.hiscoreSkill.text = hiscores.skill
        holder.view.hiscoreLevel.text = hiscores.level.toString()
        holder.view.hiscoreRank.text = hiscores.rank.toString()
        holder.view.hiscoreXP.text = hiscores.experience.toString()

    }

}

class CustomHiScoreViewHolder(val view: View): RecyclerView.ViewHolder(view){

}