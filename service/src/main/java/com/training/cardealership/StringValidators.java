package com.training.cardealership;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class StringValidators {

    public static final Predicate<String> notEmpty = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            return s !=null && s.length() > 0;
        }
    };

    public static final Predicate<String> containsWhitespace = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            Pattern p = Pattern.compile("\s+", Pattern.CASE_INSENSITIVE);
            return s!= null && p.matcher(s).find();
        }
    };
    public static final Predicate<String> notContainingWhitespace = containsWhitespace.negate();

    public static final Predicate<String> containsSpecials = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            Pattern p = Pattern.compile("[^a-z0-9\s]", Pattern.CASE_INSENSITIVE);
            return s != null && p.matcher(s).find();
        }
    };

    public static final Predicate<String> notContainingSpecials = containsSpecials.negate();

    public static final Predicate<String> isInteger = new Predicate<String>() {
        @Override
        public boolean test(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    };
}
