package ru.carcassonne.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Abbey {

    private FieldTile center;

    private List<FieldTile> environment;

    private Integer points;

    public Abbey() {
        environment = new ArrayList<>();
    }

    public void addTileToEnvironment(FieldTile fieldTile) {
        environment.add(fieldTile);
    }

    public Abbey finalizeAbbey() {
        environment.remove(center);
        points = environment.size() + 1;
        return this;
    }

}
