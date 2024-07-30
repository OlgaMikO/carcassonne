package ru.carcassonne.entity.dto;

import lombok.Data;
import ru.carcassonne.entity.Abbey;
import ru.carcassonne.entity.Road;
import ru.carcassonne.entity.Town;

import java.util.List;

@Data
public class ReportDto {

    private List<Road> roads;

    private Integer countOfRoads;

    private Integer roadPoints;

    private List<Abbey> abbeys;

    private Integer countOfAbbeys;

    private Integer abbeyPoints;

    private List<Town> towns;

    private Integer countOfTowns;

    private Integer townPoints;
}
