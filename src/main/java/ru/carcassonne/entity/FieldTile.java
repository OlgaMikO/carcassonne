package ru.carcassonne.entity;

import lombok.Data;

@Data
public class FieldTile {

    private Tile tile;

    private Integer angle;

    private Integer row;

    private Integer column;
}
