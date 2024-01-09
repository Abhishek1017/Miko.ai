package assignment02;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobustDatabase {

    public static void main(String[] args) {
        // Example usage
        executeQuery("CREATE TABLE myTable (col1 INTEGER, col2 STRING)");
        executeQuery("INSERT INTO myTable VALUES (1, 'rohn'), (2, 'Smit'), (3, 'one'), (4, 'kush')");

    }

    public static void executeQuery(String query) {
        try {
            if (query.startsWith("CREATE TABLE")) {
                createTable(query);
            } else if (query.startsWith("INSERT INTO")) {
                insertIntoTable(query);
            } else {
                System.out.println("Unsupported query: " + query);
            }
        } catch (Exception e) {
            System.out.println("Error executing query: " + query);
            e.printStackTrace();
        }
    }

    private static void createTable(String query) throws IOException {
        // Extract table name, column names, and column types from the CREATE TABLE query
        Pattern pattern = Pattern.compile("CREATE TABLE (\\w+) \\((.+)\\)");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] columnDefs = matcher.group(2).split(", ");

            // Validate column definitions
            if (columnDefs.length == 0) {
                throw new IllegalArgumentException("No columns specified in CREATE TABLE query.");
            }

            // Write metadata to metadata file
            FileWriter metadataWriter = new FileWriter("metadata.txt", true);
            metadataWriter.write(tableName + "\n");
            for (String columnDef : columnDefs) {
                String[] parts = columnDef.split(" ");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid column definition: " + columnDef);
                }
                metadataWriter.write(parts[0] + " " + parts[1] + "\n");
            }
            metadataWriter.close();

            // Create an empty table file
            FileWriter tableWriter = new FileWriter(tableName + ".txt");
            tableWriter.close();

            System.out.println("Table created successfully: " + tableName);
        } else {
            throw new IllegalArgumentException("Invalid CREATE TABLE query: " + query);
        }
    }

    private static void insertIntoTable(String query) throws IOException {
        // Extract table name and values from the INSERT INTO query
        Pattern pattern = Pattern.compile("INSERT INTO (\\w+) VALUES \\((.+)\\)");
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1);
            String[] values = matcher.group(2).split("\\), \\(");

            // Validate values
            if (values.length == 0) {
                throw new IllegalArgumentException("No values specified in INSERT INTO query.");
            }

            // Append data to the table file
            FileWriter tableWriter = new FileWriter(tableName + ".txt", true);
            for (String value : values) {
                tableWriter.write(value + "\n");
            }
            tableWriter.close();

            System.out.println("Data inserted into table: " + tableName);
        } else {
            throw new IllegalArgumentException("Invalid INSERT INTO query: " + query);
        }
    }
}
