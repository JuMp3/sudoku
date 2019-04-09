package it.jump3.sudoku.model;

public enum ItemType {

    BOOK, FOOD, MEDICAL_PRODUCT, GOOD;

    public static boolean isApplicableSalesTax(ItemType itemType) {

        switch (itemType) {
            case BOOK:
            case FOOD:
            case MEDICAL_PRODUCT:
                return false;
        }

        return true;
    }
}
