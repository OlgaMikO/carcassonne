package ru.carcassonne.usecases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.carcassonne.entity.Field;
import ru.carcassonne.entity.FieldTile;
import ru.carcassonne.entity.Road;
import ru.carcassonne.entity.Tile;
import ru.carcassonne.entity.enums.Direction;
import ru.carcassonne.entity.enums.FieldElement;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RoadUseCases {

    private List<Road> roads;

    private Field field;

    private FieldTile[][] fieldTiles;

    private Integer rowCount;

    private Integer columnCount;

    private boolean[][] usedTiles;

    public List<Road> calculateRoads(Field field) {
        initVariables(field);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if(!usedTiles[i][j] && containsRoad(fieldTiles[i][j])) {
                    findRoad(fieldTiles[i][j]);
                }
            }
        }
        return roads;
    }

    private void initVariables(Field field) {
        this.field = field;
        fieldTiles = field.getFieldTiles();
        roads = new ArrayList<>();
        rowCount = field.getRowCount();
        columnCount = field.getColumnCount();
        usedTiles = new boolean[rowCount][columnCount];
    }

    private void findRoad(FieldTile nowTile) {
        Tile tile = nowTile.getTile();
        if (tile.getCenter().equals(FieldElement.ROAD_END) || tile.getCenter().equals(FieldElement.ABBEY)) {
            usedTiles[nowTile.getRow()][nowTile.getColumn()] = true;
            for (Direction direction : Direction.values()) {
                if (tile.getElementByDirection(direction).equals(FieldElement.ROAD)) {
                    Road road = new Road();
                    road.setStart(nowTile);
                    FieldTile nextTile = getNextRoadTileByDirection(nowTile, direction);
                    if (nextTile == null) {
                        roads.add(road.finalizeRoad());
                        log.info("Road " + road + " was added to list of roads.");
                    } else if (!usedTiles[nextTile.getRow()][nextTile.getColumn()]) {
                        road.setEnd(findRoadEnd(road, nextTile, direction));
                        roads.add(road.finalizeRoad());
                        log.info("Road " + road + " was added to list of roads.");
                        checkRoadEnding(road.getEnd());
                    }
                }
            }
        } else {
            Road road = new Road();
            Direction firstDirection = null;
            for (Direction direction : Direction.values()) {
                if (tile.getElementByDirection(direction).equals(FieldElement.ROAD)) {
                    FieldTile roadEnding = findRoadEnd(road, nowTile, firstDirection);
                    if (firstDirection == null) {
                        firstDirection = direction.getOppositeDirection();
                    }
                    if (road.getStart() == null) {
                        road.setStart(roadEnding);
                    } else {
                        road.setEnd(roadEnding);
                    }
                }
            }
            roads.add(road.finalizeRoad());
            log.info("Road " + road + " was added to list of roads.");
            checkRoadEnding(road.getStart());
            checkRoadEnding(road.getEnd());
        }
    }

    private boolean containsRoad(FieldTile fieldTile) {
        if (fieldTile == null) {
            return false;
        }
        Tile tile = fieldTile.getTile();
        return tile != null && (tile.getTop().equals(FieldElement.ROAD) || tile.getBottom().equals(FieldElement.ROAD)
                || tile.getLeft().equals(FieldElement.ROAD) || tile.getRight().equals(FieldElement.ROAD)
                || tile.getCenter().equals(FieldElement.ROAD_END) || tile.getCenter().equals(FieldElement.ROAD));
    }

    private FieldTile findRoadEnd(Road road, FieldTile nowTile, Direction direction) {
        Tile tile = nowTile.getTile();
        if (tile.getCenter().equals(FieldElement.ROAD_END) || tile.getCenter().equals(FieldElement.ABBEY)) {
            return nowTile;
        } else {
            int i = nowTile.getRow();
            int j = nowTile.getColumn();
            usedTiles[i][j] = true;
            Direction nextDirection = null;
            for (Direction d: Direction.values()) {
                if ((direction == null || !d.equals(direction.getOppositeDirection()))
                        && tile.getElementByDirection(d).equals(FieldElement.ROAD)) {
                    nextDirection = d;
                    break;
                }
            }
            FieldTile nextFieldTile = getNextRoadTileByDirection(nowTile, nextDirection);
            if (nextFieldTile != null) {
                road.addTileToRoad(nowTile);
                return findRoadEnd(road, nextFieldTile, nextDirection);
            }
            return nowTile;
        }
    }

    private FieldTile getNextRoadTileByDirection(FieldTile nowTile, Direction direction) {
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
        if (!fieldTiles[i][j].getTile().getElementByDirection(direction.getOppositeDirection()).equals(FieldElement.ROAD)) {
            return null;
        }
        return fieldTiles[i][j];
    }

    private void checkRoadEnding(FieldTile fieldTile) {
        if (!usedTiles[fieldTile.getRow()][fieldTile.getColumn()]
                && fieldTile.getTile().getCenter().equals(FieldElement.ROAD_END)) {
            findRoad(fieldTile);
        }
    }


}
