/*
 * Copyright (C) 2012 CyberAgent
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dreamguard.gpuvideo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;


public class GPUVideoView extends GLSurfaceView {

    private GPUVideoRenderer mRenderer;

    private Context mContext;

    public GPUVideoView(Context context) {
        super(context);
        init(context,null);
    }

    public GPUVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (!supportsOpenGLES2(context)) {
            throw new IllegalStateException("OpenGL ES 2.0 is not supported on this phone.");
        }

        mContext = context;

    }

    public void init(IVideoSurface videoSurface){
        GPUVideoFilter filter = new GPUVideoFilter();
        mRenderer = new GPUVideoRenderer(filter,videoSurface);

        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.RGBA_8888);
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    /**
     * Checks if OpenGL ES 2.0 is supported on the current device.
     *
     * @param context the context
     * @return true, if successful
     */
    private boolean supportsOpenGLES2(final Context context) {
        final ActivityManager activityManager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo =
                activityManager.getDeviceConfigurationInfo();
        return configurationInfo.reqGlEsVersion >= 0x20000;
    }


    /**
     * Set the filter to be applied on the image.
     *
     * @param filter Filter that should be applied on the image.
     */
    public void setFilter(GPUVideoFilter filter) {
        mRenderer.setFilter(filter);
    }

    /**
     * Get the current applied filter.
     *
     * @return the current filter
     */
    public GPUVideoFilter getFilter() {
        return mRenderer.getFilter();
    }

    public String getVersion(){
        return GPUVideoConst.Version;
    }

}
