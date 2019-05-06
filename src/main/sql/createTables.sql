CREATE TABLE car (
	id INT NOT NULL AUTO_INCREMENT,
	vin VARCHAR(17) NOT NULL,
	model_code VARCHAR(40) NOT NULL,
	production_date DATE NULL,
	PRIMARY KEY (id)
);

CREATE TABLE control_unit (
	id INT NOT NULL AUTO_INCREMENT,
	type_code VARCHAR(3) NOT NULL,
	serial_number VARCHAR(30) NOT NULL,
	fk_car INT NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE control_unit
    ADD CONSTRAINT fk_car FOREIGN KEY (fk_car) REFERENCES car(id);

CREATE TABLE diag_object (
    object_id INT NOT NULL,
    object_branch INT NOT NULL,
    object_version INT NOT NULL,
    technical_name VARCHAR(80) NOT NULL,
    description VARCHAR(255),
    object_status VARCHAR(20) NOT NULL,
    created DATE NOT NULL,
    PRIMARY KEY (object_id, object_branch, object_version)
);
