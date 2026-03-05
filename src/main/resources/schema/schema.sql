DO '
BEGIN

  create sequence IF NOT EXISTS hibernate_sequence start with 1;

  if NOT EXISTS (SELECT 1
                  FROM information_schema.tables
                  WHERE table_schema = ''public''
                  AND table_name = ''schema_version'') then

    CREATE TABLE public.schema_version
    (
      version bigint
    );

    INSERT INTO public.schema_version(version) VALUES(0);

  END IF;

END ';