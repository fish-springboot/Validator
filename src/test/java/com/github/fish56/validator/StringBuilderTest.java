package com.github.fish56.validator;

import org.junit.Test;

public class StringBuilderTest {
    @Test
    public void length(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hello world, ");
        stringBuilder.delete(stringBuilder.length() - 3, stringBuilder.length() - 1);
        System.out.println(stringBuilder.toString());
        // Hello worl
    }
}
