package ru.carcassonne.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.carcassonne.entity.*;
import ru.carcassonne.entity.dto.FieldRequest;
import ru.carcassonne.entity.dto.ReportDto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;

@SpringBootTest
class FieldUseCasesTest {

    @Autowired
    FieldUseCases fieldUseCases;

    @Autowired
    ObjectMapper objectMapper;

    @SpyBean
    RoadUseCases roadUseCases;

    @SpyBean
    AbbeyUseCases abbeyUseCases;

    @SpyBean
    TownUseCases townUseCases;

    private FieldRequest initFieldRequest(String path) throws JsonProcessingException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String json = reader.lines().collect(Collectors.joining());
        return objectMapper.readValue(json, FieldRequest.class);
    }

    private Field initField(String path) throws JsonProcessingException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String json = reader.lines().collect(Collectors.joining());
        return objectMapper.readValue(json, Field.class);
    }

    private Road initRoad(FieldTile start, FieldTile end, Set<FieldTile> tiles, Integer points) {
        Road road = new Road();
        road.setStart(start);
        road.setEnd(end);
        road.setFieldTiles(tiles);
        road.setPoints(points);
        return road;
    }

    private Abbey initAbbey(FieldTile center, List<FieldTile> environment, Integer points) {
        Abbey abbey = new Abbey();
        abbey.setCenter(center);
        abbey.setEnvironment(environment);
        abbey.setPoints(points);
        return abbey;
    }

    private Town initTown(Set<FieldTile> fieldTiles, Integer shields, boolean notFinished, Integer points) {
        Town town = new Town();
        town.setFieldTiles(fieldTiles);
        town.setShields(shields);
        town.setNotFinished(notFinished);
        town.setPoints(points);
        return town;
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

    @Test
    void should_convert_and_rotate_fieldRequest()
            throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        FieldRequest fieldRequest =
                initFieldRequest("src/test/resources/jsons/fieldRequests/fieldRequest1.json");
        Field expectedField =
                initField("src/test/resources/jsons/fields/field1.json");
        FieldTile[][] fieldTiles = expectedField.getFieldTiles();
        List<Road> expectedRoadsList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1],
                        Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[1][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[1][1], Set.of(fieldTiles[2][1],
                        fieldTiles[2][0], fieldTiles[1][0]), 4),
                initRoad(fieldTiles[1][1], fieldTiles[0][1], Set.of(fieldTiles[1][2],
                        fieldTiles[0][2]), 4));
        List<Town> expectedTowns = List.of(initTown(Set.of(fieldTiles[0][0], fieldTiles[1][0]), 2, true, 2),
                initTown(Set.of(fieldTiles[0][2]), 0, true, 1),
                initTown(Set.of(fieldTiles[2][0]), 0, true, 1),
                initTown(Set.of(fieldTiles[2][1], fieldTiles[2][2]), 0, true, 2));
        ReportDto expectedReportDto = convertToReportDto(expectedRoadsList, Collections.emptyList(), expectedTowns);

        //THEN
        ReportDto actualReportDto = fieldUseCases.calculate(fieldRequest);

        //WHEN
        verify(roadUseCases).calculateRoads(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(abbeyUseCases).calculateAbbeys(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(townUseCases).calculateTowns(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        assertThat(actualReportDto).usingRecursiveComparison().ignoringCollectionOrder()
                .isEqualTo(expectedReportDto);
    }

    @Test
    void should_convert_and_rotate_fieldRequest_with_spot() throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        FieldRequest fieldRequest = initFieldRequest("src/test/resources/jsons/fieldRequests/fieldRequest2.json");
        Field expectedField = initField("src/test/resources/jsons/fields/field2.json");
        FieldTile[][] fieldTiles = expectedField.getFieldTiles();
        List<Road> expectedRoadsList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[1][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[1][0], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[2][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[0][1], Set.of(fieldTiles[1][2], fieldTiles[0][2]), 4));
        List<Town> expectedTowns = List.of(initTown(Set.of(fieldTiles[0][0], fieldTiles[1][0]), 2, true, 2),
                initTown(Set.of(fieldTiles[0][2]), 0, true, 1),
                initTown(Set.of(fieldTiles[2][1], fieldTiles[2][2]), 0, true, 2));
        ReportDto expectedReportDto = convertToReportDto(expectedRoadsList, Collections.emptyList(), expectedTowns);

        //THEN
        ReportDto actualReportDto = fieldUseCases.calculate(fieldRequest);

        //WHEN
        verify(roadUseCases).calculateRoads(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(abbeyUseCases).calculateAbbeys(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(townUseCases).calculateTowns(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        assertThat(actualReportDto).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedReportDto);
    }

    @Test
    void should_convert_and_rotate_fieldRequest_with_roads_and_abbeys() throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        FieldRequest fieldRequest = initFieldRequest("src/test/resources/jsons/fieldRequests/fieldRequest4.json");
        Field expectedField = initField("src/test/resources/jsons/fields/field4.json");
        FieldTile[][] fieldTiles = expectedField.getFieldTiles();
        List<Road> expectedRoadsList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][0], Collections.emptySet(), 1),
                initRoad(fieldTiles[0][1], fieldTiles[2][0], Set.of(fieldTiles[2][1], fieldTiles[1][1]), 4),
                initRoad(fieldTiles[0][2], fieldTiles[0][2], Collections.emptySet(), 1),
                initRoad(fieldTiles[2][0], fieldTiles[1][0], Collections.emptySet(), 2),
                initRoad(fieldTiles[2][0], fieldTiles[2][0], Collections.emptySet(), 1));
        List<Abbey> expectedAbbeysList = List.of(initAbbey(fieldTiles[0][2], List.of(fieldTiles[0][1], fieldTiles[1][1], fieldTiles[1][2]), 4),
                initAbbey(fieldTiles[1][0], List.of(fieldTiles[0][0], fieldTiles[0][1], fieldTiles[1][1], fieldTiles[2][0], fieldTiles[2][1]), 6),
                initAbbey(fieldTiles[2][2], List.of(fieldTiles[1][2], fieldTiles[1][1], fieldTiles[2][1]), 4));
        List<Town> expectedTowns = List.of(initTown(Set.of(fieldTiles[1][1], fieldTiles[1][2]), 0, true, 2),
                initTown(Set.of(fieldTiles[2][0]), 0, true, 1));
        ReportDto expectedReportDto = convertToReportDto(expectedRoadsList, expectedAbbeysList, expectedTowns);

        //THEN
        ReportDto actualReportDto = fieldUseCases.calculate(fieldRequest);

        //WHEN
        verify(roadUseCases).calculateRoads(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(abbeyUseCases).calculateAbbeys(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        verify(townUseCases).calculateTowns(assertArg(arg ->
                assertThat(arg).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedField)));
        assertThat(actualReportDto).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedReportDto);
    }
}
