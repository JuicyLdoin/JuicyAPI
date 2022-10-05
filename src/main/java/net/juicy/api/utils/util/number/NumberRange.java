package net.juicy.api.utils.util.number;

import lombok.Data;

@Data
public class NumberRange implements Cloneable {

    float lesser;
    float larger;

    public NumberRange(float number) {

        lesser = number;
        larger = number;

    }

    public NumberRange(float[] numbers) {

        if (numbers.length != 2)
            throw new RuntimeException();

        lesser = Math.min(numbers[0], numbers[1]);
        larger = Math.max(numbers[0], numbers[1]);

    }

    public NumberRange(String range) {

        float larger, lesser;

        if (!range.contains("-")) {

            lesser = Float.parseFloat(range);
            larger = Float.parseFloat(range);

        } else {

            String[] split = range.split("-");

            if (split.length != 2)
                throw new RuntimeException();

            float number1 = Float.parseFloat(split[0]);
            float number2 = Float.parseFloat(split[1]);

            lesser = Math.min(number1, number2);
            larger = Math.max(number1, number2);

        }

        this.lesser = lesser;
        this.larger = larger;

    }

    public NumberRange(float lesser, float larger) {

        this.lesser = Math.min(lesser, larger);
        this.larger = Math.max(lesser, larger);

    }

    public NumberRange clone() {

        try {

            return (NumberRange) super.clone();

        } catch (CloneNotSupportedException e) {

            throw new AssertionError();

        }
    }

    public String toString() {

        return lesser + "-" + larger;

    }
}