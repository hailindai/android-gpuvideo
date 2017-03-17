package com.dreamguard.sample;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;

import com.dreamguard.gpuvideo.GPUVideoView;
import com.dreamguard.gpuvideo.IVideoSurface;

import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoSobelEdgeDetection;


public class MainActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;

    private GPUVideoView videoView;

    private SurfaceTexture mSurfaceTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = (GPUVideoView) findViewById(R.id.videoView);

        initGPUVideoView();


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, 1);

    }

    public void initGPUVideoView() {
        videoView.init(new IVideoSurface() {
            @Override
            public void onCreated(SurfaceTexture surfaceTexture) {
                mSurfaceTexture = surfaceTexture;
            }
        });

        videoView.setFilter(new GPUVideoSobelEdgeDetection());
    }

    public void initPlayer(SurfaceTexture surfaceTexture, String path) {

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(path);
            Surface s = new Surface(surfaceTexture);
            mediaPlayer.setSurface(s);
            s.release();
            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onResume();
        stopPlayer();
    }

    public void stopPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedVideo = data.getData();
            String videoPath = selectedVideo.getPath();
            if(mSurfaceTexture == null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            initPlayer(mSurfaceTexture, videoPath);
        }
    }
}
