package ru.carcassonne.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Field {

    private FieldTile[][] fieldTiles;

    private Integer rowCount;

    private Integer columnCount;
}
