package ru.carcassonne.usecases;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.carcassonne.entity.FieldTile;
import ru.carcassonne.entity.Tile;
import ru.carcassonne.entity.enums.FieldElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class TileUseCasesTest {

    @InjectMocks
    TileUseCases tileUseCases;

    private static Tile tile;

    @BeforeAll
    public static void init() {
        tile = new Tile();
        tile.setId(1);
        tile.setTop(FieldElement.ROAD);
        tile.setBottom(FieldElement.TOWN);
        tile.setLeft(FieldElement.FIELD);
        tile.setRight(FieldElement.ROAD);
        tile.setCenter(FieldElement.ABBEY);
        tile.setShield(true);
    }

    @Test
    void should_not_rotate_when_angle_is_zero() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(0);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertEquals(tile, actualFieldTile.getTile());
    }

    @Test
    void should_rotate_when_angle_is_one() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(1);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertThat(actualFieldTile.getTile()).usingRecursiveComparison().
                ignoringFields("top", "right", "bottom", "left").isEqualTo(tile);
        assertEquals(tile.getTop(), actualFieldTile.getTile().getRight());
        assertEquals(tile.getRight(), actualFieldTile.getTile().getBottom());
        assertEquals(tile.getBottom(), actualFieldTile.getTile().getLeft());
        assertEquals(tile.getLeft(), actualFieldTile.getTile().getTop());
    }

    @Test
    void should_rotate_when_angle_is_two() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(2);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertThat(actualFieldTile.getTile()).usingRecursiveComparison().
                ignoringFields("top", "right", "bottom", "left").isEqualTo(tile);
        assertEquals(tile.getTop(), actualFieldTile.getTile().getBottom());
        assertEquals(tile.getRight(), actualFieldTile.getTile().getLeft());
        assertEquals(tile.getBottom(), actualFieldTile.getTile().getTop());
        assertEquals(tile.getLeft(), actualFieldTile.getTile().getRight());
    }

    @Test
    void should_rotate_when_angle_is_three() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(3);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertThat(actualFieldTile.getTile()).usingRecursiveComparison().
                ignoringFields("top", "right", "bottom", "left").isEqualTo(tile);
        assertEquals(tile.getTop(), actualFieldTile.getTile().getLeft());
        assertEquals(tile.getRight(), actualFieldTile.getTile().getTop());
        assertEquals(tile.getBottom(), actualFieldTile.getTile().getRight());
        assertEquals(tile.getLeft(), actualFieldTile.getTile().getBottom());
    }

    @Test
    void should_rotate_when_angle_less_then_zero() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(-1);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertThat(actualFieldTile.getTile()).usingRecursiveComparison().
                ignoringFields("top", "right", "bottom", "left").isEqualTo(tile);
        assertEquals(tile.getTop(), actualFieldTile.getTile().getLeft());
        assertEquals(tile.getRight(), actualFieldTile.getTile().getTop());
        assertEquals(tile.getBottom(), actualFieldTile.getTile().getRight());
        assertEquals(tile.getLeft(), actualFieldTile.getTile().getBottom());
    }

    @Test
    void should_rotate_when_angle_more_then_three() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(7);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertThat(actualFieldTile.getTile()).usingRecursiveComparison().
                ignoringFields("top", "right", "bottom", "left").isEqualTo(tile);
        assertEquals(tile.getTop(), actualFieldTile.getTile().getLeft());
        assertEquals(tile.getRight(), actualFieldTile.getTile().getTop());
        assertEquals(tile.getBottom(), actualFieldTile.getTile().getRight());
        assertEquals(tile.getLeft(), actualFieldTile.getTile().getBottom());
    }

    @Test
    void should_not_rotate_when_angle_is_null() {
        //GIVEN
        FieldTile fieldTile = new FieldTile();
        fieldTile.setTile(tile);
        fieldTile.setAngle(null);

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertEquals(1, actualFieldTile.getRow());
        assertEquals(1, actualFieldTile.getColumn());
        assertEquals(tile, actualFieldTile.getTile());
    }

    @Test
    void should_return_null_when_fieldTile_is_null() {
        //GIVEN
        FieldTile fieldTile = null;

        //WHEN
        FieldTile actualFieldTile = tileUseCases.rotateTile(fieldTile, 1, 1);

        //THEN
        assertNull(actualFieldTile);
    }
}
