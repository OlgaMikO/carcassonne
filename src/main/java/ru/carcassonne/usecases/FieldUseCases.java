package ru.carcassonne.usecases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.carcassonne.entity.*;
import ru.carcassonne.entity.dto.FieldRequest;
import ru.carcassonne.entity.dto.FieldTileRequest;
import ru.carcassonne.entity.dto.ReportDto;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FieldUseCases {

    private final TileUseCases tileUseCases;

    private final RoadUseCases roadUseCases;

    private final TownUseCases townUseCases;

    private final AbbeyUseCases abbeyUseCases;

    private Field rotateTiles(Field field) {
        FieldTile[][] tiles = new FieldTile[field.getRowCount()][field.getColumnCount()];
        FieldTile[][] oldTiles = field.getFieldTiles();
        int rowCount = field.getRowCount();
        int columnCount = field.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tiles[i][j] = tileUseCases.rotateTile(oldTiles[i][j], i, j);
            }
        }
        field.setFieldTiles(tiles);
        return field;
    }

    private Field convertToField(FieldRequest fieldRequest) {
        Field field = new Field();
        int rowCount = fieldRequest.getRowCount();
        int columnCount = fieldRequest.getColumnCount();
        field.setRowCount(rowCount);
        field.setColumnCount(columnCount);
        FieldTile[][] fieldTiles = new FieldTile[fieldRequest.getRowCount()][fieldRequest.getColumnCount()];
        FieldTileRequest[][] fieldTileRequests = fieldRequest.getTiles();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                FieldTileRequest fieldTileRequest = fieldTileRequests[i][j];
                if (fieldTileRequest == null) {
                    fieldTiles[i][j] = null;
                } else {
                    FieldTile fieldTile = new FieldTile();
                    fieldTile.setAngle(fieldTileRequest.getAngle());
                    fieldTile.setTile(tileUseCases.findById(fieldTileRequest.getTileId()));
                    fieldTiles[i][j] = fieldTile;
                }
            }
        }
        field.setFieldTiles(fieldTiles);
        return field;
    }

    public ReportDto calculate(FieldRequest fieldRequest) {
        Field field = rotateTiles(convertToField(fieldRequest));
        List<Road> roads = roadUseCases.calculateRoads(field);
        log.info("The road search is over.");
        List<Abbey> abbeys = abbeyUseCases.calculateAbbeys(field);
        log.info("The abbey search is over.");
        List<Town> towns = townUseCases.calculateTowns(field);
        log.info("The town search is over.");
        return convertToReportDto(roads, abbeys, towns);
    }

    private ReportDto convertToReportDto(List<Road> roads, List<Abbey> abbeys, List<Town> towns) {
        ReportDto reportDto = new ReportDto();
        int points = 0;
        reportDto.setRoads(roads);
        reportDto.setCountOfRoads(roads.size());
        for(Road road : roads) {
            points += road.getPoints();
        }
        reportDto.setRoadPoints(points);
        points = 0;
        reportDto.setAbbeys(abbeys);
        reportDto.setCountOfAbbeys(abbeys.size());
        for(Abbey abbey : abbeys) {
            points += abbey.getPoints();
        }
        reportDto.setAbbeyPoints(points);
        points = 0;
        reportDto.setTowns(towns);
        reportDto.setCountOfTowns(towns.size());
        for(Town town : towns) {
            points += town.getPoints();
        }
        reportDto.setTownPoints(points);
        return reportDto;
    }

}
