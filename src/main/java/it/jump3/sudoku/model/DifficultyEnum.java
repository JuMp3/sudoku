package it.jump3.sudoku.model;

public enum DifficultyEnum {

    EASY(42, 49),
    MEDIUM(50, 54),
    HARD(54, 60);

    private int minHoles;
    private int maxHoles;

    DifficultyEnum(int minHoles, int maxHoles) {
        this.minHoles = minHoles;
        this.maxHoles = maxHoles;
    }

    public int getMinHoles() {
        return minHoles;
    }

    public int getMaxHoles() {
        return maxHoles;
    }
}
