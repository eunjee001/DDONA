package com.kkyoungs.ddona.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.kkyoungs.ddona.R

class GridBlockAdapter(val context:Context, val qlist : Array<Int>) : BaseAdapter(){
    override fun getCount(): Int {
        return qlist.size
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view : View = LayoutInflater.from(context).inflate(R.layout.layout_step, null)
        val block = view.findViewById<ImageView>(R.id.iv_step)
        block.setImageResource(R.drawable.stepbar_component_no_fill)
        return view
    }
}