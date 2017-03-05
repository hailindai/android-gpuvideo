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

package com.dreamguard.gpuvideo.filter.test;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;

/**
 * Invert all the colors in the image.
 */
public class GPUFilterTest2 extends GPUVideoFilter {
    public static final String COLOR_TEST_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n"+
            "varying vec2 textureCoordinate;\n" +
            "\n" +
            "uniform samplerExternalOES inputTexture;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "		 vec2 tc = textureCoordinate;\n"
            + "		float xx = floor(gl_FragCoord.x);\n"
		    + "		if(mod(xx,2.0) <= 0.0001){\n"
		    + "			tc.s = tc.s*0.5 + 0.5; \n"
		    + "		}else{\n"
		    + "			tc.s = tc.s*0.5;\n"
		    + "		}\n"
            + "  gl_FragColor = texture2D(inputTexture, tc);\n"
            + "}";

    public GPUFilterTest2() {
        super(NO_FILTER_VERTEX_SHADER, COLOR_TEST_FRAGMENT_SHADER);
    }
}
