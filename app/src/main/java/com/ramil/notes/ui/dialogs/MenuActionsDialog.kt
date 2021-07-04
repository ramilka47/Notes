package com.ramil.notes.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramil.notes.R
import com.ramil.notes.data.Action
import com.ramil.notes.ui.adapter.DialogAdapter

class MenuActionsDialog(private val array: Array<Action>) : DialogFragment() {

    companion object{
        const val REQUEST_CODE = 1
    }

    private lateinit var recyclerView : RecyclerView

    private val adapter by lazy {
        DialogAdapter(array, LayoutInflater.from(requireContext())) {result_code->
            targetFragment?.onActivityResult(REQUEST_CODE, result_code, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view){
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@MenuActionsDialog.adapter
                setHasFixedSize(true)

                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context).show()

        builder.window?.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.dialog_rounder))
        val p = builder?.window?.attributes

        val dp = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().display?.getRealMetrics(dp)
        } else {
            requireActivity().windowManager.defaultDisplay.getMetrics(dp)
        }

        p?.y = dp.heightPixels / 2
        p?.width = dp.widthPixels

        builder?.window?.attributes = p

        return builder
    }
}