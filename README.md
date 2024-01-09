# Miko.ai
# Questons
<h3>Junior Developer Assignment (R1):</h3>
Create a simple file based one table database. It should be a simple text file which holds one table.
The metadata about the table, the names of the columns and the types of the columns should be in a
different table file.
Support integers and character data types for now.
Whenever the user creates a table, the syntax will be of the form CREATE TABLE ( col1 INTEGER, col2
STRING,....). It can have any number of columns. This query will be parsed and info stored in the
metadata file.
Whenever the user issues an insert command the insert will happen in the table file. The insert will be of
the form INSERT into VALUES (col1, col2,..) VALUES(,)…
