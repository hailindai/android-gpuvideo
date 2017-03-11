package com.dreamguard.gpuvideo.filter.base;

import android.opengl.GLES20;

import com.dreamguard.gpuvideo.OpenGlUtils;

import java.nio.FloatBuffer;
import java.util.LinkedList;

/**
 * Created by Administrator on 2017/3/11.
 */

public class GPUVideoFilter2D extends GPUVideoFilter {
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

    public GPUVideoFilter2D(final String vertexShader, final String fragmentShader) {
        super(vertexShader, fragmentShader);
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
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
            GLES20.glUniform1i(mGLUniformTexture, 0);
        }
        onDrawArraysPre();
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glDisableVertexAttribArray(mGLAttribPosition);
        GLES20.glDisableVertexAttribArray(mGLAttribTextureCoordinate);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
}
