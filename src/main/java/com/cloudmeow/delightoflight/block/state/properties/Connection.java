package com.cloudmeow.delightoflight.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum Connection implements StringRepresentable {
    NONE("none"),
    UP("up"),
    DOWN("down"),
    BOTH("both");

    private final String name;

    Connection(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean hasUp() {
        return this == UP || this == BOTH;
    }

    public boolean hasDown() {
        return this == DOWN || this == BOTH;
    }

    public Connection withUp() {
        return switch (this) {
            case NONE -> UP;
            case DOWN -> BOTH;
            default -> this;
        };
    }

    public Connection withDown() {
        return switch (this) {
            case NONE -> DOWN;
            case UP -> BOTH;
            default -> this;
        };
    }

    public Connection withoutUp() {
        return switch (this) {
            case UP -> NONE;
            case BOTH -> DOWN;
            default -> this;
        };
    }

    public Connection withoutDown() {
        return switch (this) {
            case DOWN -> NONE;
            case BOTH -> UP;
            default -> this;
        };
    }
}
