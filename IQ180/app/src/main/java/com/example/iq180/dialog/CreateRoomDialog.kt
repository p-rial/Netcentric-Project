package com.example.iq180.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iq180.R
import kotlinx.android.synthetic.main.dialog_create_room.*

class CreateRoomDialog: DialogFragment() {

    var btnName: String? = null

    private lateinit var onDialogListener: OnDialogListener

    fun setOnDialogListener(onDialogListener: OnDialogListener) {
        this.onDialogListener = onDialogListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            onRestoreInstanceState(arguments)
        } else {
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_create_room,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDialog.text = btnName

        btnDialog.setOnClickListener({
            // Do action here!!
            onDialogListener.onPositiveBtnClicked(etDialog.text.toString())
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("dialog_btn", btnName)
    }

    private fun onRestoreInstanceState(bundle: Bundle?) {
        btnName = bundle?.getString("dialog_btn")
    }


    open class Builder {

        private lateinit var btnNameDialog: String

        fun setBtnName(btnName: String): Builder {
            this.btnNameDialog = btnName
            return this
        }

        fun build(): CreateRoomDialog {
            return newInstance(this.btnNameDialog)
        }
    }

    companion object {
        fun newInstance(btnName: String?)
                : CreateRoomDialog {
            val editFolderNameDialog = CreateRoomDialog()
            val arg = Bundle()
            arg.putString("dialog_btn", btnName)
            editFolderNameDialog.arguments = arg
            return editFolderNameDialog
        }
    }



    interface OnDialogListener {
        fun onPositiveBtnClicked(roomName: String)
    }
}