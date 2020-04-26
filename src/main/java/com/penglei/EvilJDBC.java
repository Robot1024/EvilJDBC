package com.penglei;


import java.sql.*;

/**
 * @author ascetic
 * @version 1.0
 * @ClassName evilJDBC
 * @Description 实现最原始的JDBC操作，体会原始JDBC的痛点，理解ORM所解决的问题
 * @date 2020-04-26 19:12
 */
public class EvilJDBC {

    private Connection getConnection(){

        Connection connection = null;
        try {
            // 加载数据库驱动
            // 存在的问题：1.数据配置信息存在硬编码问题。2.频繁创建释放数据库连接    解决方案：1.配置文件。2.连接池
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/lagou?characterEncoding=utf-8";
            String user = "root";
            String password = "123456";
            // 通过驱动管理类获取数据库连接
            connection = DriverManager.getConnection(url,user,password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }


        return connection;
    }

    public User getRole(Long id){
        //通过 getConnection 方法获取数据库连接
        Connection connection = getConnection();
        //预处理statement 对象
        PreparedStatement ps = null;
        //结果集对象
        ResultSet rs = null;
        try {
            // 获取预处理对象
            // 存在的问题：1.sql 语句、设置参数、获取结果集参数均存在硬编码问题    解决方案：1.配置问题件
            ps = connection.prepareStatement("select id,username,password,birthday from user where id = ?");
            //设置参数，第一个参数为sql 语句中参数的序号
            ps.setLong(1,id);
            //执行查询语句 获取结果集
            rs = ps.executeQuery();

            while (rs.next()){
                Long userid = rs.getLong("id");
                //存在的问题：1.需要手动封装返回结果集，较为繁琐                解决方法：1.反射、内省
                String username = rs.getString("username");
                String password = rs.getString("password");
                String birthday = rs.getString("birthday");

                User user = new User();
                user.setId(id);
                user.setUsername(username);
                user.setPassword(password);
                user.setBirthday(birthday);

                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.close(rs,ps,connection);
        }

        return null;

    }
    /**
     * @Author ascetic
     * @Description 结果集资源要关闭，预处理对象要管理，连接对象要管理
     * java 都有垃圾回收处理机制为啥还要管理呢，因为
     * @Date 19:56 2020-04-26
     * @Param []
     * @return void
     **/
    private void close(ResultSet rs,Statement stmt,Connection connection){
        // 关闭结果集
        try {
            if(rs != null && !rs.isClosed()){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭预处理对象
        try {
            if(stmt != null && !stmt.isClosed()){
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭数据库连接
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        EvilJDBC evilJDBC = new EvilJDBC();
        User user = evilJDBC.getRole(1L);
        System.out.println("username = " + user.getUsername());

    }





}
