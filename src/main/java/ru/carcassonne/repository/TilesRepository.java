package ru.carcassonne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.carcassonne.entity.Tile;

@Repository
public interface TilesRepository extends JpaRepository<Tile, Integer> {
}
