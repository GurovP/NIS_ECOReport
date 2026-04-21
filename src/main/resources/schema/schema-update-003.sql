DO '
DECLARE
  newVersion     BIGINT := 3;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE IF NOT EXISTS mludailyreport
      (
          id BIGINT NOT NULL constraint mludailyreport_pkey primary key,
          reportUid VARCHAR(64),
          reportDate BIGINT NOT NULL,
          reportNumber TEXT,
          documentCode TEXT,
          comment TEXT NULL,
          approved BOOLEAN NOT NULL,
          lastApproveDate BIGINT NULL,
          lastApproveUser TEXT,
          fullDay BOOLEAN NOT NULL,
          wellNameLegal TEXT,
          wellField TEXT,
          wellCluster TEXT,
          wellCustomer TEXT,
          rigName TEXT,
          projectName TEXT,
          projectPhase INTEGER NOT NULL
      );

    CREATE TABLE IF NOT EXISTS mludailyreportdata
      (
          id BIGINT NOT NULL constraint mludailyreportdata_pkey primary key,
          dataType INTEGER NOT NULL,
          content JSONB NULL,
          report_id BIGINT NOT NULL
              constraint mludailyreportdata_mludailyreport_fkey
                  references mludailyreport
      );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

