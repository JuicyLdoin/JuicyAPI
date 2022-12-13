package net.juicy.api.utils.util.collection;

import lombok.AllArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@AllArgsConstructor
public class ArrayManager<T> {

    private final T[] array;

    public T[] newLength(int length) {

        if (array.length < length)
            throw new UnsupportedOperationException("New length cannot be smaller than array length");

        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length);

        IntStream.range(0, array.length)
                .forEach(i -> newArray[i] = array[i]);

        return newArray;

    }

    public T[] addElement(int index, T element) {

        if (array.length <= index)
            throw new ArrayIndexOutOfBoundsException();

        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length + 1);

        AtomicBoolean added = new AtomicBoolean(false);

        IntStream.range(0, array.length)
                .forEach(i -> {

                    if (i == index) {

                        newArray[i] = element;
                        added.set(true);

                    } else
                        newArray[added.get() ? (i + 1) : i] = array[i];

                });

        return newArray;

    }

    public T[] removeElement(int index) {

        if (array.length <= index)
            throw new ArrayIndexOutOfBoundsException();

        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);

        AtomicBoolean skipped = new AtomicBoolean(false);

        IntStream.range(0, array.length)
                .forEach(i -> {

                    if (i == index)
                        skipped.set(true);
                    else
                        newArray[skipped.get() ? (i - 1) : i] = array[i];

                });

        return newArray;

    }

    public T[] removeElement(T element) {

        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - 1);

        IntStream.range(0, array.length)
                .filter(i -> !array[i].equals(element))
                .forEach(i -> newArray[i] = array[i]);

        return newArray;

    }

    public void forEach(Consumer<T> consumer) {

        stream().forEach(consumer);

    }

    public Stream<T> filter(Predicate<T> predicate) {

        return stream().filter(predicate);

    }

    public List<T> toCollect() {

        return stream().collect(Collectors.toList());

    }

    public void clear() {

        IntStream.range(0, array.length).forEach(i -> array[i] = null);

    }

    public Stream<T> stream() {

        return Arrays.stream(array);

    }
}