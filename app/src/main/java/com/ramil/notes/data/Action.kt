package com.ramil.notes.data

import com.ramil.notes.R

sealed class Action(val drawable : Int, val string : Int, val action : ((Int)->Unit)->Unit){

    companion object{
        const val ADD_NOTE = 22
        const val EXIT = 23
        const val DELETE_NOTE = 12
        const val MARK_OF_FINAL = 13
        const val CLEAR_MARK_OF_FINAL = 14
        const val ADD_IMAGE = 15
        const val DELETE_IMAGE = 16
    }

    object AddNote : Action(R.drawable.ic_plus, R.string.AddNote, { it(ADD_NOTE) })

    object DeleteNote : Action(R.drawable.ic_bucket, R.string.DeleteNote, { it(DELETE_NOTE) })

    object Exit : Action(R.drawable.ic_exit_from, R.string.Exit, { it(EXIT) })

    object MarkOfFinal : Action(R.drawable.ic_check, R.string.MarkOfFinal, { it(MARK_OF_FINAL) })

    object ClearMarkOfFinal : Action(R.drawable.ic_remove, R.string.ClearMarkOfFinal, { it(CLEAR_MARK_OF_FINAL) })

    object AddImage : Action(R.drawable.ic_add_cover, R.string.AddImage, { it(ADD_IMAGE) })

    object DeleteImage : Action(R.drawable.ic_remove, R.string.DeleteImage, { it(DELETE_IMAGE) })
}
