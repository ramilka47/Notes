package com.ramil.notes.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.ramil.notes.data.Action
import com.ramil.notes.ui.adapter.DialogAdapter

class MenuActionsDialog(private val array: Array<Action>) : DialogFragment() {

    companion object{
        const val REQUEST_CODE = 1
    }

    private lateinit var recyclerView : RecyclerView

    private val adapter by lazy {
        DialogAdapter(array, LayoutInflater.from(requireContext())) {result_core->
            targetFragment?.let {
                it.onActivityResult(REQUEST_CODE, result_core, null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context).show()
        //  builder.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity!!, R.drawable.dialog_rounder_3))
        val p = builder?.window?.attributes

        val dp = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dp)
        p?.y = dp.heightPixels / 2
        p?.width = dp.widthPixels

        builder?.window?.attributes = p

        return builder
    }
}