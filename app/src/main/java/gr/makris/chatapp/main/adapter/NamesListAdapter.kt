package gr.makris.chatapp.main.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import gr.makris.chatapp.R
import gr.makris.chatapp.data.User


class NamesListAdapter: RecyclerView.Adapter<NamesListAdapter.ContentViewHolder>() {

    private var namesList = mutableListOf<User>()
    private var listener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: ((User) -> Unit)) {
        this.listener = listener
    }

    fun setData(list: List<User>) {
        namesList.clear()
        namesList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_layout,parent,false)

        return ContentViewHolder(v)
    }

    override fun getItemCount(): Int {
        return namesList.size
    }

    override fun onBindViewHolder(holder: NamesListAdapter.ContentViewHolder, position: Int) {
        holder.name.text = namesList[position].firstname + " " + namesList[position].lastname
        holder.msg.text = namesList[position].id

        holder.itemView.setOnClickListener { it ->
            itemClicked(position)
        }
    }
//
    private fun itemClicked(position :Int) {
        listener?.invoke(User(namesList[position].id,namesList[position].firstname,namesList[position].lastname,namesList[position].email))
    }

    inner class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.main_recycler_name)
        val msg = itemView.findViewById<TextView>(R.id.main_recycler_msg)

    }



}