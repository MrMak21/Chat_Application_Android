package gr.makris.chatapp.chat.adapter

import android.app.ActionBar
import android.content.Context
import android.drm.DrmStore
import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import gr.makris.chatapp.R
import gr.makris.chatapp.data.Message
import gr.makris.chatapp.utils.SharedPrefsUtils
import java.lang.Exception
import java.sql.Date
import java.text.SimpleDateFormat


class MessageAdapter(application: Context) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val app = application
    private var messagesList = mutableListOf<Message>()
    private var listener: ((Message) -> Unit)? = null
    private val userId: String?

    init {
        val prefs = SharedPrefsUtils.getPrefs(app)
        userId = prefs?.getString(SharedPrefsUtils.USER_ID,null)
    }

    fun setOnItemClickListener(listener: ((Message) -> Unit)) {
        this.listener = listener
    }

    fun setData(list: List<Message>) {
        messagesList.clear()
        messagesList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_recycler_item, parent, false)
        return MessageViewHolder(v)
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {



        if (messagesList[position].senderId.equals(userId)) {
            holder.recipientView.visibility = View.GONE
            holder.senderView.visibility = View.VISIBLE
            holder.sender_date.text = getDateTime(messagesList[position].timestamp)
            holder.sender_msg.text = messagesList[position].message
        } else {
            holder.senderView.visibility = View.GONE
            holder.recipientView.visibility = View.VISIBLE
            holder.recipient_date.text = getDateTime(messagesList[position].timestamp)
            holder.recipient_msg.text = messagesList[position].message
        }



        holder.itemView.setOnClickListener { it ->
            itemClicked(position)
        }
    }

    private fun itemClicked(position: Int) {
        listener?.invoke(
            Message(
                messagesList[position].msgId
                , messagesList[position].senderId
                , messagesList[position].receipientId
                , messagesList[position].messageType
                , messagesList[position].message
                , messagesList[position].chatId
                , messagesList[position].timestamp
            ))
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recipientView = itemView.findViewById<ConstraintLayout>(R.id.recipientView)
        val senderView = itemView.findViewById<ConstraintLayout>(R.id.senderView)

        val recipient_date = itemView.findViewById<TextView>(R.id.recipient_recycler_date)
        val recipient_msg = itemView.findViewById<TextView>(R.id.recipient_recycler_message)

        val sender_date = itemView.findViewById<TextView>(R.id.sender_recycler_date)
        val sender_msg = itemView.findViewById<TextView>(R.id.sender_recycler_message)
    }

    private fun getDateTime(s: String): String? {
        try {
            var sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
            var netDate = Date(s.toLong() * 1000)
            if (DateUtils.isToday(s.toLong() * 1000)) {
                sdf = SimpleDateFormat("HH:mm")
            }
            if (DateUtils.isToday((s.toLong() * 1000) + DateUtils.DAY_IN_MILLIS)) {
                sdf = SimpleDateFormat("HH:mm")
                return "Yesterday " + sdf.format(netDate)
            }
            return sdf.format(netDate)
        }catch (e: Exception) {
            return e.toString()
        }
    }

}