/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.functional.concurrency.lessonfive.forkjoin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Andrew Kearney
 */
public class FindMin extends RecursiveTask<Integer> {
    private static final Logger log = LoggerFactory.getLogger(FindMin.class);

    private static final long serialVersionUID = 1L;
    private int[] numbers;
    private int startIndex;
    private int endIndex;

    public FindMin(int[] numbers, int startIndex, int endIndex) {
        this.numbers = numbers;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    protected Integer compute() {
        int sliceLength = (endIndex - startIndex) + 1;
        if (sliceLength > 2) {
            FindMin lowerFindMin = new FindMin(numbers, startIndex, startIndex + (sliceLength / 2) - 1);
            lowerFindMin.fork();
            FindMin upperFindMin = new FindMin(numbers, startIndex + (sliceLength / 2), endIndex);
            upperFindMin.fork();
            return Math.min(lowerFindMin.join(), upperFindMin.join());
        } else {
            return Math.min(numbers[startIndex], numbers[endIndex]);
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Integer min = pool.invoke(new FindMin(numbers, 0, numbers.length - 1));
        log.info("{}", min);
    }
}
