package net.juicy.api.utils.util.builders.requirement;

import net.juicy.api.utils.requirement.Requirement;
import net.juicy.api.utils.requirement.RequirementPiece;
import net.juicy.api.utils.util.builders.IBuilder;

import java.util.ArrayList;

public class RequirementBuilder<T> implements IBuilder<Requirement<T>> {

    private final Requirement<T> requirement = new Requirement<>(new ArrayList<>());

    public RequirementBuilder<T> addRequirementPiece(RequirementPiece<T> requirementPiece) {

        requirement.addRequirementPiece(requirementPiece);
        return this;

    }

    public RequirementBuilder<T> addRequirementPiece(RequirementPieceBuilder<T> requirementPieceBuilder) {

        requirement.addRequirementPiece(requirementPieceBuilder.build());
        return this;

    }

    public RequirementBuilder<T> removeRequirementPiece(RequirementPiece<T> requirementPiece) {

        requirement.removeRequirementPiece(requirementPiece);
        return this;

    }

    public Requirement<T> build() {

        return requirement;

    }
}