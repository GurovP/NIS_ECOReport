DO '
DECLARE
  newVersion     BIGINT := 4;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE IF NOT EXISTS muddailyreport
      (
          id BIGINT NOT NULL constraint muddailyreport_pkey primary key,
          reportUid VARCHAR(64),
          reportDate BIGINT NOT NULL,
          reportNumber TEXT,
          documentCode TEXT,
          comment TEXT NULL,
          approved BOOLEAN NOT NULL,
          lastApproveDate BIGINT NULL,
          lastApproveUser TEXT,
          wellNameLegal TEXT,
          wellField TEXT,
          wellCluster TEXT,
          wellCustomer TEXT,
          rigName TEXT,
          projectName TEXT,
          projectPhase INTEGER NOT NULL
      );

    CREATE TABLE IF NOT EXISTS muddailyreportdata
      (
          id BIGINT NOT NULL constraint muddailyreportdata_pkey primary key,
          dataType INTEGER NOT NULL,
          content JSONB NULL,
          report_id BIGINT NOT NULL
              constraint muddailyreportdata_muddailyreport_fkey
                  references muddailyreport
      );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

