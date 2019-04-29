CREATE DATABASE jpa_training;

CREATE USER 'app_user'@'%' IDENTIFIED BY 'app_pass';
GRANT USAGE ON jpa_training.* TO 'app_user'@'%';
GRANT ALL PRIVILEGES ON jpa_training.* TO 'app_user'@'%';
FLUSH PRIVILEGES;
