DO '
DECLARE
  newVersion     BIGINT := 1;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE if not exists ecoreportingservicesettings
    (
      id bigint NOT NULL constraint ecoreportingservicesettings_pkey primary key,
      apiUrl text NULL,
      apiUser text NULL,
      apiPass text NULL,
      timeout INTEGER NOT NULL default 30
    );

    CREATE TABLE if not exists ecoreportingsynclog
    (
      id bigint NOT NULL constraint ecoreportingsynclog_pkey primary key,
      timeSync text NULL,
      reportType text NULL,
      readFromApiCount INTEGER,
      addedCount INTEGER,
      updatedCount INTEGER,
      deletedCount INTEGER
    );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

