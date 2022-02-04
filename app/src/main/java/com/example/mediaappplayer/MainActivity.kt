package com.example.mediaappplayer

import android.Manifest
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaappplayer.adapters.MusicAdapter
import com.example.mediaappplayer.databinding.ActivityMainBinding
import com.example.mediaappplayer.dialogs.AddMusicDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding
    var media = MediaPlayer()
    private var pos = 1
    private var adapter = MusicAdapter(object : MusicAdapter.OnItemClickListener {
        override fun onItemClick(position: Int) {
            println(position)
            playMusic("${position + 1}", position)
        }
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        bind.recyclerView.layoutManager = LinearLayoutManager(this)
        bind.recyclerView.adapter = adapter



        callRuntimePermission()



        bind.btnAddMusic.setOnClickListener {
            showMusicAddDialog()
        }

        swipeToDelete()
    }

    private fun callRuntimePermission() {
        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object: PermissionListener {
                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }
            })
            .check();
    }

    fun playMusic(songName: String, position: Int) {
        if (!media.isPlaying) {
            pos = position
            var path = "/sdcard/Download/$songName.mp3"
            try {
                media.setDataSource(path)
                media.prepare()
                media.start()
            } catch (ex: IllegalStateException) {
                println(ex)
            } catch (ex: Exception) {
                println(ex)
            }
        } else {
            media.pause()
        }
    }

    fun showMusicAddDialog() {
        var musicAddDialog = AddMusicDialog()
        musicAddDialog.show(supportFragmentManager, "musicAdd")
        adapter.addMusic("Dances")

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