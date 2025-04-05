package com.example.gurudev

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class naadYogaFragment : Fragment() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private lateinit var btnPlay: ImageButton
    private lateinit var btnPrevious: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvTotalDuration: TextView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_naad_yoga, container, false)

        btnPlay = view.findViewById(R.id.btnPlay)
        btnPrevious = view.findViewById(R.id.btnPrevious)
        btnNext = view.findViewById(R.id.btnNext)
        seekBar = view.findViewById(R.id.seekBar)
        tvCurrentTime = view.findViewById(R.id.tvCurrentTime)
        tvTotalDuration = view.findViewById(R.id.tvTotalDuration)

        initializeMediaPlayer()
        setAudioMaxVolume()

        btnPlay.setOnClickListener {
            togglePlayPause()
        }

        btnPrevious.setOnClickListener {
            seekBy(-10000)
        }

        btnNext.setOnClickListener {
            seekBy(10000)
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    tvCurrentTime.text = formatTime(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        return view
    }

    private fun initializeMediaPlayer() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.naad_yoga)
        mediaPlayer?.setOnPreparedListener {
            seekBar.max = it.duration
            tvTotalDuration.text = formatTime(it.duration)
        }
        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            btnPlay.setImageResource(R.drawable.ic_play)
            seekBar.progress = 0
        }
    }

    private fun togglePlayPause() {
        if (mediaPlayer == null) {
            initializeMediaPlayer()
        }

        val avd = if (isPlaying) {
            ContextCompat.getDrawable(requireContext(), R.drawable.avd_pause_to_play) as AnimatedVectorDrawable
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.avd_play_to_pause) as AnimatedVectorDrawable
        }

        btnPlay.setImageDrawable(avd)
        avd.start()

        if (isPlaying) {
            mediaPlayer?.pause()
            Log.d("MediaPlayer", "Audio Paused")
        } else {
            mediaPlayer?.start()
            Log.d("MediaPlayer", "Audio Started")
            updateSeekBar()
        }
        isPlaying = !isPlaying
    }

    private fun seekBy(milliseconds: Int) {
        mediaPlayer?.let {
            val newPosition = (it.currentPosition + milliseconds).coerceIn(0, it.duration)
            it.seekTo(newPosition)
            seekBar.progress = newPosition
        }
    }

    private fun updateSeekBar() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        seekBar.progress = it.currentPosition
                        tvCurrentTime.text = formatTime(it.currentPosition)
                        handler.postDelayed(this, 500)
                    }
                }
            }
        }, 500)
    }

    private fun formatTime(ms: Int): String {
        val minutes = (ms / 1000) / 60
        val seconds = (ms / 1000) % 60
        return String.format("%02d:%02d/", minutes, seconds)
    }

    private fun setAudioMaxVolume() {
        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
