package ru.carcassonne.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import ru.carcassonne.entity.enums.Direction;
import ru.carcassonne.entity.enums.FieldElement;

@Entity
@Getter
@Setter
@Table(name = "tiles")
public class Tile {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "top_element")
    @Enumerated(EnumType.STRING)
    private FieldElement top;

    @Column(name = "bottom_element")
    @Enumerated(EnumType.STRING)
    private FieldElement bottom;

    @Column(name = "left_element")
    @Enumerated(EnumType.STRING)
    private FieldElement left;

    @Column(name = "right_element")
    @Enumerated(EnumType.STRING)
    private FieldElement right;

    @Column(name = "center_element")
    @Enumerated(EnumType.STRING)
    private FieldElement center;

    @Column(name = "shield")
    private boolean shield;

    public FieldElement getElementByDirection(Direction direction) {
        switch (direction) {
            case UP -> {
                return top;
            }
            case RIGHT -> {
                return right;
            }
            case DOWN -> {
                return bottom;
            }
            case LEFT -> {
                return left;
            }
        }
        throw new RuntimeException("Invalid direction value");
    }

}
