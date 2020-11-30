/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue252;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

public class YearSpliterator implements Spliterator<LocalDate> {
    private LocalDate date;

    public YearSpliterator(LocalDate startDate) {
        this.date = startDate;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public boolean tryAdvance(Consumer<? super LocalDate> action) {
        Objects.requireNonNull(action);
        action.accept(date);
        date = date.minusYears(1);
        return true;
    }

    @Override
    public Spliterator<LocalDate> trySplit() {
        return null;
    }

    @Override
    public int characteristics() {
        return DISTINCT | IMMUTABLE | NONNULL | ORDERED | SORTED;
    }

    @Override
    public Comparator<? super LocalDate> getComparator() {
        return Comparator.reverseOrder();
    }
}
