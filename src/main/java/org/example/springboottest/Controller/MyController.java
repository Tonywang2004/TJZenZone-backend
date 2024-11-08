package org.example.springboottest.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5173/")
@RequestMapping("myHello")
public class MyController {
    @GetMapping("hello")
    @ResponseBody
    public String hello(){
        return "hello world!";
    }

    //以下postman直接传网址  /greet1/张三   就可return 张三
    @PostMapping("greet1/{name}")
    @ResponseBody
    public String greet1(@PathVariable(value = "name")String name){
        return name;
    }

    //以下postman使用body——x-www-form-urlencoded，key为name
    @PostMapping("greet2")
    @ResponseBody
    public String greet2(@RequestBody() Map<String, String> requestData){
        String name=requestData.get("name");
        String id=requestData.get("id");
        if(id==null)
            return "id is empty";
        String url = "jdbc:mysql://localhost:3306/test"; // 修改为你的数据库 URL
        String username = "root"; // 修改为你的数据库用户名
        String password = "wrh20040417"; // 修改为你的数据库密码

        // 插入数据的 SQL 语句
        String sql = "INSERT INTO user (UserId, UserName, Password, Email, CreatedTime,UpdatedTime) VALUES (?, ?, ?, ?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置 SQL 语句中的参数
            pstmt.setString(1, id); // user_id
            pstmt.setString(2, name); // username
            pstmt.setString(3, "hashed_password"); // password
            pstmt.setString(4, "john@example.com"); // email
            pstmt.setString(5, "john@example.com");
            pstmt.setString(6, "john@example.com");
            // 执行插入操作
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "hello,"+name+"!";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
