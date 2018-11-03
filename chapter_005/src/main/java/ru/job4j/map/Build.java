package ru.job4j.map;

import java.util.Objects;

public class Build {
    private String name;
    private int levels;
    private float levelHeight;
    private boolean hasParking;

    Build(String name, int levels, float levelHeight, boolean hasParking) {
        this.name = name;
        this.levels = levels;
        this.levelHeight = levelHeight;
        this.hasParking = hasParking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Build build = (Build) o;
        return levels == build.levels
                && Float.compare(build.levelHeight, levelHeight) == 0
                && hasParking == build.hasParking
                && Objects.equals(name, build.name);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + name.hashCode();
        result = 31 * result + levels;
        result = 31 * result + Float.floatToIntBits(levelHeight);
        result = 31 * result + (hasParking ? 1 : 0);
        return result;
    }
}
