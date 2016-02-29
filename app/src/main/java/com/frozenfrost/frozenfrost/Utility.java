package com.frozenfrost.frozenfrost;

/**
 * Created by ShAg on 29-02-2016.
 */
public class Utility {
    public static boolean isValidString(String value)
    {
        return (!"".equals(value.trim()) && value.trim() != null && value.trim().length()>=1);
    }
}