package com.johnosezele.listmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.johnosezele.listmaker.databinding.MainActivityBinding
import com.johnosezele.listmaker.ui.main.MainFragment
import com.johnosezele.listmaker.ui.main.MainViewModel
import com.johnosezele.listmaker.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    //TODO:(Step 4a) Acquire the binding for the activity
    private lateinit var binding: MainActivityBinding

    //TODO:(Step 5a) Initialize a property to hold the view model
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,
        MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this)))
            .get(MainViewModel::class.java)

        //TODO:(Step 4b) setup the binding for dialog
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        //TODO:(Step 4c) setup dialog onclick listener
        binding.fabButton.setOnClickListener{
            showCreateListDialog()
        }
    }

    //TODO:(Step 3) AlertDialog to get name of the list from user. Code to show the dialog
    private fun showCreateListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) {dialog, _ -> dialog.dismiss()

        viewModel.saveList(TaskList(listTitleEditText.toString()))
        }

        builder.create().show()
    }
}