CREATE TABLE branch_zones (
    zone_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    branch_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_zone_branch FOREIGN KEY (branch_id) REFERENCES bistro_branch(branch_id)
);
