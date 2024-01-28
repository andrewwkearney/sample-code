/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.examples.corejava.chapter0207.retire;

import java.awt.*;

/**
 * @author Andrew Kearney
 */
public class GBC extends GridBagConstraints {
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    public GBC(int gridx, int gridy, int gridWidth, int gridHeight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }
}
