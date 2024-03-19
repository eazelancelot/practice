package com.example.practice;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class PracticeApplicationTests {

	@Test
	void contextLoads() {
		String str = "AA123456";
		char[] arr = str.toCharArray();
		//         array, fromIndex, toIndex, update_value
		Arrays.fill(arr, 4, str.length(), '*');
		System.out.println(new String(arr));
	}

}
