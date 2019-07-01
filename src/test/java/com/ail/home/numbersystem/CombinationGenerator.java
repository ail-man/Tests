package com.ail.home.numbersystem;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CombinationGenerator {

    @Test
    public void testGenerateCombinations() {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            elements.add(String.valueOf(i));
        }

        int rowElementsCount = 3;

        List<List<String>> combinations = allPossibleCombinations(elements, rowElementsCount);
        combinations.forEach(System.out::println);
        System.out.println(combinations.size());
    }

    private <T> List<List<T>> allPossibleCombinations(List<T> elements, int rowElementsCount) {
        int elementsSize = elements.size();

        if (elementsSize > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Maximum allowed elements number is " + Character.MAX_RADIX
                    + ". Current number is " + elementsSize);
        }

        double pow = Math.pow(elementsSize, rowElementsCount);
        if (pow > (double) Integer.MAX_VALUE) {
            throw new ArrayIndexOutOfBoundsException("Unable to create more combinations than " + Integer.MAX_VALUE
                    + ". Current amount of possible combinations is " + pow);
        }

        int amountOfCombinations = (int) pow;
        List<List<T>> possibleCombinations = new ArrayList<>(amountOfCombinations);

        String maxCombinationNumber = BigInteger.valueOf(amountOfCombinations - 1).toString(elementsSize);
        int numberLength = maxCombinationNumber.length();

        for (int i = 0; i < amountOfCombinations - 1; i++) {
            String nextCombinationNumber = BigInteger.valueOf(i).toString(elementsSize);
            nextCombinationNumber = StringUtils.leftPad(nextCombinationNumber, numberLength, "0");
//            System.out.println(nextCombinationNumber);

            List<T> nextCombination = generateCombination(elements, nextCombinationNumber, numberLength);
            possibleCombinations.add(nextCombination);
        }

        possibleCombinations.add(generateCombination(elements, maxCombinationNumber, numberLength));

        return possibleCombinations;
    }

    private <T> List<T> generateCombination(List<T> elements, String combinationNumber, int elementsInCombination) {
        List<T> combination = new ArrayList<>(elementsInCombination);

        for (int i = 0; i < elementsInCombination; i++) {
            char charAt = combinationNumber.charAt(i);

            int elementIndex = getElementIndex(charAt);
            T nextElement = elements.get(elementIndex);
            combination.add(nextElement);
        }

        return combination;
    }

    private int getElementIndex(char charAt) {
        int index;

        if ('0' <= charAt && charAt <= '9') {
            index = charAt - 48;
        } else if ('a' <= charAt && charAt <= 'z') {
            index = charAt - 87;
        } else {
            throw new IllegalArgumentException("Only 0-9 and a-z supported for current number system");
        }

//        System.out.println("letter: " + charAt + ", char: " + index);

        return index;
    }

    @Test
    public void testChars() {
        char c = 'z';
        System.out.println((int) c);
    }
}