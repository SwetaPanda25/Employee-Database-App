import java.sql.*;
import java.util.*;

public class Employee{
    static final String url = "jdbc:mysql://localhost:3306/employee_db";
    static final String user = "root";
    static final String pass = "root";

    private static Connection conn;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        try{
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to MySql...");

            while(true){
                System.out.println("\n--- Employee Database ---\n");
                System.out.println("1. Add Employee");
                System.out.println("2. View All Employees");
                System.out.println("3. Update Employee Record");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch(choice){
                    case 1: add(sc); break;
                    case 2: view(); break;
                    case 3: update(sc); break;
                    case 4: delete(sc); break;
                    case 5: { System.out.println("Exiting...");
                              conn.close();
                              return;
                    }
                    default: System.out.println("Invalid choice!");
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void view(){
        try{
            String sql = "SELECT * FROM employee";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            
            System.out.println("\nID\tName\t\tDepartment\t\tDesignation\tSalary");
            System.out.println("---------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%d\t%-10s\t%-10s\t%-10s\t%.2f\n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("department"),
                        rs.getString("designation"),
                        rs.getDouble("salary"));
            }
        }
        catch(SQLException e){
            System.out.println("Error fetching employees.");
            e.printStackTrace();
        }
    }

    public static void add(Scanner sc){
        try{
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Department: ");
            String dept = sc.nextLine();

            System.out.print("Enter Designation: ");
            String des = sc.nextLine();

            System.out.print("Enter Salary: ");
            double sal = sc.nextDouble();

            String sql = "INSERT INTO employee (name, department, designation, salary) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setString(3, des);
            ps.setDouble(4, sal);

            int rows = ps.executeUpdate();
            System.out.println("Employee added Successfully! (" + rows + " row affected)");
        }
        catch(SQLException e){
            System.out.println("Error Adding Employee!!");
            e.printStackTrace();
        }
    }

    public static void update(Scanner sc){
        try{
            System.out.print("Enter employee ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.println("What you want to update?\nDesignation or Salary or Both");
            String want = sc.nextLine().trim();
            
            if(want.equalsIgnoreCase("salary")){
                System.out.print("Enter new salary: ");
                double salary = sc.nextDouble();

                String sql = "UPDATE employee SET salary = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setDouble(1, salary);
                ps.setInt(2, id);
                
                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("Employee Updated.");
                else
                    System.out.println("Employee not found.");
            }
            else if(want.equalsIgnoreCase("designation")){
                System.out.print("Enter new Designation: ");
                String designation = sc.nextLine();

                String sql = "UPDATE employee SET designation = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, designation);
                ps.setInt(2, id);

                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("Employee Updated.");
                else
                    System.out.println("Employee not found.");
            }
            else if(want.equalsIgnoreCase("both")){
                System.out.print("Enter new salary: ");
                double salary = sc.nextDouble();
                sc.nextLine();
                System.out.print("Enter new Designation: ");
                String designation = sc.nextLine();

                String sql = "UPDATE employee SET designation = ?, salary = ? WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, designation);
                ps.setDouble(2, salary);
                ps.setInt(3, id);

                int rows = ps.executeUpdate();
                if (rows > 0)
                    System.out.println("Employee Updated.");
                else
                    System.out.println("Employee not found.");   
            }
            else{
                System.out.println("Not a valid option.");
            }
        }
        catch(SQLException e){
            System.out.println("Error Updating Employee!!");
            e.printStackTrace();
        }
    }

    public static void delete(Scanner sc){
        try{
            System.out.print("Enter employee ID to be deleted: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM employee WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Employee Deleted.");
            else
                System.out.println("Employee not found.");
        }
        catch(SQLException e){
            System.out.println("Error Deleting Employee!!");
            e.printStackTrace();
        }
    }
}