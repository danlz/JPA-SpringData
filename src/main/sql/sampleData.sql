INSERT INTO car(vin, model_code, production_date) VALUES ('WAUZZZ8P12345678', 'S3', null);
SELECT LAST_INSERT_ID() INTO @car_id;
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ECU', '8P0.035.193.G', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('AC', '8P0.287.888.A', @car_id);


INSERT INTO car(vin, model_code, production_date) VALUES ('WAUZZZ9X55345678', 'S8', null);
SELECT LAST_INSERT_ID() INTO @car_id;
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ECU', '9X1.285.233.Y', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('AC', '9X5.113.279.A', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ESP', '9X2.690.005.E', @car_id);


INSERT INTO car(vin, model_code, production_date) VALUES ('WAUZZZ8P12345999', 'RS3', null);
SELECT LAST_INSERT_ID() INTO @car_id;
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ECU', '8P0.783.244.G', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('AC', '8P0.287.888.A', @car_id);


INSERT INTO car(vin, model_code, production_date) VALUES ('WAUZZZ6G123456AV', 'A6 Avant', null);
SELECT LAST_INSERT_ID() INTO @car_id;
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ECU', '6G1.485.581.J', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('AC', '6G5.152.203.A', @car_id);
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ABS', '6G2.388.105.B', @car_id);


INSERT INTO car(vin, model_code, production_date) VALUES ('WAUZZZ4P12345TDI', 'A4 w TDIku :)', null);
SELECT LAST_INSERT_ID() INTO @car_id;
INSERT INTO control_unit(type_code, serial_number, fk_car) VALUES('ECU', '4P2.310.318.D', @car_id);


INSERT INTO diag_object(object_id, object_branch, object_version, technical_name, description, object_status, created)
    VALUES (1, 1, 1, 'RPM-MV', 'measurement of engine RPM', 'ACCEPTED', '2019-04-30');


COMMIT;
