package ru.carcassonne.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldTileRequest {

    private Integer tileId;

    private Integer angle;
}
