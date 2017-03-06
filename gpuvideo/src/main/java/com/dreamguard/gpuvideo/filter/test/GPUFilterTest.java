package com.dreamguard.gpuvideo.filter.test;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoColorInvertFilter2D;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoGammaFilter2D;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoColorInvertFilter;
import com.dreamguard.gpuvideo.filter.textureoes.GPUVideoGammaFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;

/**
 * Created by hailin.dai on 3/4/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUFilterTest extends GPUVideoFilterGroup {

    GPUVideoColorInvertFilter invertFilter;
    GPUVideoGammaFilter gammaFilter;

    public GPUFilterTest() {
//        invertFilter = new GPUVideoColorInvertFilter();
//        addFilter(new GPUVideoColorInvertFilter());
//        addFilter(new GPUVideoColorInvertFilter());
//        addFilter(new GPUVideoColorInvertFilter());

        addFilter(new GPUVideoFilter());
        addFilter(new GPUVideoFilter2D());
        addFilter(new GPUVideoGammaFilter2D());
        addFilter(new GPUVideoColorInvertFilter2D());
        addFilter(new GPUFilterTest22D());
    }
}
