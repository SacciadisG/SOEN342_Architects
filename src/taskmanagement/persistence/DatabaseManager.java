package taskmanagement.persistence;

import java.sql.*;

/**
 * Database manager for SQLite persistence.
 * Handles all database operations including connection management and table creation.
 */
public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:taskmanagement.db";
    private Connection connection;

    public DatabaseManager() {
        initializeDatabase();
    }

    /**
     * Initialize database connection and create tables if they don't exist
     */
    private void initializeDatabase() {
        try {
            // Load SQLite driver
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
            createTables();
            System.out.println("Database initialized successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    /**
     * Create all necessary tables for the task management system
     */
    private void createTables() throws SQLException {
        Statement stmt = connection.createStatement();

        // Tasks table
        stmt.execute("CREATE TABLE IF NOT EXISTS tasks (" +
                "task_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "description TEXT," +
                "creation_date TEXT NOT NULL," +
                "due_date TEXT," +
                "priority TEXT DEFAULT 'MEDIUM'," +
                "status TEXT DEFAULT 'OPEN'," +
                "project_id INTEGER," +
                "FOREIGN KEY (project_id) REFERENCES projects(project_id)" +
                ")");

        // Projects table
        stmt.execute("CREATE TABLE IF NOT EXISTS projects (" +
                "project_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT" +
                ")");

        // Tags table
        stmt.execute("CREATE TABLE IF NOT EXISTS tags (" +
                "tag_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE NOT NULL" +
                ")");

        // Task_Tags association table (many-to-many)
        stmt.execute("CREATE TABLE IF NOT EXISTS task_tags (" +
                "task_id INTEGER NOT NULL," +
                "tag_id INTEGER NOT NULL," +
                "PRIMARY KEY (task_id, tag_id)," +
                "FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE," +
                "FOREIGN KEY (tag_id) REFERENCES tags(tag_id)" +
                ")");

        // Subtasks table
        stmt.execute("CREATE TABLE IF NOT EXISTS subtasks (" +
                "subtask_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task_id INTEGER NOT NULL," +
                "title TEXT NOT NULL," +
                "status TEXT DEFAULT 'OPEN'," +
                "FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE" +
                ")");

        // Activity entries table
        stmt.execute("CREATE TABLE IF NOT EXISTS activity_entries (" +
                "activity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "task_id INTEGER NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "description TEXT NOT NULL," +
                "FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE" +
                ")");

        // Collaborators table
        stmt.execute("CREATE TABLE IF NOT EXISTS collaborators (" +
                "collaborator_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "max_open_tasks INTEGER NOT NULL" +
                ")");

        // Task_Collaborators association table
        stmt.execute("CREATE TABLE IF NOT EXISTS task_collaborators (" +
                "task_id INTEGER NOT NULL," +
                "collaborator_id INTEGER NOT NULL," +
                "PRIMARY KEY (task_id, collaborator_id)," +
                "FOREIGN KEY (task_id) REFERENCES tasks(task_id) ON DELETE CASCADE," +
                "FOREIGN KEY (collaborator_id) REFERENCES collaborators(collaborator_id)" +
                ")");

        stmt.close();
    }

    /**
     * Get database connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DATABASE_URL);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving database connection: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Close database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }

    /**
     * Execute a query with no results (INSERT, UPDATE, DELETE)
     */
    public void executeUpdate(String sql) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    /**
     * Execute a query that returns results (SELECT)
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(sql);
    }

    /**
     * Get next available ID for a table
     */
    public long getNextId(String tableName) throws SQLException {
        String sql = "SELECT MAX(ROWID) as max_id FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getLong("max_id") + 1;
            }
        }
        return 1;
    }
}
