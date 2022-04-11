package com.joeroble.android.treespotter_firebase

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalStateException

class TreeNameDialogFragment : DialogFragment() {


    private lateinit var treeInput: EditText
    private lateinit var listener: TreeNameDialogListener

    interface TreeNameDialogListener{
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    private val treeViewModel: TreeViewModel by lazy {
        ViewModelProvider(requireActivity()).get(TreeViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

//    override fun onAttach(context: Context){
//        super.onAttach(context)
//        try {
//            listener = context as TreeNameDialogListener
//        } catch (e: ClassCastException){
//            throw ClassCastException((context.toString() + " must implement TreeNameDialogListener"))
//        }
//    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tree_name_dialog, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        targetFragment?.let{
            listener = it as TreeNameDialogListener
        }
    }

    // Worked through this and adapted it from the developer android documentation
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val mainView = inflater.inflate(R.layout.fragment_tree_name_dialog, null)
            treeInput = mainView.findViewById(R.id.tree_name_textview)
            builder.setView(mainView)
                .setPositiveButton(android.R.string.ok) {dialog, id ->
                    val namedTree = treeInput.text.toString()
                    treeViewModel.enteredTreeName = namedTree
                    listener.onDialogPositiveClick(this)
                }
                .setNegativeButton(android.R.string.cancel){dialog, id ->
                    treeViewModel.enteredTreeName = null
                    listener.onDialogNegativeClick(this)
                    getDialog()?.cancel()
                }
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")


    }

    companion object {
        const val TAG = "TREE_NAME_DIALOG"
        @JvmStatic
        fun newInstance() = TreeNameDialogFragment()

    }
}