

ALTER TABLE public.menus
ADD COLUMN created_at TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN created_by UUID;

UPDATE TABLE public.menus
set created_at = CURRENT_TIMESTAMP where created_at IS NULL;

UPDATE TABLE public.menus
set created_by = '00000000-0000-0000-0000-000000000000';

ALTER TABLE public.menus
ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE public.menus
ALTER COLUMN created_by Set NOT NULL;