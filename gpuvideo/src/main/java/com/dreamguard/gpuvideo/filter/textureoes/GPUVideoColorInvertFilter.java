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

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;

/**
 * Invert all the colors in the image.
 * 反色
 */
public class GPUVideoColorInvertFilter extends GPUVideoFilter {
    public static final String COLOR_INVERT_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "\n" +
            "uniform samplerExternalOES inputTexture;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    lowp vec4 textureColor = texture2D(inputTexture, textureCoordinate);\n" +
            "    \n" +
            "    gl_FragColor = vec4((1.0 - textureColor.rgb), textureColor.w);\n" +
            "}";

    public GPUVideoColorInvertFilter() {
        super(NO_FILTER_VERTEX_SHADER, COLOR_INVERT_FRAGMENT_SHADER);
    }
}
