package com.ruhuna.uniplus;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UniplusApplicationTests {

    @Disabled("Disabled in CI due to missing DB")
	@Test
	void contextLoads() {
	}

}
