# GPUVideo  for Android
Goal is to have something as similar to GPUImage as possible.

一个视频的滤镜项目，灵感来源于GPUImage。

<h2>Requirements</h2>

    Android 2.2 or higher (OpenGL ES 2.0)

<h2>Usage</h2>

Gradle dependency

    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }

    dependencies {
        compile 'com.github.hailindai:android-gpuvideo:1.1.0'
    }

<h2>Sample Code</h2>

    //加载视频显示控件
    GPUVideoView videoView = (GPUVideoView)findViewById(R.id.videoView);
    //初始化
    videoView.init(new IVideoSurface() {
        @Override
        public void onCreated(SurfaceTexture surfaceTexture) {
            //播放视频
            initPlayer(surfaceTexture);
        }
    });
    //设置滤镜
    videoView.setFilter(new GPUVideoColorInvertFilter());

    //使用Mediaplayer 播放视频
    public void initPlayer(SurfaceTexture surfaceTexture){
        mediaPlayer = new MediaPlayer();

        try {
            mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.girl4));
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

<h2>License</h2>

    Copyright 2012 CyberAgent, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.