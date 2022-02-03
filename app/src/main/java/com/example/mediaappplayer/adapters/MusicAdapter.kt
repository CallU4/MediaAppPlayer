package com.example.mediaappplayer.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mediaappplayer.databinding.ChooseMusicBinding
import com.example.mediaappplayer.models.Music

class MusicAdapter(private var songClickListener: OnItemClickListener) : RecyclerView.Adapter<MusicAdapter.MusicHolder>() {

    private var musicList = ArrayList<Music>()

    fun addMusic(name: String) {
        var song = Music(name)
        musicList.add(song)

        notifyItemInserted(itemCount)
    }

    fun deleteMusic(position: Int) {
        musicList.removeAt(position)

        notifyItemRemoved(itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicHolder {

        var bind = ChooseMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicHolder(bind)
    }

    override fun onBindViewHolder(holder: MusicHolder, position: Int) {
        holder.bind.tvFolder.text = musicList[position].name

        holder.itemView.setOnClickListener{
            songClickListener.onClick(musicList[position])
        }
    }

    override fun getItemCount(): Int = musicList.size

    inner class MusicHolder(var bind: ChooseMusicBinding) : RecyclerView.ViewHolder(bind.root)

    interface OnItemClickListener{
        fun onClick(song: Music)
    }

}