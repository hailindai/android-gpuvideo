package com.dreamguard.sample;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;

import com.dreamguard.gpuvideo.GPUVideoView;
import com.dreamguard.gpuvideo.IVideoSurface;

import com.dreamguard.gpuvideo.filter.base.GPUVideoBitmapInputFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoBitmapInputFilter2D;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;
import com.dreamguard.gpuvideo.filter.test.GPUGroupFilterTest;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoColorInvertFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoMonochromeFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoOpacityFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoRGBFilter;


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
    }

    public void initGPUVideoView() {
        videoView.init(new IVideoSurface() {
            @Override
            public void onCreated(SurfaceTexture surfaceTexture) {
                mSurfaceTexture = surfaceTexture;
                initPlayer(mSurfaceTexture);
            }
        });

        GPUVideoColorInvertFilter invertFilter = new GPUVideoColorInvertFilter();
        videoView.setFilter(invertFilter);
    }

    public void initPlayer(SurfaceTexture surfaceTexture) {

        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.wwmxd));
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
    protected void onDestroy() {
        super.onDestroy();
        stopPlayer();
    }

    public void stopPlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
