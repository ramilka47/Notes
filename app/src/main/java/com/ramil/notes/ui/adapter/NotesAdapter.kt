package com.ramil.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramil.notes.R
import com.ramil.notes.data.entity.Note
import org.w3c.dom.Text
import java.lang.RuntimeException

class NotesAdapter(private val layoutInflater: LayoutInflater,
                   private val showNote : (Note)->Unit) : RecyclerView.Adapter<NotesAdapter.Holder>() {

    private val openList = mutableListOf<Note>()
    private val doneList = mutableListOf<Note>()

    fun setOpenList(openList : List<Note>){
        this.openList.clear()
        this.openList.addAll(openList)
    }

    fun setDoneList(doneList : List<Note>){
        this.doneList.clear()
        this.doneList.addAll(doneList)
    }

    private companion object{
        const val VIEW_TYPE_TITLE = 1
        const val VIEW_TYPE_NOTE = 2
    }

    override fun getItemCount(): Int{
        var size = 0
        if (openList.isNotEmpty()){
            size += openList.size + 1
        }
        if (doneList.isNotEmpty()){
            size += doneList.size + 1
        }
        return size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        when(holder){
            is TitleHolder->{
                if (position == 0)
                    holder.bind(holder.itemView.context.getString(R.string.newNotes))
                else
                    holder.bind(holder.itemView.context.getString(R.string.doneNotes))
            }
            is NoteHolder->{
                holder.bind(note(position))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        when(viewType){
            VIEW_TYPE_TITLE->{
                TitleHolder(layoutInflater.inflate(R.layout.item_title, parent, false))
            }
            VIEW_TYPE_NOTE->{
                NoteHolder(layoutInflater.inflate(R.layout.item_note, parent, false))
            }
            else->{
                throw RuntimeException()
            }
        }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || position == (openList.size + 1)){
            VIEW_TYPE_TITLE
        } else {
            VIEW_TYPE_NOTE
        }
    }

    private fun note(position: Int) : Note{
        return if (openList.isEmpty()){
            doneList[position - 1]
        } else {
            if (position - 1 >= openList.size) {
                doneList[position - openList.size - 2]
            } else {
                openList[position - 1]
            }
        }
    }

    sealed class Holder(view : View) : RecyclerView.ViewHolder(view)

    inner class TitleHolder(view : View) : Holder(view){
        private val textView : TextView = view.findViewById(R.id.title)

        fun bind(title : String){
            textView.text = title
        }
    }

    inner class NoteHolder(view: View) : Holder(view){
        private val title : TextView
        private val createDate : TextView
        private val changeDate : TextView
        private val cover : ImageView

        init {
            with(view){
                title = findViewById(R.id.title)
                createDate = findViewById(R.id.createdDate)
                changeDate = findViewById(R.id.lastChangeDate)
                cover = findViewById(R.id.cover)
            }
            view.setOnClickListener {
                showNote(note(adapterPosition))
            }
        }

        fun bind(note : Note) =
            with(note){
                this@NoteHolder.title.text = title
                this@NoteHolder.createDate.text = createDate.toString()
                changeDate.text = lastChangeDate.toString()
                if (url != null)
                    Glide.with(itemView).load(url).into(cover)
                else
                    cover.setImageResource(R.drawable.ic_add_cover)
            }
    }
}