package net.juicy.api.utils.util.builders.requirement;

import net.juicy.api.utils.requirement.RequirementCheckType;
import net.juicy.api.utils.requirement.RequirementPiece;
import net.juicy.api.utils.util.builders.IBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RequirementPieceBuilder<T> implements IBuilder<RequirementPiece<T>> {

    private final RequirementPiece<T> requirementPiece = new RequirementPiece<>();

    public RequirementPieceBuilder<T> setObjectPath(String objectPath) {

        requirementPiece.setObjectPath(objectPath);
        return this;

    }

    public RequirementPieceBuilder<T> setObject(Object object) {

        requirementPiece.setObject(object);
        return this;

    }

    public RequirementPieceBuilder<T> setRequirementCheckType(RequirementCheckType requirementCheckType) {

        requirementPiece.setRequirementCheckType(requirementCheckType);
        return this;

    }

    public RequirementPieceBuilder<T> applyActionIfChecked(@NotNull T parent, @NotNull Consumer<T> consumer) {

        requirementPiece.callActionIfChecked(parent, consumer);
        return this;

    }

    public RequirementPiece<T> build() {

        return requirementPiece;

    }
}