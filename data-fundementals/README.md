# Database Setup instructions

## Connect to database

Run docker compose up in root folder and connect to the running mysql database using library_db as default scheme

## Create Book Table

Execute

```SQL
CREATE TABLE IF NOT EXISTS BOOKS(
    id INT(6) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) NOT NULL,
    PRIMARY KEY(id)
);
```