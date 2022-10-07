package net.juicy.api.utils.util.builders.number;

import net.juicy.api.utils.util.builders.IBuilder;
import net.juicy.api.utils.util.number.NumberRange;

public class NumberRangeBuilder implements IBuilder<NumberRange> {

    private final NumberBuilder min = new NumberBuilder(Float.MIN_VALUE);
    private final NumberBuilder max = new NumberBuilder(Float.MAX_VALUE);

    private float minLimit = Float.MIN_VALUE;
    private float maxLimit = Float.MAX_VALUE;

    public NumberRangeBuilder setMinLimit(float limit) {

        minLimit = limit;
        return this;

    }

    public NumberRangeBuilder setMaxLimit(float limit) {

        maxLimit = limit;
        return this;

    }

    public NumberRangeBuilder setMin(float number) {

        min.set(number);
        return this;

    }

    public NumberRangeBuilder addMin(float number) {

        min.add(number);
        return this;

    }

    public NumberRangeBuilder takeMin(float number) {

        min.take(number);
        return this;

    }

    public NumberRangeBuilder multiplyMin(float number) {

        min.multiply(number);
        return this;

    }

    public NumberRangeBuilder divineMin(float number) {

        min.divine(number);
        return this;

    }

    public NumberRangeBuilder addRandomFloatMin() {

        min.addRandomFloat();
        return this;

    }

    public NumberRangeBuilder takeRandomFloatMin() {

        min.takeRandomFloat();
        return this;

    }

    public NumberRangeBuilder multiplyRandomFloatMin() {

        min.multiplyRandomFloat();
        return this;

    }

    public NumberRangeBuilder divineRandomFloatMin() {

        min.divineRandomFloat();
        return this;

    }

    public NumberRangeBuilder addRandomIntMin() {

        min.addRandomInt();
        return this;

    }

    public NumberRangeBuilder takeRandomIntMin() {

        min.takeRandomInt();
        return this;

    }

    public NumberRangeBuilder multiplyRandomIntMin() {

        min.multiplyRandomInt();
        return this;

    }

    public NumberRangeBuilder divineRandomIntMin() {

        min.divineRandomInt();
        return this;

    }

    public NumberRangeBuilder setMax(float number) {

        max.set(number);
        return this;

    }

    public NumberRangeBuilder addMax(float number) {

        max.add(number);
        return this;

    }

    public NumberRangeBuilder takeMax(float number) {

        max.take(number);
        return this;

    }

    public NumberRangeBuilder multiplyMax(float number) {

        max.multiply(number);
        return this;

    }

    public NumberRangeBuilder divineMax(float number) {

        max.divine(number);
        return this;

    }

    public NumberRangeBuilder addRandomFloatMax() {

        max.addRandomFloat();
        return this;

    }

    public NumberRangeBuilder takeRandomFloatMax() {

        max.takeRandomFloat();
        return this;

    }

    public NumberRangeBuilder multiplyRandomFloatMax() {

        max.multiplyRandomFloat();
        return this;

    }

    public NumberRangeBuilder divineRandomFloatMax() {

        max.divineRandomFloat();
        return this;

    }

    public NumberRangeBuilder addRandomIntMax() {

        max.addRandomInt();
        return this;

    }

    public NumberRangeBuilder takeRandomIntMax() {

        max.takeRandomInt();
        return this;

    }

    public NumberRangeBuilder multiplyRandomIntMax() {

        max.multiplyRandomInt();
        return this;

    }

    public NumberRangeBuilder divineRandomIntMax() {

        max.divineRandomInt();
        return this;

    }

    public NumberRange applyFor(NumberRange numberRange) {

        if (numberRange.getLesser() > minLimit)
            numberRange.setLesser(min
                    .setMinLimit(minLimit)
                    .build());

        if (numberRange.getLarger() < maxLimit)
            numberRange.setLarger(max
                    .setMaxLimit(maxLimit)
                    .build());

        return numberRange;

    }

    public NumberRange build() {

        return new NumberRange(
                min.setMinLimit(minLimit)
                        .build(),
                max.setMaxLimit(maxLimit)
                        .build());

    }
}