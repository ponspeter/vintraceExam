package com.vintrace.exam.predicate;

import com.vintrace.exam.model.Wine;

import java.util.function.Predicate;

public class WinePredicate {

    public static Predicate<Wine> byLotCode(String lotCode) {
        return p -> p.getLotCode().equals(lotCode);
    }

    public static Predicate<Wine> byVolume(double volume) {
        return p -> p.getVolume() == volume;
    }

    public static Predicate<Wine> byDescription(String description) {
        return p -> p.getDescription().equals(description);
    }

    public static Predicate<Wine> byTankCode(String tankCode) {
        return p -> p.getTankCode().equals(tankCode);
    }

    public static Predicate<Wine> byOwner(String owner) {
        return p -> p.getOwnerName().equals(owner);
    }

    public static Predicate<Wine> byProductState(String productState) {
        return p -> p.getProductState().equals(productState);
    }
}
