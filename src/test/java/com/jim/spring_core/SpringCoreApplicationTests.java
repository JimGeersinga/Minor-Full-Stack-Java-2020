package com.jim.spring_core;

import com.jim.spring_core.service.StringCapsService;
import com.jim.spring_core.service.StringTransformService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"caps"})
class SpringCoreApplicationTests {

	@Autowired
	StringTransformService stringTransformService;

	@Test
	void contextLoads() {
		assertThat(stringTransformService).isNotNull();
		assertThat(stringTransformService).isOfAnyClassIn(StringCapsService.class);
	}

}
