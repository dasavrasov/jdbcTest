package office;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public enum Option {
    AddEmployee {
        String getText() {
            return this.ordinal() + ".Добавить сотрудника";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его имя:");
            String name=sc.next();
            System.out.println("Введите id отдела:");
            int depid=sc.nextInt();
            Service.addEmployee(new Employee(id,name,depid));
        }
    },
    DeleteEmployee {
        String getText() {
            return this.ordinal() + ".Удалить сотрудника";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Service.removeEmployee(new Employee(id,"",0));
        }
    },
    AddDepartment {
        String getText() {
            return this.ordinal() + ".Добавить отдел";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его название:");
            String name=sc.next();
            Service.addDepartment(new Department(id,name));
        }
    },
    DeleteDepartment {
        String getText() {
            return this.ordinal() + ".Удалить отдел";
        }

        void action() {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Service.removeDepartment(new Department(id,""));
        }
    },
    CLEAR_DB {
        String getText() {
            return this.ordinal() + ".Сбросить базу данных";
        }

        void action() {
            Service.createDB();
        }

    },
    PRINT_DEPS {
        String getText() {
            return this.ordinal() + ".Вывести на экран все отделы";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                PreparedStatement stm = con.prepareStatement(
                        "Select ID, NAME as txt from Department where name like ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                );
                String str="%";
                //ResultSet rs= stm.executeQuery("Select ID, NAME as txt from Department");
                stm.setString(1,str);
                ResultSet rs=stm.executeQuery();
                System.out.println("------------------------------------");
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("name"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }
    },
    PRINT_EMPLOYEES {
        String getText() {
            return this.ordinal() + ".Вывести на экран всех сотрудников";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                Statement stm = con.createStatement();
                ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Department.Name as DepName from Employee join Department on Employee.DepartmentID=Department.ID");
                //ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Employee.DepartmentID as DepName from Employee");
                System.out.println("------------------------------------");
                ResultSetMetaData metaData= rs.getMetaData();
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("NAME")+"\t"+rs.getString("DepName"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }   
    },
    UPDATE_ANN {
        String getText() {
            return this.ordinal() + ".Найти ID сотрудника с именем Ann. Если такой сотрудник только один, то установите его департамент в HR";
        }
        void action(){
            int size=0;
            int empId=0;
            try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
                PreparedStatement stm = con.prepareStatement("select ID from Employee where name=?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE);

                stm.setString(1,"Ann");
                ResultSet rs=stm.executeQuery();
                if (rs != null)
                {
                    rs.last();
                    size = rs.getRow();
                }
                if (size==1){
                    //Если такой сотрудник только один, то установите его департамент в HR.
                    empId=rs.getInt("ID");
                    System.out.println(empId);
                    stm = con.prepareStatement("update Employee set DepartmentID=(select ID from Department where name=?) where7 id=?",
                            ResultSet.TYPE_SCROLL_INSENSITIVE);
                    stm.setString(1,"HR");
                    stm.setInt(2,empId);
                    stm.executeUpdate();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    },
    CHECK_DEPARTS {
        String getText() {
            return this.ordinal() + ".Проверить, есть ли в базе сотрудники у которых ID отдела отсутствует в таблице Department";
        }
        void action(){
            try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
                Statement stm = con.createStatement();

                ResultSet rs=stm.executeQuery("select ID, NAME from Employee where not exists (select ID from Department where Department.id=Employee.DepartmentId)");
                while(rs.next())
                {
                    System.out.println("Ошибка!  - Сотрудник "+rs.getInt("ID")+" "+rs.getString("NAME")+" работает в несуществующем отделе!" );
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    },
    EXIT {
        String getText() {
            return this.ordinal() + ".Выход";
        }

        void action() {
            System.out.println("выход");
        }
    },;
    
    Scanner sc = new Scanner(System.in);
    abstract String getText();
    abstract void action();
}
