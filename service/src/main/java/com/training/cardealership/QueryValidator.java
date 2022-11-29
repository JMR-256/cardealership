package com.training.cardealership;


import com.training.cardealership.exceptions.InvalidQueryException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

public class QueryValidator {
    private Map<String, List<Predicate<String>>> validationRules;
    private boolean errorOnFieldMissingRule;

    public QueryValidator(boolean errorOnFieldMissingRule) {
        this.validationRules = new HashMap<>();
        this.errorOnFieldMissingRule = errorOnFieldMissingRule;
    }

    public QueryValidator addValidationRule(String param, List<Predicate<String>> rules) {
        validationRules.put(param, rules);
        return this;
    }

    public void validate(Map<String, String> queryParams) {
        queryParams.forEach((queryParam, value) -> {
            if (errorOnFieldMissingRule && !validationRules.containsKey(queryParam)) {
                throw new InvalidQueryException(queryParam);
            }
            if (validationRules.containsKey(queryParam)) {
                validationRules.get(queryParam).forEach(stringPredicate -> {
                    if (!stringPredicate.test(value)) {
                        throw new InvalidQueryException(stringPredicate.toString());
                    }
                });
            }
        });
    }
}
