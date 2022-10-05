package net.juicy.api.utils.util.builders.number;

import lombok.AllArgsConstructor;
import net.juicy.api.utils.util.builders.IBuilder;

import java.util.concurrent.ThreadLocalRandom;

@AllArgsConstructor
public class NumberBuilder implements IBuilder<Float> {

    private float number;

    private float maxLimit = Float.MAX_VALUE;
    private float minLimit = Float.MIN_VALUE;

    public NumberBuilder(float number) {

        this.number = number;

    }

    public NumberBuilder setMinLimit(float limit) {

        minLimit = limit;
        return this;

    }

    public NumberBuilder setMaxLimit(float limit) {

        maxLimit = limit;
        return this;

    }

    public NumberBuilder set(float number) {

        this.number = number;
        return this;

    }

    public NumberBuilder add(float number) {

        this.number += number;
        return this;

    }

    public NumberBuilder take(float number) {

        this.number -= number;
        return this;

    }

    public NumberBuilder multiply(float number) {

        this.number *= number;
        return this;

    }

    public NumberBuilder divine(float number) {

        this.number /= number;
        return this;

    }

    public NumberBuilder addRandomFloat() {

        add(ThreadLocalRandom.current().nextFloat());
        return this;

    }

    public NumberBuilder takeRandomFloat() {

        take(ThreadLocalRandom.current().nextFloat());
        return this;

    }

    public NumberBuilder multiplyRandomFloat() {

        multiply(ThreadLocalRandom.current().nextFloat());
        return this;

    }

    public NumberBuilder divineRandomFloat() {

        divine(ThreadLocalRandom.current().nextFloat());
        return this;

    }

    public NumberBuilder addRandomInt() {

        add(ThreadLocalRandom.current().nextInt());
        return this;

    }

    public NumberBuilder takeRandomInt() {

        take(ThreadLocalRandom.current().nextInt());
        return this;

    }

    public NumberBuilder multiplyRandomInt() {

        multiply(ThreadLocalRandom.current().nextInt());
        return this;

    }

    public NumberBuilder divineRandomInt() {

        divine(ThreadLocalRandom.current().nextInt());
        return this;

    }

    public Float build() {

        return Math.max(Math.min(number, maxLimit), minLimit);

    }
}