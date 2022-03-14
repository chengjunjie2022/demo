package site.chengjunjie.demo.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("t_user")
@Accessors(chain = true)
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;
    private Short age;
    private String email;
}
