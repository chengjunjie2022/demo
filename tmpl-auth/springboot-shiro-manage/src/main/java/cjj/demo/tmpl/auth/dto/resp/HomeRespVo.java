package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeRespVo {

    @ApiModelProperty("用户信息")
    private UserRespVo userInfo;

    @ApiModelProperty("权限菜单")
    private List<PermissionRespNodeVo> menus;
}
