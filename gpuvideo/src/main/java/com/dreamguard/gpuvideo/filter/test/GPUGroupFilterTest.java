package com.dreamguard.gpuvideo.filter.test;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideo3x3ConvolutionFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoColorInvertFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoContrastFilter2D;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoDirectionalSobelEdgeDetectionFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoExposureFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoHighlightShadowFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoMonochromeFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoOpacityFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoPosterizeFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoRGBFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoSaturationFilter2D;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideo3x3ConvolutionFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoPosterizeFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoRGBFilter;

/**
 * Created by hailin.dai on 3/4/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUGroupFilterTest extends GPUVideoFilterGroup {

    public GPUGroupFilterTest() {

        addFilter(new GPUVideoFilter());
        addFilter(new GPUVideoOpacityFilter2D());
    }
}
