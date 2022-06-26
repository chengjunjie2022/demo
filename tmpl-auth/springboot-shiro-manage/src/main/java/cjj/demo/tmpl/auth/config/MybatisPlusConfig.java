package cjj.demo.tmpl.auth.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import road.cjj.commons.entity.consts.SysDefCfg;

@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor=new PaginationInnerInterceptor();

        //设置请求的页面大于最后页面操作 ，true调回首页，false继续请求，默认false；
        paginationInnerInterceptor.setOverflow(false);
        //设置最大单页限制数量，默认500条 ，-1不受限制
        paginationInnerInterceptor.setMaxLimit(SysDefCfg.PAGE_MAX_SIZE);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
