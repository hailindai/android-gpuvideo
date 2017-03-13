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
 * Sharpens the picture. <br>’
 * Sharpen(锐化)是Photoshop的内置滤镜之一！
 * 锐化就是使图片的局部清晰一些，但一定要适度。锐化不是万能的，很容易使东西不真实
 * Sharpen(锐化) 滤镜通过增加相邻像素的对比度来使模糊图像变清晰。
 * <br>
 * sharpness: from -4.0 to 4.0, with 0.0 as the normal level
 */
public class GPUVideoSharpenFilter extends GPUVideoFilter {
    public static final String SHARPEN_VERTEX_SHADER = "" +
            "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            "\n" +
            "uniform float imageWidthFactor; \n" +
            "uniform float imageHeightFactor; \n" +
            "uniform float sharpness;\n" +
            "\n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 leftTextureCoordinate;\n" +
            "varying vec2 rightTextureCoordinate; \n" +
            "varying vec2 topTextureCoordinate;\n" +
            "varying vec2 bottomTextureCoordinate;\n" +
            "\n" +
            "varying float centerMultiplier;\n" +
            "varying float edgeMultiplier;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = position;\n" +
            "    \n" +
            "    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n" +
            "    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n" +
            "    \n" +
            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
            "    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n" +
            "    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n" +
            "    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n" +
            "    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n" +
            "    \n" +
            "    centerMultiplier = 1.0 + 4.0 * sharpness;\n" +
            "    edgeMultiplier = sharpness;\n" +
            "}";

    public static final String SHARPEN_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "precision highp float;\n" +
            "\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "varying highp vec2 leftTextureCoordinate;\n" +
            "varying highp vec2 rightTextureCoordinate; \n" +
            "varying highp vec2 topTextureCoordinate;\n" +
            "varying highp vec2 bottomTextureCoordinate;\n" +
            "\n" +
            "varying highp float centerMultiplier;\n" +
            "varying highp float edgeMultiplier;\n" +
            "\n" +
            "uniform samplerExternalOES inputTexture;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    mediump vec3 textureColor = texture2D(inputTexture, textureCoordinate).rgb;\n" +
            "    mediump vec3 leftTextureColor = texture2D(inputTexture, leftTextureCoordinate).rgb;\n" +
            "    mediump vec3 rightTextureColor = texture2D(inputTexture, rightTextureCoordinate).rgb;\n" +
            "    mediump vec3 topTextureColor = texture2D(inputTexture, topTextureCoordinate).rgb;\n" +
            "    mediump vec3 bottomTextureColor = texture2D(inputTexture, bottomTextureCoordinate).rgb;\n" +
            "\n" +
            "    gl_FragColor = vec4((textureColor * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), texture2D(inputTexture, bottomTextureCoordinate).w);\n" +
            "}";

    private int mSharpnessLocation;
    private float mSharpness;
    private int mImageWidthFactorLocation;
    private int mImageHeightFactorLocation;

    public GPUVideoSharpenFilter() {
        this(0.0f);
    }

    public GPUVideoSharpenFilter(final float sharpness) {
        super(SHARPEN_VERTEX_SHADER, SHARPEN_FRAGMENT_SHADER);
        mSharpness = sharpness;
    }

    @Override
    public void onInit() {
        super.onInit();
        mSharpnessLocation = GLES20.glGetUniformLocation(getProgram(), "sharpness");
        mImageWidthFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageWidthFactor");
        mImageHeightFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageHeightFactor");
        setSharpness(mSharpness);
    }

    @Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
        setFloat(mImageWidthFactorLocation, 1.0f / width);
        setFloat(mImageHeightFactorLocation, 1.0f / height);
    }

    public void setSharpness(final float sharpness) {
        mSharpness = sharpness;
        setFloat(mSharpnessLocation, mSharpness);
    }
}
