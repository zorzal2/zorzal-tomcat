CREATE TABLE TOOLBAR_COLUMN
(
  PROPERTY VARCHAR2(4000),
  VISIBLE VARCHAR2(4000),
  USER_ID VARCHAR2(4000),
  TOOLBAR_ID VARCHAR2(4000),
  TOOLBAR_COLUMN_ID NUMBER NOT NULL
, CONSTRAINT TOOLBAR_COLUMN_PK PRIMARY KEY
  (
    TOOLBAR_COLUMN_ID
  )
)
;

CREATE SEQUENCE  "ABUY"."SEQ_IDTOOLBAR_COLUMN"  MINVALUE 1 MAXVALUE 1.00000000000000E+27 INCREMENT BY 1 START WITH 26 NOCACHE  NOORDER  NOCYCLE ;


