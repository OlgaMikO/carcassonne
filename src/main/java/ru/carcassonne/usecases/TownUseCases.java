package ru.carcassonne.usecases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.carcassonne.entity.*;
import ru.carcassonne.entity.enums.Direction;
import ru.carcassonne.entity.enums.FieldElement;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TownUseCases {

    private List<Town> towns;

    private Field field;

    private FieldTile[][] fieldTiles;

    private Integer rowCount;

    private Integer columnCount;

    private boolean[][] usedTiles;

    public List<Town> calculateTowns(Field field) {
        initVariables(field);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (!usedTiles[i][j] && containsTown(fieldTiles[i][j])) {
                    findTown(fieldTiles[i][j]);
                }
            }
        }
        return towns;
    }

    private void initVariables(Field field) {
        this.field = field;
        fieldTiles = field.getFieldTiles();
        towns = new ArrayList<>();
        rowCount = field.getRowCount();
        columnCount = field.getColumnCount();
        usedTiles = new boolean[rowCount][columnCount];
    }

    private void findTown(FieldTile nowTile) {
        boolean result = true;
        Town town = new Town();
        town.addTileToTown(nowTile);
        usedTiles[nowTile.getRow()][nowTile.getColumn()] = true;
        for (Direction direction : Direction.values()) {
            if (nowTile.getTile().getElementByDirection(direction).equals(FieldElement.TOWN)) {
                result &= findTownEnd(town, getNextTownTileByDirection(nowTile, direction), direction);
            }
        }
        if (!result) {
            town.setNotFinished(true);
        }
        towns.add(town.finalyzeTown());
        log.info("Town " + town.toString() + " was added to list of towns.");
    }

    private boolean findTownEnd(Town town, FieldTile nowTile, Direction direction) {
        if (nowTile == null) {
            return false;
        }
        Tile tile = nowTile.getTile();
        int i = nowTile.getRow();
        int j = nowTile.getColumn();
        if (usedTiles[i][j]) {
            return true;
        }
        usedTiles[i][j] = true;
        boolean result = true;
        for (Direction d : Direction.values()) {
            if (nowTile.getTile().getElementByDirection(d).equals(FieldElement.TOWN)
                    && !d.equals(direction.getOppositeDirection())) {
                town.addTileToTown(nowTile);
                result &= findTownEnd(town, getNextTownTileByDirection(nowTile, d), d);
            }
        }
        return result;
    }

    private boolean containsTown(FieldTile fieldTile) {
        if (fieldTile == null) {
            return false;
        }
        Tile tile = fieldTile.getTile();
        return tile.getCenter().equals(FieldElement.TOWN) || tile.getBottom().equals(FieldElement.TOWN)
                || tile.getTop().equals(FieldElement.TOWN) || tile.getLeft().equals(FieldElement.TOWN)
                || tile.getRight().equals(FieldElement.TOWN);
    }

    private FieldTile getNextTownTileByDirection(FieldTile nowTile, Direction direction) {
        if (direction == null) {
            return null;
        }
        int i = nowTile.getRow();
        int j = nowTile.getColumn();
        switch (direction) {
            case UP -> i--;
            case RIGHT -> j++;
            case DOWN -> i++;
            case LEFT -> j--;
        }
        if (i < 0 || i >= rowCount || j < 0 || j >= columnCount || fieldTiles[i][j] == null) {
            return null;
        }
        if (!fieldTiles[i][j].getTile().getElementByDirection(direction.getOppositeDirection()).equals(FieldElement.TOWN)) {
            return null;
        }
        return fieldTiles[i][j];
    }

}
