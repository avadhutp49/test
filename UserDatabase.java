import java.sql.*;
import java.util.Scanner;

public class UserDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/currency";
    //replace 'currency' with acutal db name.
    private static final String USER = "root";
    // update username
    private static final String PASSWORD = "root";
    // update password

    private Connection connection;

    public UserDatabase() {
        try {
            // Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public void addUser(int id, String name, int age) {
        String sql = "INSERT INTO test1 (id, name, age) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, age);
            pstmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        String sql = "SELECT * FROM test1";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("Id \tName \tAge");
            while (rs.next()) {
                System.out.println("" + rs.getInt("id") +
                                   "\t" + rs.getString("name") +
                                   "\t" + rs.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int id, String name, int age) {
        String sql = "UPDATE test1 SET name = ?, age = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("User updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM test1 WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Update User");
            System.out.println("4. Delete User");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Id: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    addUser(id, name, age);
                    break;
                case 2:
                    System.out.println("All users:");
                    getAllUsers();
                    break;
                case 3:
                    System.out.print("Enter user ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new age: ");
                    int newAge = scanner.nextInt();
                    updateUser(updateId, newName, newAge);
                    break;
                case 4:
                    System.out.print("Enter user ID to delete: ");
                    int deleteId = scanner.nextInt();
                    deleteUser(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        UserDatabase userDB = new UserDatabase();
        userDB.start();
    }
}