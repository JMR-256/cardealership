package com.training.cardealership.validation;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class StringValidatorsTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    void test_notEmpty_returnsFalse(String value) {
        System.out.println("fdsafdsa");
        Assertions.assertFalse(StringValidators.notEmpty.test(value));
    }


    @Test
    void test_notEmpty_returnsTrueWhenStringIsNotEmpty() {
        Assertions.assertTrue(StringValidators.notEmpty.test("test"));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"test"})
    void containsWhitespace_returnsFalse(String value) {
        Assertions.assertFalse(StringValidators.containsWhitespace.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test ", "test test", "test     test"})
    void containsWhitespace_returnsTrue(String value) {
        Assertions.assertTrue(StringValidators.containsWhitespace.test(value));
    }


    @ParameterizedTest
    @ValueSource(strings = {"test", "test\ntest"})
    @NullSource
    @EmptySource
    void notContainingWhitespace_returnsTrue(String value) {
        Assertions.assertTrue(StringValidators.notContainingWhitespace.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello test", "hello    test"})
    void notContainingWhitespace_returnsFalse(String value) {
        Assertions.assertFalse(StringValidators.notContainingWhitespace.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"?", "!", "/", "%", "23df*"})
    void containsSpecials_returnsTrue(String value) {
        Assertions.assertTrue(StringValidators.containsSpecials.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "asf", "fdsa2", "3e", "    "})
    @EmptySource
    @NullSource
    void containsSpecials_returnsFalse(String value) {
        Assertions.assertFalse(StringValidators.containsSpecials.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"?", "!", "/", "%", "23df*"})
    void notContainingSpecials_returnsFalse(String value) {
        Assertions.assertFalse(StringValidators.notContainingSpecials.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "asf", "fdsa2", "3e", "    "})
    @EmptySource
    @NullSource
    void notContainingSpecials_returnsTrue(String value) {
        Assertions.assertTrue(StringValidators.notContainingSpecials.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1", "5"})
    void isInteger_returnsTrue(String value) {
        Assertions.assertTrue(StringValidators.isInteger.test(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "asfd", "5.5"})
    @NullSource
    @EmptySource
    void isInteger_returnsFalse(String value) {
        Assertions.assertFalse(StringValidators.isInteger.test(value));
    }

}
