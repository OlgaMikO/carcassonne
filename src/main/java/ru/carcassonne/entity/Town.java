package ru.carcassonne.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Town {

    private Set<FieldTile> fieldTiles;

    private Integer shields;

    private boolean notFinished;

    private Integer points;

    public Town() {
        fieldTiles = new HashSet<>();
        shields = 0;
        notFinished = false;
        points = 0;
    }

    public void addTileToTown(FieldTile tile) {
        if (tile.getTile().isShield()) {
            shields++;
        }
        fieldTiles.add(tile);
    }

    public Town finalyzeTown() {
        if (notFinished) {
            points = fieldTiles.size();
        } else {
            points = fieldTiles.size() * 2 + shields * 2;
        }
        return this;
    }
}
