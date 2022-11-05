package net.juicy.api.utils.requirement;

import lombok.Value;

import java.util.List;

@Value
public class Requirement<T> {

    List<RequirementPiece<T>> pieces;

    public void addRequirementPiece(RequirementPiece<T> requirementPiece) {

        pieces.add(requirementPiece);

    }

    public void removeRequirementPiece(RequirementPiece<T> requirementPiece) {

        pieces.remove(requirementPiece);

    }

    public boolean check(T parent) {

        return pieces.size() == pieces.stream().filter(piece -> piece.check(parent)).count();

    }
}