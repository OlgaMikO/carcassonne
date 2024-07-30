package ru.carcassonne.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.carcassonne.entity.FieldTile;
import ru.carcassonne.entity.Tile;
import ru.carcassonne.entity.enums.FieldElement;
import ru.carcassonne.repository.TilesRepository;

@Component
@RequiredArgsConstructor
public class TileUseCases {

    private final TilesRepository tilesRepository;

    public FieldTile rotateTile(FieldTile fieldTile, int i, int j) {
        if (fieldTile == null || fieldTile.getTile() == null) {
            return null;
        }
        if (fieldTile.getAngle() == null || (fieldTile.getAngle() + 4) % 4 == 0) {
            fieldTile.setRow(i);
            fieldTile.setColumn(j);
            return fieldTile;
        }
        Tile tile = new Tile();
        Tile oldTile = fieldTile.getTile();
        tile.setId(oldTile.getId());
        tile.setCenter(oldTile.getCenter());
        tile.setShield(oldTile.isShield());
        FieldElement top = oldTile.getTop();
        switch ((fieldTile.getAngle() + 4) % 4) {
            case 1:
                tile.setTop(oldTile.getLeft());
                tile.setLeft(oldTile.getBottom());
                tile.setBottom(oldTile.getRight());
                tile.setRight(top);
                break;
            case 2:
                FieldElement left = oldTile.getLeft();
                tile.setTop(oldTile.getBottom());
                tile.setLeft(oldTile.getRight());
                tile.setRight(left);
                tile.setBottom(top);
                break;
            case 3:
                tile.setTop(oldTile.getRight());
                tile.setRight(oldTile.getBottom());
                tile.setBottom(oldTile.getLeft());
                tile.setLeft(top);
                break;
            default:
                throw new RuntimeException("Invalid angle value");
        }
        FieldTile result = new FieldTile();
        result.setTile(tile);
        result.setRow(i);
        result.setColumn(j);
        result.setAngle(0);
        return result;
    }

    public Tile findById(Integer id) {
        return tilesRepository.findById(id).orElseGet(null);
    }

}
