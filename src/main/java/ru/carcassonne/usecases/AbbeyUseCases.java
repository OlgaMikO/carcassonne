package ru.carcassonne.usecases;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.carcassonne.entity.Abbey;
import ru.carcassonne.entity.Field;
import ru.carcassonne.entity.FieldTile;
import ru.carcassonne.entity.enums.FieldElement;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AbbeyUseCases {

    private List<Abbey> abbeys;

    private Field field;

    private FieldTile[][] fieldTiles;

    private Integer rowCount;

    private Integer columnCount;

    public List<Abbey> calculateAbbeys(Field field) {
        initVariables(field);
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if(containsAbbey(fieldTiles[i][j])) {
                    Abbey abbey = calculateAbbey(fieldTiles[i][j]);
                    abbeys.add(abbey);
                    log.info("Abbey " + abbey.toString() + " was added to list of abbeys.");
                }
            }
        }
        return abbeys;
    }

    private void initVariables(Field field) {
        this.field = field;
        fieldTiles = field.getFieldTiles();
        abbeys = new ArrayList<>();
        rowCount = field.getRowCount();
        columnCount = field.getColumnCount();
    }

    private boolean containsAbbey(FieldTile fieldTile) {
        if (fieldTile == null) {
            return false;
        }
        return fieldTile.getTile() != null && fieldTile.getTile().getCenter().equals(FieldElement.ABBEY);
    }

    private Abbey calculateAbbey(FieldTile fieldTile) {
        Abbey abbey = new Abbey();
        abbey.setCenter(fieldTile);
        int row = fieldTile.getRow();
        int column = fieldTile.getColumn();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newColumn = column + j;
                if (newRow >= 0 && newRow < rowCount && newColumn >= 0 && newColumn < columnCount && fieldTiles[newRow][newColumn] != null) {
                    abbey.addTileToEnvironment(fieldTiles[row + i][column + j]);
                }
            }
        }
        return abbey.finalizeAbbey();
    }

}
