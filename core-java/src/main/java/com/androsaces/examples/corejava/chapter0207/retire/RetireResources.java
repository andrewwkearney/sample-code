/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.examples.corejava.chapter0207.retire;

import java.awt.*;
import java.util.ListResourceBundle;

/**
 * @author Andrew Kearney
 */
public class RetireResources extends ListResourceBundle {
    private static final Object[][] contents = {
            // BEGIN LOCALISE
            { "colorPre", Color.blue}, {"colorGain", Color.white}, { "colorLoss", Color.red}
            // END LOCALISE
    };

    @Override
    public Object[][] getContents() {
        return contents;
    }
}
