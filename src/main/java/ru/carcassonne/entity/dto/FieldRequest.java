package ru.carcassonne.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FieldRequest {

    @JsonProperty("tiles")
    private FieldTileRequest[][] tiles;

    @JsonProperty("rowCount")
    private Integer rowCount;

    @JsonProperty("columnCount")
    private Integer columnCount;
}
