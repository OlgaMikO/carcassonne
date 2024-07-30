package ru.carcassonne.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbbeyTest {

    private FieldTile initFieldTile(int row, int column) {
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(new Tile());
        fieldTile.setRow(row);
        fieldTile.setColumn(column);
        return fieldTile;
    }

    @Test
    void should_finalize_finished_abbey() {
        //GIVEN
        Abbey abbey = new Abbey();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                abbey.addTileToEnvironment(initFieldTile(i, j));
            }
        }
        abbey.setCenter(abbey.getEnvironment().get(4));
        abbey.getEnvironment().remove(4);

        //WHEN
        abbey.finalizeAbbey();

        //THEN
        assertEquals(9, abbey.getPoints());
    }

    @Test
    void should_finalize_not_finished_abbey() {
        //GIVEN
        Abbey abbey = new Abbey();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                abbey.addTileToEnvironment(initFieldTile(i, j));
            }
        }
        abbey.setCenter(abbey.getEnvironment().get(4));
        abbey.getEnvironment().remove(4);

        //WHEN
        abbey.finalizeAbbey();

        //THEN
        assertEquals(6, abbey.getPoints());
    }


}
