# Employee-Database-App
This is a simple console-based Java application that connects to a MySQL database using JDBC and performs CRUD (Create, Read, Update, Delete) operations on employee records.

🚀 Features

- Add new employee
- View all employees
- Update employee (salary, designation, or both)
- Delete employee by ID
- Uses `PreparedStatement` to avoid SQL injection
- MySQL backend with JDBC

𝄜 Database Setup (MySQL)

1. Open MySQL Workbench or your preferred MySQL CLI.
2. Create a database:

```sql
CREATE DATABASE employee_db;
USE employee_db;
```

🛠️ Technologies Used

- **Java 17+**
- **MySQL 9.x**
- **JDBC Driver** (`mysql-connector-java`)
- **IDE**: VS Code
