--liquibase formatted sql
--changeset mikhailova:tiles-init-001

INSERT INTO tiles (top_element, bottom_element, left_element, right_element, center_element, shield)
VALUES
('FIELD', 'ROAD', 'FIELD', 'FIELD', 'ABBEY', false),
('FIELD', 'FIELD', 'FIELD', 'FIELD', 'ABBEY', false),
('TOWN', 'TOWN', 'TOWN', 'TOWN', 'TOWN', true),
('ROAD', 'ROAD', 'FIELD', 'TOWN', 'ROAD', false),
('ROAD', 'FIELD', 'FIELD', 'FIELD', 'FIELD', false),
('FIELD', 'FIELD', 'TOWN', 'TOWN', 'TOWN', true),
('TOWN', 'TOWN', 'FIELD', 'FIELD', 'TOWN', false),
('FIELD', 'FIELD', 'TOWN', 'TOWN', 'FIELD', false),
('FIELD', 'TOWN', 'FIELD', 'TOWN', 'FIELD', false),
('TOWN', 'ROAD', 'FIELD', 'ROAD', 'ROAD', false),
('ROAD', 'FIELD', 'ROAD', 'TOWN', 'ROAD', false),
('ROAD', 'ROAD', 'ROAD', 'TOWN', 'ROAD_END', false),
('TOWN', 'FIELD', 'TOWN', 'FIELD', 'FIELD', true),
('TOWN', 'FIELD', 'TOWN', 'FIELD', 'FIELD', false),
('TOWN', 'ROAD', 'TOWN', 'ROAD', 'ROAD', true),
('TOWN', 'ROAD', 'TOWN', 'ROAD', 'ROAD', false),
('TOWN', 'FIELD', 'TOWN', 'TOWN', 'TOWN', true),
('TOWN', 'FIELD', 'TOWN', 'TOWN', 'TOWN', false),
('TOWN', 'ROAD', 'TOWN', 'TOWN', 'ROAD_END', true),
('TOWN', 'ROAD', 'TOWN', 'TOWN', 'ROAD_END', false),
('ROAD', 'ROAD', 'FIELD', 'FIELD', 'ROAD', false),
('FIELD', 'ROAD', 'ROAD', 'FIELD', 'ROAD', false),
('FIELD', 'ROAD', 'ROAD', 'ROAD', 'ROAD_END', false),
('ROAD', 'ROAD', 'ROAD', 'ROAD', 'ROAD_END', false)