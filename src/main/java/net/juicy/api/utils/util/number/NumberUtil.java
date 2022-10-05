package net.juicy.api.utils.util.number;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtil {

    public static boolean random(float chance) {

        return (ThreadLocalRandom.current().nextFloat() <= chance);

    }

    public static int randomInRange(int min, int max) {

        if (min >= max)
            return 0;

        return ThreadLocalRandom.current().nextInt((int) (max - min)) + min;

    }

    public static int randomInRange(NumberRange range) {

        return randomInRange((int) range.getLesser(), (int) range.getLarger());

    }

    public static boolean isInRange(NumberRange range, int number) {

        return range.getLesser() <= number && number <= range.getLarger();

    }

    public static float getPercentageOfValue(String percentage, float value) {

        if (percentage.endsWith("%"))
            return getPercentageOfValue(Float.parseFloat(percentage.replace("%", "")), value);

        return getPercentageOfValue(Float.parseFloat(percentage), value);

    }

    public static float getPercentageOfValue(float percentage, float value) {

        return value / 100.0F * percentage;

    }

    public static float addPercentage(float number, float percentage) {

        return number + getPercentageOfValue(percentage, number);

    }
}