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

package com.dreamguard.gpuvideo.filter.textureoes;

import android.opengl.GLES20;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;

/**
 * Adjusts the alpha channel of the incoming image
 * opacity: The value to multiply the incoming alpha channel for each pixel by (0.0 - 1.0, with 1.0 as the default)
*/
public class GPUVideoOpacityFilter extends GPUVideoFilter {
    public static final String OPACITY_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "  varying highp vec2 textureCoordinate;\n" +
            "  \n" +
            "  uniform samplerExternalOES inputTexture;\n" +
            "  uniform lowp float opacity;\n" +
            "  \n" +
            "  void main()\n" +
            "  {\n" +
            "      lowp vec4 textureColor = texture2D(inputTexture, textureCoordinate);\n" +
            "      \n" +
            "      gl_FragColor = vec4(textureColor.rgb, textureColor.a * opacity);\n" +
            "  }\n";

    private int mOpacityLocation;
    private float mOpacity;

    public GPUVideoOpacityFilter() {
        this(1.0f);
    }

    public GPUVideoOpacityFilter(final float opacity) {
        super(NO_FILTER_VERTEX_SHADER, OPACITY_FRAGMENT_SHADER);
        mOpacity = opacity;
    }

    @Override
    public void onInit() {
        super.onInit();
        mOpacityLocation = GLES20.glGetUniformLocation(getProgram(), "opacity");
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
        setOpacity(mOpacity);
    }

    public void setOpacity(final float opacity) {
        mOpacity = opacity;
        setFloat(mOpacityLocation, mOpacity);
    }
}
