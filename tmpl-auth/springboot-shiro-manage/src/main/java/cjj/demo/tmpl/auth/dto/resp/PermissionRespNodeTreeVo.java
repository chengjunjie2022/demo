package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRespNodeTreeVo {

    @ApiModelProperty("权限id")
    private String id;

    @ApiModelProperty("菜单权限名称")
    private String title;

    @ApiModelProperty("level几级子菜单")
    private int level;
}
