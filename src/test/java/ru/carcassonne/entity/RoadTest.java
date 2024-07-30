package ru.carcassonne.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoadTest {

    private FieldTile initFieldTile(int row, int column) {
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(new Tile());
        fieldTile.setRow(row);
        fieldTile.setColumn(column);
        return fieldTile;
    }

    @Test
    void should_finalize_road_with_start_end_fieldTiles() {
        //GIVEN
        Road road = new Road();
        road.setStart(initFieldTile(0, 0));
        road.setEnd(initFieldTile(2, 0));
        road.setFieldTiles(new HashSet<>(Set.of(initFieldTile(1, 0))));

        //WHEN
        road.finalizeRoad();

        //THEN
        assertEquals(3, road.getPoints());
    }

    @Test
    void should_finalize_road_with_start_end_without_fieldTiles() {
        //GIVEN
        Road road = new Road();
        road.setStart(initFieldTile(0, 0));
        road.setEnd(initFieldTile(2, 0));
        road.setFieldTiles(new HashSet<>());

        //WHEN
        road.finalizeRoad();

        //THEN
        assertEquals(2, road.getPoints());
    }

    @Test
    void should_finalize_road_where_start_equals_end() {
        //GIVEN
        Road road = new Road();
        road.setStart(initFieldTile(0, 0));
        road.setEnd(road.getStart());
        road.setFieldTiles(new HashSet<>(Set.of(initFieldTile(0, 1), initFieldTile(1, 1),
                initFieldTile(1, 0))));

        //WHEN
        road.finalizeRoad();

        //THEN
        assertEquals(4, road.getPoints());
    }
}
