/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue265;

import java.util.LinkedList;

/**
 * What is wrong with this class?
 *
 * <b>Answer:</b>: Here are some of the things you could point out:
 * <ol>
 * <li>Since offer() is not synchronized, notify() will throw an IllegalMonitorStateException.</li>
 * <li>Similarly, since take() is not synchronized, wait() will throw an IllegalMonitorStateException.</li>
 * <li>In take(), we should use while and not if to check the precondition.</li>
 * <li>It is possible that a notify and an interrupt are sent to the same waiting thread. We thus need to catch InterruptedException in take() and call notify() again in case this happens. This problem would go away if we used notifyAll().</li>
 * <li>We should synchronize on a private monitor if we use notify() instead of notifyAll(), otherwise alien code could synchronize on our instance and "steal" the notify.</li>
 * <li>offer() unnecessarily declares InterruptedException.</li>
 * </ol>
 */
public class Threading01<E> {
   private final LinkedList<E> elements = new LinkedList<>();

   public boolean offer(E e) throws InterruptedException {
      elements.add(e);
      notify();
      return true;
   }

   public E take() throws InterruptedException {
      if (elements.isEmpty()) {
         wait();
      }
      return elements.removeFirst();
   }

   public static void main(String[] args) throws Exception {
      Threading01<String> clazz = new Threading01<>();
      Runnable runnable = () -> {
         try {
            clazz.offer("test string");
         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
         }
      };

      Runnable take = () -> {
         try {
            clazz.take();
         } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
         }
      };
      runnable.run();
      take.run();
   }
}
