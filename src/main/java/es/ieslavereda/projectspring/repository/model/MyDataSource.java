package es.ieslavereda.projectspring.repository.model;

import javax.sql.DataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class MyDataSource {
    public static DataSource getMySQLDataSource() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL("jdbc:mysql://127.0.0.1:3306/java");
        mysqlDS.setUser("root");
        mysqlDS.setPassword("1234");

        return mysqlDS;

    }
}
