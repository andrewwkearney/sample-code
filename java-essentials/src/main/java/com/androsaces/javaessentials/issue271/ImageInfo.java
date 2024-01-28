/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue271;

import java.time.LocalDate;

abstract class ImageInfo {
    private String date;
    protected String imagePath;
    private byte[] imageData;

    public abstract ImageInfo findImage(String body);

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public ImageInfo setImageData(byte[] imageData) {
        this.imageData = imageData;
        return this;
    }

    public abstract String getUrlForDate(LocalDate date);
}
