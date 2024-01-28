/*
 * Copyright 2022. Androsaces. All rights reserved.
 */

package com.androsaces.examples.corejava.chapter0207.retire;

public class RetireInfo {
    private double savings;
    private double contrib;
    private double income;
    private int currentAge;
    private int retireAge;
    private int deathAge;
    private double inflationPercent;
    private double investPercent;
    private int age;
    private double balance;

    /**
     * Gets the available balance for a given year.
     * @param year the year for which to compute the balance
     * @return the amount of money available (or required_ in that year
     */
    public double getBalance(int year) {
        if (year < currentAge) return 0;
        else if (year == currentAge) {
          age = year;
          balance = savings;
          return balance;
        } else if (year == age) return balance;
        if (year != age + 1) getBalance(year - 1);
        age = year;
        if (age < retireAge) balance += contrib;
        else balance -= income;
        balance = balance * (1 + (investPercent - inflationPercent));
        return balance;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double newValue) {
        savings = newValue;
    }

    public double getContrib() {
        return contrib;
    }

    public void setContrib(double newValue) {
        contrib = newValue;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double newValue) {
        income = newValue;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int newValue) {
        currentAge = newValue;
    }

    public int getRetireAge() {
        return retireAge;
    }

    public void setRetireAge(int newValue) {
        retireAge = newValue;
    }

    public int getDeathAge() {
        return deathAge;
    }

    public void setDeathAge(int newValue) {
        deathAge = newValue;
    }

    public double getInflationPercent() {
        return inflationPercent;
    }

    public void setInflationPercent(double inflationPercent) {
        this.inflationPercent = inflationPercent;
    }

    public double getInvestPercent() {
        return investPercent;
    }

    public void setInvestPercent(double investPercent) {
        this.investPercent = investPercent;
    }
}
