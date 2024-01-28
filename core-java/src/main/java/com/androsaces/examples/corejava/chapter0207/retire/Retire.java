/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.examples.corejava.chapter0207.retire;

import javax.swing.*;
import java.awt.*;

/**
 * @author Andrew Kearney
 */
public class Retire {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new RetireFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
