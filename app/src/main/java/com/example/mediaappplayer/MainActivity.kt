package com.example.mediaappplayer

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaappplayer.adapters.MusicAdapter
import com.example.mediaappplayer.databinding.ActivityMainBinding
import com.example.mediaappplayer.dialogs.AddMusicDialog
import com.example.mediaappplayer.models.Music
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding

    private var adapter = MusicAdapter(object: MusicAdapter.OnItemClickListener{
        override fun onClick(song: Music) {
            println("Clicked")
            playMusic()
        }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter


        bind.btnAddMusic.setOnClickListener {
            showMusicAddDialog()
        }

        swipeToDelete()

    }


    fun playMusic(){
        var media = MediaPlayer.create(this, R.raw.coco)
        media.setOnCompletionListener {
            it.stop()
        }
        media.start()
    }

    fun showMusicAddDialog() {
        var musicAddDialog = AddMusicDialog()
        musicAddDialog.show(supportFragmentManager, "musicAdd")
        adapter.addMusic("Samuel Cargidres - Stunning Blade")

    }

    fun swipeToDelete() {
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

}