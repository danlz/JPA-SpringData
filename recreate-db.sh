#!/bin/bash

abspath() {
    path=`eval echo "$1"`
    folder=$(dirname "$path")
    echo $(cd "$folder"; pwd)/$(basename "$path");
}

SQL_DIR=$(abspath $(dirname "$0")/src/main/sql)


cd $SQL_DIR
mysql -u root -p -Be "SOURCE dropDB.sql; SOURCE createDB.sql; USE jpa_training; SOURCE createTables.sql; SOURCE sampleData.sql;"
cd - > /dev/null
