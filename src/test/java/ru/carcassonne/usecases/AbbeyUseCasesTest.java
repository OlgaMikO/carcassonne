package ru.carcassonne.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.carcassonne.entity.Abbey;
import ru.carcassonne.entity.Field;
import ru.carcassonne.entity.FieldTile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AbbeyUseCasesTest {

    @InjectMocks
    AbbeyUseCases abbeyUseCases;

    private static ObjectMapper objectMapper;

    private Abbey initAbbey(FieldTile center, List<FieldTile> environment, Integer points) {
        Abbey abbey = new Abbey();
        abbey.setCenter(center);
        abbey.setEnvironment(environment);
        abbey.setPoints(points);
        return abbey;
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
    void should_return_abbeys() throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field4.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Abbey> expectedList = List.of(initAbbey(fieldTiles[0][2], List.of(fieldTiles[0][1], fieldTiles[1][1], fieldTiles[1][2]), 4),
                initAbbey(fieldTiles[1][0], List.of(fieldTiles[0][0], fieldTiles[0][1], fieldTiles[1][1], fieldTiles[2][0], fieldTiles[2][1]), 6),
                initAbbey(fieldTiles[2][2], List.of(fieldTiles[1][2], fieldTiles[1][1], fieldTiles[2][1]), 4));

        //WHEN
        List<Abbey> actualList = abbeyUseCases.calculateAbbeys(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);

    }

    @Test
    void should_return_abbeys_when_field_has_spot() throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field5.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Abbey> expectedList = List.of(initAbbey(fieldTiles[0][2], List.of(fieldTiles[0][1], fieldTiles[1][2]), 3),
                initAbbey(fieldTiles[1][0], List.of(fieldTiles[0][0], fieldTiles[0][1], fieldTiles[2][0], fieldTiles[2][1]), 5),
                initAbbey(fieldTiles[2][2], List.of(fieldTiles[1][2], fieldTiles[2][1]), 3));

        //WHEN
        List<Abbey> actualList = abbeyUseCases.calculateAbbeys(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);

    }
}
