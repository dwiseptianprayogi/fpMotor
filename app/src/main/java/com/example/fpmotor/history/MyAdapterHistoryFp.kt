package com.example.fpmotor.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fpmotor.R

class MyAdapterHistoryFp (private val fpListHistory: ArrayList<itemHistoryFp>): RecyclerView.Adapter<MyAdapterHistoryFp.MyViewHolder>() {

//    private var oldHistoryFp = emptyList<itemHistoryFp>()

//    private var context: Context? = null
//    var list: ArrayList<itemHistoryFp> = ArrayList()
//    fun RVAdapter(ctx: Context?) {
//        context = ctx
//    }
//
//    fun setItems(emp: ArrayList<itemHistoryFp>?) {
//        list.addAll(emp!!)
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_history_fp, parent, false)
        return MyViewHolder(item)
    }

    override fun onBindViewHolder(holder: MyAdapterHistoryFp.MyViewHolder, position: Int) {
        val currentItem = fpListHistory[position]

        holder.historyWaktu.text = currentItem.historyWaktu
        holder.historyStatus.text = currentItem.historyStatus
//        holder.historyWaktu.text = oldHistoryFp[position].historyWaktu
//        holder.historyStatus.text = oldHistoryFp[position].historyStatus
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val historyWaktu = itemView.findViewById<TextView>(R.id.tvHistoryWaktuFp)
        val historyStatus = itemView.findViewById<TextView>(R.id.tvHistoryStatusFp)
    }

    override fun getItemCount(): Int {
        return fpListHistory.size
    }

//    fun setData(newHisoryFp:List<itemHistoryFp>){
//        val diffUtill = MyDiffUtill(oldHistoryFp, newHisoryFp)
//        val diffResults = DiffUtil.calculateDiff(diffUtill)
//        oldHistoryFp = newHisoryFp
//        diffResults.dispatchUpdatesTo(this)
//    }
}