package site.chengjunjie.demo.swagger.dto.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("用户分页查询参数")
public class UserPageReq {

    @ApiModelProperty("年龄")
    private Short age;

    @ApiModelProperty("邮箱")
    private String email;
}
