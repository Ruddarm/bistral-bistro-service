
--Adding not null constraint on menu_name
ALTER TABLE public.menus
ALTER COLUMN menu_name SET  NOT NULL;



--Adding bistro foreign key references
ALTER TABLE public.menus
ADD CONSTRAINT fk_menu_bistro FOREIGN KEY (bistro_id)
REFERENCES public.bistros(bistro_id)
ON DELETE CASCADE;

--Adding audit column for menus
ALTER TABLE public.menus
ADD COLUMN created_at TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN created_by UUID;


--Adding data in existing row
UPDATE  public.menus
set created_at = CURRENT_TIMESTAMP where created_at IS NULL;

UPDATE  public.menus
set created_by = '00000000-0000-0000-0000-000000000000';

ALTER TABLE public.menus
ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE public.menus
ALTER COLUMN created_by Set NOT NULL;

ALTER TABLE public.menus
ADD CONSTRAINT uq_menu_name_bistro
UNIQUE (menu_name, bistro_id);




