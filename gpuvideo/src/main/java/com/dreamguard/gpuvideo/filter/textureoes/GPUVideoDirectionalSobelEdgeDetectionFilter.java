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


public class GPUVideoDirectionalSobelEdgeDetectionFilter extends GPUVideo3x3TextureSamplingFilter {
    public static final String DIRECTIONAL_SOBEL_EDGE_DETECTION_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision mediump float;\n" +
            "\n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 leftTextureCoordinate;\n" +
            "varying vec2 rightTextureCoordinate;\n" +
            "\n" +
            "varying vec2 topTextureCoordinate;\n" +
            "varying vec2 topLeftTextureCoordinate;\n" +
            "varying vec2 topRightTextureCoordinate;\n" +
            "\n" +
            "varying vec2 bottomTextureCoordinate;\n" +
            "varying vec2 bottomLeftTextureCoordinate;\n" +
            "varying vec2 bottomRightTextureCoordinate;\n" +
            "\n" +
            "uniform samplerExternalOES inputTexture;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    float bottomLeftIntensity = texture2D(inputTexture, bottomLeftTextureCoordinate).r;\n" +
            "    float topRightIntensity = texture2D(inputTexture, topRightTextureCoordinate).r;\n" +
            "    float topLeftIntensity = texture2D(inputTexture, topLeftTextureCoordinate).r;\n" +
            "    float bottomRightIntensity = texture2D(inputTexture, bottomRightTextureCoordinate).r;\n" +
            "    float leftIntensity = texture2D(inputTexture, leftTextureCoordinate).r;\n" +
            "    float rightIntensity = texture2D(inputTexture, rightTextureCoordinate).r;\n" +
            "    float bottomIntensity = texture2D(inputTexture, bottomTextureCoordinate).r;\n" +
            "    float topIntensity = texture2D(inputTexture, topTextureCoordinate).r;\n" +
            "\n" +
            "    vec2 gradientDirection;\n" +
            "    gradientDirection.x = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n" +
            "    gradientDirection.y = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n" +
            "\n" +
            "    float gradientMagnitude = length(gradientDirection);\n" +
            "    vec2 normalizedDirection = normalize(gradientDirection);\n" +
            "    normalizedDirection = sign(normalizedDirection) * floor(abs(normalizedDirection) + 0.617316); // Offset by 1-sin(pi/8) to set to 0 if near axis, 1 if away\n" +
            "    normalizedDirection = (normalizedDirection + 1.0) * 0.5; // Place -1.0 - 1.0 within 0 - 1.0\n" +
            "\n" +
            "    gl_FragColor = vec4(gradientMagnitude, normalizedDirection.x, normalizedDirection.y, 1.0);\n" +
            "}";

    public GPUVideoDirectionalSobelEdgeDetectionFilter() {
        super(DIRECTIONAL_SOBEL_EDGE_DETECTION_FRAGMENT_SHADER);
    }
}
