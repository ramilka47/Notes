package com.ramil.notes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.terrakok.cicerone.Router
import com.ramil.notes.Application
import com.ramil.notes.R
import com.ramil.notes.data.Action
import com.ramil.notes.ui.MainActivity
import com.ramil.notes.ui.adapter.NotesAdapter
import com.ramil.notes.ui.dialogs.MenuActionsDialog
import com.ramil.notes.ui.navigation.Screens
import com.ramil.notes.ui.viewmodels.MainViewModel
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var router : Router

    private lateinit var recyclerView: RecyclerView
    private lateinit var loading : ProgressBar
    private lateinit var exit : ImageView
    private lateinit var menu : ImageView

    private val adapter by lazy{
        NotesAdapter(LayoutInflater.from(requireContext())) {
            router.navigateTo(Screens.note(Bundle().apply {
                putLong("ID", it.id)
            }))
        }
    }

    private val viewModel by lazy {
        viewModelFactory.create(MainViewModel::class.java)
    }

    init {
        Application.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view){
            recyclerView = findViewById(R.id.recycler_view)
            with(recyclerView){
                setHasFixedSize(true)
                adapter = this@MainFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            loading = findViewById(R.id.loading)
            loading.visibility = View.GONE

            exit = findViewById(R.id.exit)
            exit.setOnClickListener {
                router.newRootScreen(Screens.login())
            }

            menu = findViewById(R.id.menu)
            menu.setOnClickListener {
                MenuActionsDialog(arrayOf(Action.AddNote, Action.Exit)).apply {
                    setTargetFragment(this@MainFragment, MenuActionsDialog.REQUEST_CODE)
                }.show(parentFragmentManager, "")
            }
        }

        subscribe()
        viewModel.getNotes()
    }

    private fun subscribe(){
        viewModel.empty.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), getString(R.string.onEmptyList), Toast.LENGTH_SHORT).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            if (it){
                showProgress()
            } else {
                hideProgress()
            }
        })
        viewModel.noteList.observe(viewLifecycleOwner, {
            adapter.setOpenList(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
        viewModel.doneList.observe(viewLifecycleOwner, {
            adapter.setDoneList(it)
            recyclerView.adapter?.notifyDataSetChanged()
        })
    }

    private fun showProgress(){
        recyclerView.visibility = View.INVISIBLE
        loading.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        recyclerView.visibility = View.VISIBLE
        loading.visibility = View.INVISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MenuActionsDialog.REQUEST_CODE){
            when(resultCode){
                Action.ADD_NOTE->{
                    router.navigateTo(Screens.note())
                }
                Action.EXIT->{
                    router.newRootScreen(Screens.login())
                }
            }
        }
    }

}