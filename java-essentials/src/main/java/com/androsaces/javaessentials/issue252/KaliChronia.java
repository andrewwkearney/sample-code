/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue252;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class KaliChronia {
    private static final Logger log = LoggerFactory.getLogger(KaliChronia.class);

    public static void main(String[] args) {
        Stream<LocalDate> newYearDays = StreamSupport.stream(
            new YearSpliterator(LocalDate.of(2020, Month.JANUARY, 1)), false);

        newYearDays
            .filter(day -> day.getDayOfWeek() == DayOfWeek.MONDAY)
            .takeWhile(day -> day.getYear() >= 1900)
            .forEach(day -> log.info("{}", day));

        StreamSupport.stream(
            new YearSpliterator(
                LocalDate.of(2020, Month.DECEMBER, 17)), false)
            .takeWhile(day -> day.getYear() >= 1980) // Java 9
            .map(day -> day + " -> " + day.getDayOfWeek())
            .forEach(day -> log.info("{}", day));
    }
}
