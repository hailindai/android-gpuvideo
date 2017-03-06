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

package com.dreamguard.gpuvideo.filter.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.PointF;
import android.opengl.GLES20;

import com.dreamguard.gpuvideo.OpenGlUtils;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;

import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.LinkedList;

public class GPUVideoFilter2D extends GPUVideoFilter{
    public static final String FILTER_FRAGMENT_SHADER_2D = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "varying highp vec2 textureCoordinate;\n" +
            " \n" +
            "uniform sampler2D inputTexture;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "     gl_FragColor = texture2D(inputTexture, textureCoordinate);\n" +
            "}";



    public GPUVideoFilter2D() {
        super(NO_FILTER_VERTEX_SHADER, FILTER_FRAGMENT_SHADER_2D);
    }
}
