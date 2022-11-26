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


at some point later we update the table to include a rating column, can either do that at the start or later (if doing it at the start then we'll want a default value - which we'd need if we're adding later (that or allow null))
column would look something like ```rating INT`` - in this case I'm allowing null