@echo off

set THIS_DIR=%~dp0
set THIS_DIR=%THIS_DIR:~0,-1%
set SQL_DIR=%THIS_DIR%\src\main\sql

set CURRENT_DIR=%CD%


cd %SQL_DIR%
mysql -u root -p -Be "SOURCE dropDB.sql; SOURCE createDB.sql; USE jpa_training; SOURCE createTables.sql; SOURCE sampleData.sql;"
cd %CURRENT_DIR%
