DO '
DECLARE
  newVersion     BIGINT := 2;
  currentVersion BIGINT;
BEGIN

  currentVersion := (SELECT version
                     FROM schema_version
                     LIMIT 1);

  IF (currentVersion < newVersion)
  THEN

    CREATE TABLE IF NOT EXISTS ddrdailyreport
      (
          id BIGINT NOT NULL constraint ddrdailyreport_pkey primary key,
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
          projectPhase INTEGER NOT NULL,
          ddrdailytimedistributiondata_id BIGINT
      );

    CREATE TABLE IF NOT EXISTS ddrdailyreportdata
      (
          id BIGINT NOT NULL constraint ddrdailyreportdata_pkey primary key,
          dataType INTEGER NOT NULL,
          content JSONB NULL,
          report_id BIGINT NOT NULL
              constraint ddrdailyreportdata_ddrdailyreport_fkey
                  references ddrdailyreport
      );

    CREATE TABLE IF NOT EXISTS ddrdailytimedistributiondata
      (
          id BIGINT NOT NULL CONSTRAINT ddrdailytimedistributiondata_pkey PRIMARY KEY
      );

    CREATE TABLE IF NOT EXISTS ddrdailytimedistributionrecord
      (
          id BIGINT NOT NULL CONSTRAINT ddrdailytimedistributionrecord_pkey PRIMARY KEY,
          timeDistributionUid VARCHAR(64),
          timestamp BIGINT,
          name TEXT,
          content JSONB NULL,
          ddrdailytimedistributiondata_id BIGINT NOT NULL
              CONSTRAINT ddrtdrecord_ddrtddata_fkey
                  REFERENCES ddrdailytimedistributiondata(id)
      );

    UPDATE schema_version
    SET version = newVersion;

  END IF;
END ';

