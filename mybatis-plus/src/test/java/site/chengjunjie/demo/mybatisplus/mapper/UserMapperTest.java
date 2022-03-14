package site.chengjunjie.demo.mybatisplus.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.chengjunjie.demo.mybatisplus.entity.User;

import java.util.List;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList(){
        System.out.println(("----- selectList method test ------"));
        List<User> userList = userMapper.selectList(null);
//        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void testInsertUser(){
        System.out.println(("----- insertUser method test ------"));
        User user = new User().setName("jone").setAge((short)30).setEmail("jone@163.com");
        userMapper.insert(user);
        System.out.println("id=" + user.getId());
    }
}
