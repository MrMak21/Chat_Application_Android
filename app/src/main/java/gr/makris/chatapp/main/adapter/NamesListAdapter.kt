package gr.makris.chatapp.main.adapter

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.switchmaterial.SwitchMaterial
import gr.makris.chatapp.R
import gr.makris.chatapp.data.User
import gr.makris.chatapp.utils.SharedPrefsUtils


class NamesListAdapter(application: Context): RecyclerView.Adapter<NamesListAdapter.ContentViewHolder>() {

    private val app = application
    private var namesList = mutableListOf<User>()
    private var listener: ((User) -> Unit)? = null
    private val userId: String?
    private var prefs: SharedPreferences? = SharedPrefsUtils.getPrefs(app)

    init {
        val prefs = SharedPrefsUtils.getPrefs(app)
        userId = prefs?.getString(SharedPrefsUtils.USER_ID,null)
    }

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
        holder.name.text = "${namesList[position].firstname} ${namesList[position].lastname}"
        holder.msg.text = namesList[position].id
        //set user image else set default
        if (!namesList[position].imageThumb.isNullOrEmpty()) {
            Glide.with(app)
                .load(namesList[position].imageThumb)
                .into(holder.userImage)
        }


        if (namesList[position].id.equals(userId)) {
            holder.name.text = "Just you"
            val img = prefs?.getString(SharedPrefsUtils.USER_IMAGE_THUMB,"")
            val uri = Uri.parse(img)
//            holder.userImage.setImageURI(uri)
            Glide.with(app)
                .load(uri)
                .into(holder.userImage)
        }

        holder.itemView.setOnClickListener { it ->
            itemClicked(position)
        }
    }
//
    private fun itemClicked(position :Int) {
        listener?.invoke(User(
            namesList[position].id,
            namesList[position].firstname,
            namesList[position].lastname,
            namesList[position].email,
            namesList[position].image,
            namesList[position].imageThumb)
        )
    }

    inner class ContentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.main_recycler_name)
        val msg = itemView.findViewById<TextView>(R.id.main_recycler_msg)
        val userImage = itemView.findViewById<ImageView>(R.id.main_recycler_userImage)

    }

    fun clear() {
        namesList.clear()
    }



}