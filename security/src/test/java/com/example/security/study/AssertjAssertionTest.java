package com.example.security.study;

import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssertjAssertionTest {
	@DisplayName("isEqualTo 에서 배열은 주소가 아닌 값을 비교한다.")
	@Test
	void testAssertionsArray() {
		//given
		int[] array = IntStream.rangeClosed(0, 10).toArray();
		int[] array2 = IntStream.rangeClosed(0, 10).toArray();
		//when
		//then
		Assertions.assertThat(array == array2).isFalse();
		Assertions.assertThat(array).isEqualTo(array2);
	}
}
