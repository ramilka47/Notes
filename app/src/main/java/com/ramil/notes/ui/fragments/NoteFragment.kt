package com.ramil.notes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.ramil.notes.Application
import com.ramil.notes.R
import com.ramil.notes.data.Action
import com.ramil.notes.ui.dialogs.MenuActionsDialog
import com.ramil.notes.ui.viewmodels.NoteViewModel
import com.ramil.notes.ui.viewmodels.ViewModelFactory
import javax.inject.Inject


class NoteFragment : Fragment() {

    companion object{
        private const val PICK_IMAGE_AVATAR = 12
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        viewModelFactory.create(NoteViewModel::class.java)
    }

    init {
        Application.component.inject(this)
    }

    private lateinit var back : ImageView
    private lateinit var accept : ImageView
    private lateinit var menu : ImageView
    private lateinit var title : EditText
    private lateinit var description : EditText

    private var arrayOfAction : Array<Action> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_note, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view){
            back = findViewById(R.id.back)
            accept = findViewById(R.id.accept)
            menu = findViewById(R.id.menu)
            title = findViewById(R.id.title_edit)
            description = findViewById(R.id.description_edit)

            title.afterTextChanged {
                viewModel.titleChanged(it)
            }

            description.afterTextChanged {
                viewModel.descriptionChanged(it)
            }

            back.setOnClickListener {
                router.exit()
            }
            menu.setOnClickListener {
                MenuActionsDialog(arrayOfAction).apply {
                    setTargetFragment(this@NoteFragment, MenuActionsDialog.REQUEST_CODE)
                }.show(parentFragmentManager, "")
            }
            accept.setOnClickListener {
                viewModel.acceptChanged()
            }
        }

        subscribe()
        viewModel.getNote(arguments?.getLong("ID"))
    }

    private fun subscribe(){
        viewModel.done.observe(viewLifecycleOwner, {
            router.exit()
        })
        viewModel.markOfAcceptChanged.observe(viewLifecycleOwner, {
            accept.visibility = View.VISIBLE
        })
        viewModel.doseNotNote.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), getString(R.string.newNote), Toast.LENGTH_SHORT).show()
        })
        viewModel.note.observe(viewLifecycleOwner, {
            title.setText(it.title)
            description.setText(it.description)
        })
        viewModel.arrayOfAction.observe(viewLifecycleOwner, {
            arrayOfAction = it
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MenuActionsDialog.REQUEST_CODE){
            when(resultCode){
                Action.DELETE_NOTE->{
                    viewModel.deleteNote()
                }
                Action.ADD_IMAGE->{
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_PICK
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_AVATAR
                    )
                }
                Action.DELETE_IMAGE->{
                    viewModel.deleteImage()
                }
                Action.MARK_OF_FINAL->{
                    viewModel.markOfFinal()
                }
                Action.CLEAR_MARK_OF_FINAL->{
                    viewModel.clearMarkOfFinal()
                }
            }
        }

        if (requestCode == PICK_IMAGE_AVATAR && data != null){
            val selectedImage = data.data
            selectedImage?.let {
                viewModel.addImage(it.toString())
            }
        }
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit){
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                afterTextChanged.invoke(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

}