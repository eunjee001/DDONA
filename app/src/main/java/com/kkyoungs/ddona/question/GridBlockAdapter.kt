package com.kkyoungs.ddona.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.kkyoungs.ddona.R
import com.kkyoungs.ddona.databinding.LayoutStepBinding

class GridBlockAdapter(val context:Context, val qlist : Array<Int>) : RecyclerView.Adapter<GridBlockAdapter.ViewHolder>() {
    private var binding: LayoutStepBinding? = null

    var position : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridBlockAdapter.ViewHolder {
        binding = LayoutStepBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!.root)
    }

    override fun onBindViewHolder(holder: GridBlockAdapter.ViewHolder, position: Int) {

        if (this.position >= position){
            binding?.ivStep?.setImageResource(R.drawable.stepbar_component_fill)
        }else {
            binding?.ivStep?.setImageResource(R.drawable.stepbar_component_no_fill)
        }
    }

    override fun getItemCount(): Int {
        return qlist.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}