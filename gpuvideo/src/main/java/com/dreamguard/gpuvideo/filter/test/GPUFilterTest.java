package com.dreamguard.gpuvideo.filter.test;

import com.dreamguard.gpuvideo.filter.GPUVideoColorInvertFilter;
import com.dreamguard.gpuvideo.filter.GPUVideoGammaFilter;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;

/**
 * Created by hailin.dai on 3/4/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUFilterTest extends GPUVideoFilterGroup {

    GPUVideoColorInvertFilter invertFilter;
    GPUVideoGammaFilter gammaFilter;

    public GPUFilterTest() {
        invertFilter = new GPUVideoColorInvertFilter();
//        addFilter(new GPUVideoColorInvertFilter());
//        addFilter(new GPUVideoColorInvertFilter());
        addFilter(new GPUVideoColorInvertFilter());
        addFilter(new GPUFilterTest2());
    }
}
