package com.ramil.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramil.notes.R
import com.ramil.notes.data.Action

class DialogAdapter(private val actions : Array<Action>,
                    private val layoutInflater: LayoutInflater,
                    private val actionOnResult : (Int)->Unit) : RecyclerView.Adapter<DialogAdapter.ActionHolder>(){

    override fun getItemCount(): Int = actions.size

    override fun onBindViewHolder(holder: ActionHolder, position: Int){
        holder.bind(actions[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionHolder =
        ActionHolder(layoutInflater.inflate(R.layout.item__action, parent, false))

    inner class ActionHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView
        private val text: TextView

        init {
            with(view) {
                image = findViewById(R.id.menu_item_image)
                text = findViewById(R.id.menu_text)
                this.setOnClickListener {
                    actions[adapterPosition].action {
                        actionOnResult(it)
                    }
                }
            }
        }

        fun bind(action: Action) =
            with(action) {
                image.setImageResource(drawable)
                text.setText(string)
            }

    }

}