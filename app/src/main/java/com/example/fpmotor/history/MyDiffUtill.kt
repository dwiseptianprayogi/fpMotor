package com.example.fpmotor.history

import androidx.recyclerview.widget.DiffUtil

class MyDiffUtill(
    private val oldList:List<itemHistoryFp>,
    private val newList:List<itemHistoryFp>
):DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].historyWaktu == newList[newItemPosition].historyWaktu
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].historyWaktu != newList[newItemPosition].historyWaktu ->{
                false
            }
            oldList[oldItemPosition].historyStatus != newList[newItemPosition].historyStatus ->{
                false
            }
            else -> true
        }
    }
}