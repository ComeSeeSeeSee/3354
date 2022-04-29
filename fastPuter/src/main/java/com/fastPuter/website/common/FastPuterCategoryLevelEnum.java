package com.fastPuter.website.common;

public enum FastPuterCategoryLevelEnum {

    DEFAULT(0, "ERROR"),
    LEVEL_ONE(1, "Level one"),
    LEVEL_TWO(2, "Level two"),
    LEVEL_THREE(3, "Level three");

    private int level;

    private String name;

    FastPuterCategoryLevelEnum(int level, String name) {
        this.level = level;
        this.name = name;
    }


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
