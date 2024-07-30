package ru.carcassonne.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Road {

    private Set<FieldTile> fieldTiles;

    private FieldTile start;

    private FieldTile end;

    private Integer points;

    public Road() {
        fieldTiles = new HashSet<>();
    }

    public void addTileToRoad(FieldTile tile) {
        fieldTiles.add(tile);
    }

    public Road finalizeRoad() {
        if (start != null) {
            fieldTiles.add(start);
        }
        if (end != null) {
            fieldTiles.add(end);
        } else {
            end = start;
        }
        points = fieldTiles.size();
        fieldTiles.remove(start);
        fieldTiles.remove(end);
        return this;
    }

}
