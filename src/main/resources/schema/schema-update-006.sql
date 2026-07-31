DO '
DECLARE
  newVersion     BIGINT := 6;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE IF NOT EXISTS dvsdtable
      (
          id BIGINT NOT NULL constraint dvsdtable_pkey primary key,
          tableUid VARCHAR(64),
          lastUpdated BIGINT,
          startDate BIGINT,
          wellNameLegal TEXT,
          wellField TEXT,
          wellCluster TEXT,
          wellCustomer TEXT,
          rigName TEXT,
          projectName TEXT,
          projectPhase INTEGER NOT NULL,
          content JSONB NULL
      );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

