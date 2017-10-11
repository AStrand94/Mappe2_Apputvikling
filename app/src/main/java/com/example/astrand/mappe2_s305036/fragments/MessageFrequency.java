package com.example.astrand.mappe2_s305036.fragments;



public enum MessageFrequency {
    DAILY("Daily"), WEEKLY("Weekly"), EVERY_OTHER_WEEK("Every other week"), MONTHLY("Every month");

    String freq;

    MessageFrequency(String freq) {
        this.freq = freq;
    }
}
