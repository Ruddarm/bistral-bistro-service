ALTER TABLE bistro_tables
ADD COLUMN zone_id UUID;

ALTER TABLE bistro_tables
ADD CONSTRAINT fk_table_branch_zones FOREIGN KEY (zone_id) REFERENCES branch_zones(zone_id);
