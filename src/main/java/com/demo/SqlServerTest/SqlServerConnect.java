package com.demo.SqlServerTest;


import java.sql.*;

public class SqlServerConnect {
    private Connection con;
    private Statement st;
    private String driverName = "";
    private String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=LUXYTEST";
    private String userName = "sa";
    private String userPwd = "jky12375";
    private ResultSet rs;

    public void createConnection() {
        try {
            System.out.println("数据库连接开始---");
            this.con = DriverManager.getConnection(dbURL, userName, userPwd);
            this.st = con.createStatement();
            System.out.println("连接--" + dbURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ResultSet query(String sql) {
        try {
            System.out.println("sql查询开始---");
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
    private void insertData(){
        SqlServerConnect ssc = new SqlServerConnect();
        ssc.setDbURL(ssc.dbURL);
        ssc.setUserName(ssc.userName);
        ssc.setUserPwd(ssc.userPwd);
        PreparedStatement ps = null;
        ssc.createConnection();
        String sql = "select * from LUXYTEST.dbo.TDD_DIFF_N0601 where deviceid in(SELECT a.deviceid from \n" +
                "LUXYTEST.dbo.Deviceinfo a  join LUXYTEST.dbo.Ts_water_define b \n" +
                "on a.deviceNo =  b.deviceNo );";

        ResultSet set = ssc.query(sql);

        try {
            while (set.next()) {
                String str = set.getString("date");
                System.out.println(str);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String[] args) {


    }


    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDbURL() {
        return dbURL;
    }

    public void setDbURL(String dbURL) {
        this.dbURL = dbURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }
}
