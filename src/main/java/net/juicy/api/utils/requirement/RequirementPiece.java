package net.juicy.api.utils.requirement;

import lombok.AccessLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.function.Consumer;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequirementPiece<T> {

    String objectPath;

    Object object;
    RequirementCheckType requirementCheckType;

    public boolean check(@NotNull T parent) {

        Object checked = getByPath(parent);

        if (checked == null)
            return false;

        boolean check = (requirementCheckType.equals(RequirementCheckType.EQUALS) && object.equals(checked)) ||
                (requirementCheckType.equals(RequirementCheckType.NOT_EQUALS) && !object.equals(checked));

        if (!check)
            if (object instanceof Number objectNumber && checked instanceof Number checkedNumber) {

                check = (requirementCheckType.equals(RequirementCheckType.LARGER_THAN) && objectNumber.doubleValue() >= checkedNumber.doubleValue()) ||
                        (requirementCheckType.equals(RequirementCheckType.LESSER_THAN) && objectNumber.doubleValue() <= checkedNumber.doubleValue());

            }

        return check;

    }

    public void callActionIfChecked(@NotNull T parent, @NotNull Consumer<T> consumer) {

        if (check(parent))
            consumer.accept(parent);

    }

    @Nullable
    @SneakyThrows
    private Object getByPath(@NotNull T parent) {

        Object object = null;

        String[] splitPath = objectPath.contains(".") ? objectPath.split("\\.") : new String[] { objectPath };
        Class<?> currentClass = parent.getClass();

        int index = 0;

        while (index < splitPath.length) {

            String fieldName = splitPath[index];

            Field field = currentClass.getDeclaredField(fieldName);

            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            if (fieldName.equals(splitPath[splitPath.length - 1]))
                object = field.get(field.getType());
            else
                currentClass = field.getType();

            field.setAccessible(accessible);

            index++;

        }

        return object;

    }
}