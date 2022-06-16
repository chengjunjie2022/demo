package cjj.demo.shiro.springboot.mybatis.dao;

import cjj.demo.shiro.springboot.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getUserByUsername(String username);
}
