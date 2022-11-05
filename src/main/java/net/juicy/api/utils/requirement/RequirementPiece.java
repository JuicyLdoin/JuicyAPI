package net.juicy.api.utils.requirement;

import lombok.AccessLevel;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

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
            if (object instanceof Number && checked instanceof Number) {

                Number objectNumber = (Number) object;
                Number checkedNumber = (Number) checked;

                check = (requirementCheckType.equals(RequirementCheckType.LARGER_THAN) && objectNumber.doubleValue() >= checkedNumber.doubleValue()) ||
                        (requirementCheckType.equals(RequirementCheckType.LESSER_THAN) && objectNumber.doubleValue() <= checkedNumber.doubleValue());

            }

        return check;

    }

    @Nullable
    @SneakyThrows
    private Object getByPath(@NotNull T parent) {

        Class<?> clazz = parent.getClass();
        Object object = null;

        if (objectPath.contains(".")) {

            String[] splitPath = objectPath.split("\\.");
            Class<?> currentClass = clazz;

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
        } else {

            Field field = clazz.getDeclaredField(objectPath);

            boolean accessible = field.isAccessible();
            field.setAccessible(true);

            object = field.get(field.getType());

            field.setAccessible(accessible);

        }

        return object;

    }
}