package site.chengjunjie.demo.swagger.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("用户详情返回信息")
public class UserResp {
    @ApiModelProperty("编号")
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Short age;

    @ApiModelProperty("邮箱")
    private String email;
}
