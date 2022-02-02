package com.example.mediaappplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaappplayer.adapters.MusicAdapter
import com.example.mediaappplayer.databinding.ActivityMainBinding
import com.example.mediaappplayer.dialogs.AddMusicDialog

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding

    private var adapter = MusicAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter


        bind.btnAddMusic.setOnClickListener {
            showMusicAddDialog()
        }


        var callback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    (adapter).deleteMusic(viewHolder.adapterPosition)
                }
            }

        var itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(bind.recyclerView)

    }

    fun showMusicAddDialog() {
        var musicAddDialog = AddMusicDialog()
        musicAddDialog.show(supportFragmentManager, "musicAdd")
        adapter.addMusic("Samuel Cargidres - Stunning Blade")

    }

}