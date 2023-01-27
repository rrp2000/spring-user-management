package com.example.saveUser;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class SaveUserApplicationTests {

	Calculator calculator = new Calculator();
	@Test
	void contextLoads() {
	}

	@Test
	void testSum(){
		int expectedResult = 11;

		int actualResult = calculator.doSum(5,6);

		assertThat(actualResult).isEqualTo(expectedResult);
	}

}
