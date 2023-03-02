import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class ServiceTest {

    /*
    * Чтобы тесты не завиели друг от друга вызов метода сервиса в каждом тесте делается только 1 раз
    * остальные действия (добавление, удаление) - вручную
     */

    @Test
    public void testAddDepartment(){
        Department department = new Department(Integer.MAX_VALUE,"TESTING");
        Statement stm;
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            stm = con.createStatement();
            stm.executeUpdate("delete from Department where ID=" + Integer.MAX_VALUE);

            //добавляем отдел
            Service.addDepartment(department); //вызов сервиса
            //проверяем, что добавлился
            ResultSet rs = stm.executeQuery("select ID from Department where ID=" + Integer.MAX_VALUE);
            while (rs.next()) {
                //нашли, все ок
                stm.executeUpdate("delete from Department where ID=" + Integer.MAX_VALUE); //удалил, что добавили
                return;
            }
            //не нашли, ошибка
            Assertions.fail("Отдел не добавился");
        }catch (Exception e) {
            //не нашли, ошибка
            Assertions.fail("При добавдении отдела исключение "+e.getMessage());
        }
    }

    @Test
    public void testAddEmployee(){
        Employee employee = new Employee(Integer.MAX_VALUE,"TEST",0);
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();
            stm.executeUpdate("delete from Employee where ID=" + Integer.MAX_VALUE);

            Service.addEmployee(employee); //вызов сервиса

            ResultSet rs = stm.executeQuery("select ID from Employee where ID=" + Integer.MAX_VALUE);
            while (rs.next()) {
                //нашли, все ок
                stm.executeUpdate("delete from Employee where ID=" + Integer.MAX_VALUE); //удалил, что добавили
                return;
            }
            Assertions.fail("Сотрудник не добавился");
        }catch (Exception e) {
            Assertions.fail("При добавдении сотрудника исключение "+e.getMessage());
        }
    }

    @Test
    public void testRemoveEmployee(){
        //добавить пользователя
        //проверить, что пользователь добавился
        ResultSet rs;
        PreparedStatement stm;
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            //сначала добавляем нового сотрудника в таблицу вручную
            stm= con.prepareStatement("delete from Employee where ID=?");
            stm.setInt(1, Integer.MAX_VALUE);
            stm.executeUpdate();
            stm = con.prepareStatement("insert into Employee (ID,NAME,DEPARTMENTID) VALUES (?,?,?)");
            stm.setInt(1, Integer.MAX_VALUE);
            stm.setString(2, "TEST");
            stm.setInt(3, 0);
            stm.executeUpdate();

            //проверяем, что добавили
            stm = con.prepareStatement("select count(*) as cnt from Employee where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            rs=stm.executeQuery();
            rs.next();
            if (rs.getInt("cnt")==0)
                Assertions.fail("Ошибка: не удалось добавить сотрудника");

            //сотрудник добавлен
            //теперь удаяляем через метод Service.removeEmployee
            Employee employee=new Employee(Integer.MAX_VALUE,"TEST",0);

            Service.removeEmployee(employee); //вызов сервиса

            //надо проверить, что сотрудника нет
            stm = con.prepareStatement("select count(*) as cnt from Employee where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            rs=stm.executeQuery();
            rs.next();
            if (rs.getInt("cnt")>0)
                Assertions.fail("Ошибка: сотрудник не удалился");

        } catch (Exception e) {
            Assertions.fail("Ошибка теста "+e.getMessage());
        }
    }
//                    System.out.println("Ошибка!  - Сотрудник "+rs.getInt("ID")+" "+rs.getString("NAME")+" работает в несуществующем отделе!" );

    /*
     * При удалении отдела (Department) информация о всех сотрудниках, работающих в этом отделе, должна быть удалена
     */
    @Test
    public void testRemoveDepartment(){
        Department department = new Department(Integer.MAX_VALUE,"TESTING");
        PreparedStatement stm;
        ResultSet rs;
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            stm = con.prepareStatement("delete from Department where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            stm.executeUpdate();
            stm = con.prepareStatement("insert into Department (ID,NAME) values (?,?)");
            stm.setInt(1,Integer.MAX_VALUE);
            stm.setString(2,"TEST");
            stm.executeUpdate();

            //добавим сотрудника в отдел
            stm = con.prepareStatement("delete from Employee where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            stm.executeUpdate();
            stm = con.prepareStatement("insert into Employee (ID,NAME,DEPARTMENTID) values (?,?,?)");
            stm.setInt(1,Integer.MAX_VALUE);
            stm.setString(2,"TEST");
            stm.setInt(3,Integer.MAX_VALUE);
            stm.executeUpdate();

            //удаляем отдел - вызов сервиса
            Service.removeDepartment(department);

            //проверяем, удалился ли отдел
            stm = con.prepareStatement("select count(*) as cnt from Department where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            rs=stm.executeQuery();
            rs.next();
            if (rs.getInt("cnt")>0)
                Assertions.fail("Ошибка: отдел не удалился");

            //проверяем, удалился ли сотрудник
            stm = con.prepareStatement("select count(*) as cnt from Employee where ID=?");
            stm.setInt(1,Integer.MAX_VALUE);
            rs=stm.executeQuery();
            rs.next();
            if (rs.getInt("cnt")>0)
                Assertions.fail("Ошибка: при удалении отдела сотрудник не удалился");

        } catch (Exception e) {
            Assertions.fail("Ошибка теста "+e.getMessage());
        }

    }
}
