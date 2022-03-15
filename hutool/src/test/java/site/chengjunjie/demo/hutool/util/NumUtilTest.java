package site.chengjunjie.demo.hutool.util;

import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class NumUtilTest {

    @Test
    public void testRound(){
        log.info("----- round method test ------");
        log.info("result = " + NumUtil.round(3.1456));
    }

}
