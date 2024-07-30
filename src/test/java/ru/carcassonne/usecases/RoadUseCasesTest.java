package ru.carcassonne.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.carcassonne.entity.Field;
import ru.carcassonne.entity.FieldTile;
import ru.carcassonne.entity.Road;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoadUseCasesTest {

    @InjectMocks
    RoadUseCases roadUseCases;

    private static ObjectMapper objectMapper;

    private Road initRoad(FieldTile start, FieldTile end, Set<FieldTile> tiles, Integer points) {
        Road road = new Road();
        road.setStart(start);
        road.setEnd(end);
        road.setFieldTiles(tiles);
        road.setPoints(points);
        return road;
    }

    private Field initField(String path) throws JsonProcessingException, FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String json = reader.lines().collect(Collectors.joining());
        return objectMapper.readValue(json, Field.class);
    }

    @BeforeAll
    public static void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_return_roads_when_field_contains_roads_with_start_and_end() throws JsonProcessingException, FileNotFoundException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field1.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Road> expectedList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[1][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[1][1], Set.of(fieldTiles[2][1], fieldTiles[2][0], fieldTiles[1][0]), 4),
                initRoad(fieldTiles[1][1], fieldTiles[0][1], Set.of(fieldTiles[1][2], fieldTiles[0][2]), 4));

        //WHEN
        List<Road> actualList = roadUseCases.calculateRoads(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }

    @Test
    void should_return_roads_when_field_contains_roads_without_end() throws JsonProcessingException, FileNotFoundException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field2.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Road> expectedList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[1][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[1][0], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[2][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[0][1], Set.of(fieldTiles[1][2], fieldTiles[0][2]), 4));

        //WHEN
        List<Road> actualList = roadUseCases.calculateRoads(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }

    @Test
    void should_return_roads_when_field_contains_roads_without_start_and_end() throws JsonProcessingException, FileNotFoundException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field3.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Road> expectedList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[0][1], Collections.emptySet(), 1),
                initRoad(fieldTiles[0][1], fieldTiles[1][2], Set.of(fieldTiles[0][2]), 3),
                initRoad(fieldTiles[2][1], fieldTiles[1][0], Set.of(fieldTiles[2][0]), 3));

        //WHEN
        List<Road> actualList = roadUseCases.calculateRoads(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }

    @Test
    void should_return_roads_when_field_contains_abbey_with_road() throws JsonProcessingException, FileNotFoundException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field6.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Road> expectedList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[0][1], fieldTiles[1][1], Collections.emptySet(), 2),
                initRoad(fieldTiles[1][1], fieldTiles[1][1], Set.of(fieldTiles[2][1], fieldTiles[2][0], fieldTiles[1][0]), 4),
                initRoad(fieldTiles[1][1], fieldTiles[0][1], Set.of(fieldTiles[1][2], fieldTiles[0][2]), 4));

        //WHEN
        List<Road> actualList = roadUseCases.calculateRoads(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }

    @Test
    void should_return_roads_when_field_contains_abbey_without_road() throws JsonProcessingException, FileNotFoundException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field4.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Road> expectedList = List.of(initRoad(fieldTiles[0][0], fieldTiles[0][0], Collections.emptySet(), 1),
                initRoad(fieldTiles[0][1], fieldTiles[2][0], Set.of(fieldTiles[2][1], fieldTiles[1][1]), 4),
                initRoad(fieldTiles[0][2], fieldTiles[0][2], Collections.emptySet(), 1),
                initRoad(fieldTiles[2][0], fieldTiles[1][0], Collections.emptySet(), 2),
                initRoad(fieldTiles[2][0], fieldTiles[2][0], Collections.emptySet(), 1));

        //WHEN
        List<Road> actualList = roadUseCases.calculateRoads(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }

}
