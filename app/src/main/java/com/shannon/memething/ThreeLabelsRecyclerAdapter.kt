package com.shannon.memething

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.friends_item_row.view.*

class ThreeLabelsRecyclerAdapter(private val friends: ArrayList<User>) : RecyclerView.Adapter<ThreeLabelsRecyclerAdapter.CellItemHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreeLabelsRecyclerAdapter.CellItemHolder {
        val inflatedView = parent.inflate(R.layout.friends_item_row, false)
        return CellItemHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return friends.count()
    }

    override fun onBindViewHolder(holder: ThreeLabelsRecyclerAdapter.CellItemHolder, position: Int) {
        holder.bindViews(friends[position])
    }

    class CellItemHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.i("RecyclerView", "CLICK!")
        }

        companion object {
            private val FRIEND_KEY = "FRIEND"
        }

        fun bindViews(friend: User) {
            view.firstLabel.text = friend.screenName
            view.thirdLabel.text = "Points: ${friend.points}"
        }
    }
}