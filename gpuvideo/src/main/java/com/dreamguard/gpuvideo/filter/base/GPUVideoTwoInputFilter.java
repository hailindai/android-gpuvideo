package com.dreamguard.gpuvideo.filter.base;

import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.dreamguard.gpuvideo.IVideoSurface;
import com.dreamguard.gpuvideo.OpenGlUtils;
import com.dreamguard.gpuvideo.Rotation;
import com.dreamguard.gpuvideo.TextureRotationUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by hailin.dai on 3/13/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUVideoTwoInputFilter extends GPUVideoFilter {
    public static final String FILTER_VERTEX_SHADER = "" +
            "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            "attribute vec4 inputTextureCoordinate2;\n" +
            " \n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 textureCoordinate2;\n" +

            " \n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = position;\n" +
            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
            "    textureCoordinate2 = inputTextureCoordinate2.xy;\n" +
            "}";
    public static final String FILTER_FRAGMENT_SHADER = "" +
            "#extension GL_OES_EGL_image_external : require\n" +
            "varying highp vec2 textureCoordinate;\n" +
            "varying highp vec2 textureCoordinate2;\n" +
            "uniform float videoWidthFactor;\n" +
            " \n" +
            "uniform samplerExternalOES inputTexture;\n" +
            "uniform samplerExternalOES inputTexture2;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "	  float xx=floor(gl_FragCoord.x);\n" +
            "     if(xx<videoWidthFactor/2.0){  \n" +
            "       gl_FragColor = texture2D(inputTexture, textureCoordinate);\n" +
            "     }else{  \n" +
            "       gl_FragColor = texture2D(inputTexture2, textureCoordinate2);\n" +
            "     } \n" +
            "}";

    public static final int NO_VIDEO = -1;
    private int mGLTextureId = NO_VIDEO;
    public int mFilterSecondTextureCoordinateAttribute;
    public int mFilterInputTextureUniform2;
    private ByteBuffer mTexture2CoordinatesBuffer;
    private SurfaceTexture mSurfaceTexture;
    private IVideoSurface mVideoSurface;

    private int mVideoWidthLocation;

    public GPUVideoTwoInputFilter() {
        super(FILTER_VERTEX_SHADER,FILTER_FRAGMENT_SHADER);
        setRotation(Rotation.NORMAL, false, false);
    }

    public GPUVideoTwoInputFilter(String fragmentShader){
        super(FILTER_VERTEX_SHADER,fragmentShader);
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override
    public void onInit() {
        super.onInit();
        mVideoWidthLocation = GLES20.glGetUniformLocation(getProgram(), "videoWidthFactor");
        mFilterSecondTextureCoordinateAttribute = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate2");
        mFilterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputTexture2"); // This does assume a name of "inputImageTexture2" for second input texture in the fragment shader
        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);
        mGLTextureId = initTex();
        mSurfaceTexture = new SurfaceTexture(mGLTextureId);
        if(mVideoSurface != null){
            mVideoSurface.onCreated(mSurfaceTexture);
        }
    }

    @Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
        setFloat(mVideoWidthLocation, width);
    }

    public void setCallback(IVideoSurface surface){
        mVideoSurface = surface;
    }

    public int initTex() {
        final int[] tex = new int[1];
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glGenTextures(1, tex, 0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0]);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        return tex[0];
    }

    @Override
    protected void onDrawArraysPre() {

        GLES20.glEnableVertexAttribArray(mFilterSecondTextureCoordinateAttribute);
        mTexture2CoordinatesBuffer.position(0);
        GLES20.glVertexAttribPointer(mFilterSecondTextureCoordinateAttribute, 2, GLES20.GL_FLOAT, false, 0, mTexture2CoordinatesBuffer);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, mGLTextureId);
        GLES20.glUniform1i(mFilterInputTextureUniform2, 1);
   }

    public void onDraw(final int textureId, final FloatBuffer cubeBuffer,
                       final FloatBuffer textureBuffer) {
        GLES20.glUseProgram(mGLProgId);
        runPendingOnDrawTasks();
        if (!mIsInitialized) {
            return;
        }

        cubeBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribPosition, 2, GLES20.GL_FLOAT, false, 0, cubeBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribPosition);
        textureBuffer.position(0);
        GLES20.glVertexAttribPointer(mGLAttribTextureCoordinate, 2, GLES20.GL_FLOAT, false, 0,
                textureBuffer);
        GLES20.glEnableVertexAttribArray(mGLAttribTextureCoordinate);
        if (textureId != OpenGlUtils.NO_TEXTURE) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }
        onDrawArraysPre();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);

        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();
//            mSurfaceTexture.getTransformMatrix(mStMatrix);
        }
    }

    public void setRotation(final Rotation rotation, final boolean flipHorizontal, final boolean flipVertical) {
        float[] buffer = TextureRotationUtil.getRotation(rotation, flipHorizontal, flipVertical);

        ByteBuffer bBuffer = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer fBuffer = bBuffer.asFloatBuffer();
        fBuffer.put(buffer);
        fBuffer.flip();

        mTexture2CoordinatesBuffer = bBuffer;
    }
}
