package cjj.demo.shiro.springboot.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface RoleDao {
    Set<String> getRoleNamesByUsername(String username);
}
