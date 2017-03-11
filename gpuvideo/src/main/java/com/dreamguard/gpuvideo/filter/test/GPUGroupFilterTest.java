package com.dreamguard.gpuvideo.filter.test;

import com.dreamguard.gpuvideo.filter.base.GPUVideoFilter;
import com.dreamguard.gpuvideo.filter.texture2d.GPUVideoContrastFilter2D;
import com.dreamguard.gpuvideo.filter.base.GPUVideoFilterGroup;

/**
 * Created by hailin.dai on 3/4/17.
 * email:hailin.dai@wz-tech.com
 */

public class GPUGroupFilterTest extends GPUVideoFilterGroup {

    public GPUGroupFilterTest() {

        addFilter(new GPUVideoFilter());
        addFilter(new GPUVideoContrastFilter2D());

    }
}
