package com.example.mediaappplayer.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.mediaappplayer.R

class AddMusicDialog: DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var musicDialog = AlertDialog.Builder(activity)

        return musicDialog
            .setTitle("Add music")
            .setView(R.layout.choose_music)
            .create()

    }
}