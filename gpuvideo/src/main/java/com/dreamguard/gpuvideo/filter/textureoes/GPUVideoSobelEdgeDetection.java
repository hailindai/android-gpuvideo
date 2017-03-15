package com.dreamguard.gpuvideo.filter.textureoes;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideo3x3TextureSamplingFilter2D;

/**
 * Created by hailin.dai on 3/14/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUVideoSobelEdgeDetection extends GPUVideoFilterGroup {
    public static final String SOBEL_EDGE_DETECTION = "" +
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
            "uniform sampler2D inputTexture;\n" +
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
            "    float h = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n" +
            "    float v = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n" +
            "\n" +
            "    float mag = length(vec2(h, v));\n" +
            "\n" +
            "    gl_FragColor = vec4(vec3(mag), 1.0);\n" +
            "}";

    public GPUVideoSobelEdgeDetection() {
        super();
        addFilter(new GPUVideoGrayscaleFilter());
        addFilter(new GPUVideo3x3TextureSamplingFilter2D(SOBEL_EDGE_DETECTION));
    }

    public void setLineSize(final float size) {
        ((GPUVideo3x3TextureSamplingFilter) getFilters().get(1)).setLineSize(size);
    }

}
