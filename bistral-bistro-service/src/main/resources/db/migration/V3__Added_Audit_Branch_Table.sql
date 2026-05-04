
ALTER TABLE public.bistro_branches
ADD COLUMN updated_by UUID ,
ADD COLUMN updated_at TIMESTAMP,
ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN created_by UUID;


UPDATE public.bistro_branches
set created_at = CURRENT_TIMESTAMP,
created_by  = '00000000-0000-0000-0000-000000000000';

ALTER TABLE public.bistro_branches
ALTER COLUMN created_by SET NOT NULL;

ALTER TABLE public.bistro_branches
ALTER COLUMN created_at SET NOT NULL;