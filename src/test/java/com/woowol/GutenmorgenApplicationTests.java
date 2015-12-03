package com.woowol;

import com.woowol.gutenmorgen.GutenmorgenApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GutenmorgenApplication.class)
@WebAppConfiguration
public class GutenmorgenApplicationTests {

	@Test
	public void contextLoads() {
	}

}
