-- Step 1: Every branch gets a default zone
INSERT INTO branch_zones (zone_id, branch_id, name)
SELECT gen_random_uuid(), branch_id, 'Default'
FROM bistro_branch;

-- Step 2: Assign tables to their branch's default zone
UPDATE bistro_tables t
SET zone_id = z.zone_id
FROM branch_zones z
WHERE z.branch_id = t.branch
  AND z.name = 'Default'
  AND t.zone_id IS NULL;
