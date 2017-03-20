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

package com.dreamguard.gpuvideo.filter.texture2d;

import android.opengl.GLES20;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter2D;

/**
 * Reduces the color range of the image. <br>
 * 多色调分色印（相片）
 * <br>
 * colorLevels: ranges from 1 to 256, with a default of 10
 */
public class GPUVideoPosterizeFilter2D extends GPUVideoFilter2D {
    public static final String POSTERIZE_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "\n" +
            "uniform sampler2D inputTexture;\n" +
            "uniform highp float colorLevels;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "   highp vec4 textureColor = texture2D(inputTexture, textureCoordinate);\n" +
            "   \n" +
            "   gl_FragColor = floor((textureColor * colorLevels) + vec4(0.5)) / colorLevels;\n" +
            "}";

    private int mGLUniformColorLevels;
    private int mColorLevels;

    public GPUVideoPosterizeFilter2D() {
        this(10);
    }

    public GPUVideoPosterizeFilter2D(final int colorLevels) {
        super(NO_FILTER_VERTEX_SHADER, POSTERIZE_FRAGMENT_SHADER);
        mColorLevels = colorLevels;
    }

    @Override
    public void onInit() {
        super.onInit();
        mGLUniformColorLevels = GLES20.glGetUniformLocation(getProgram(), "colorLevels");
        setColorLevels(mColorLevels);
    }

    public void setColorLevels(final int colorLevels) {
        mColorLevels = colorLevels;
        setFloat(mGLUniformColorLevels, colorLevels);
    }
}
