/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue271;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WikimediaImageInfo extends ImageInfo {
    public WikimediaImageInfo findImage(String body) {
        Pattern pattern = Pattern.compile("<img\\s+alt=\"[^\"]+\"\\s+src=\"(?<src>[^\\\"]+)\"");
        Matcher matcher = pattern.matcher(body);
        if (matcher.find()) {
            this.imagePath = matcher.group("src");
        } else {
            this.imagePath = null;
        }
        return this;
    }

    @Override
    public String getUrlForDate(LocalDate date) {
        return "https://commons.wikimedia.org/wiki/Special:FeedItem/potd/" + DateTimeFormatter.BASIC_ISO_DATE.format(date) + "000000/en";
    }
}