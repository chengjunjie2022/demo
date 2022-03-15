package site.chengjunjie.demo.swagger.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("用户信息")
@ToString
public class UserModResp {
    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Short age;

    @ApiModelProperty("邮箱")
    private String email;
}
