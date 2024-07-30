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
import ru.carcassonne.entity.Town;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TownUseCasesTest {

    @InjectMocks
    TownUseCases townUseCases;

    private static ObjectMapper objectMapper;

    private Town initTown(Set<FieldTile> fieldTiles, Integer shields, boolean notFinished, Integer points) {
        Town town = new Town();
        town.setFieldTiles(fieldTiles);
        town.setShields(shields);
        town.setNotFinished(notFinished);
        town.setPoints(points);
        return town;
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
    void test() throws FileNotFoundException, JsonProcessingException {
        //GIVEN
        Field field = initField("src/test/resources/jsons/fields/field1.json");
        FieldTile[][] fieldTiles = field.getFieldTiles();
        List<Town> expectedList = List.of(initTown(Set.of(fieldTiles[0][0], fieldTiles[1][0]), 2, true, 2),
                initTown(Set.of(fieldTiles[0][2]), 0, true, 1),
                initTown(Set.of(fieldTiles[2][0]), 0, true, 1),
                initTown(Set.of(fieldTiles[2][1], fieldTiles[2][2]), 0, true, 2));

        //WHEN
        List<Town> actualList = townUseCases.calculateTowns(field);

        //THEN
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }
}
