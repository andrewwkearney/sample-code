/*
 * Copyright 2020. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue258;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class RandomRelatedLetters {
    public Collection<Newsletter> getRandomRelatedNewsletters(String issue,
                                                              String category,
                                                              int numberOfNewsletters) {
        List<Newsletter> result = new ArrayList<>(getNewsletters(category));
        result.remove(new Newsletter(issue));
        Collections.shuffle(result);
        return numberOfNewsletters < result.size() ? result.subList(0, numberOfNewsletters) : result;
    }

    public Collection<Newsletter> getStreamRandomRelatedNewsletters(String issue,
                                                                    String category,
                                                                    int numberOfNewsletters) {
        return getNewsletters(category).stream()
            .filter(newsletter -> !newsletter.getIssue().equals(issue))
            .collect(ShuffleCollector.shuffle())
            .limit(numberOfNewsletters)
            .collect(toList());
    }

    private Collection<Newsletter> getNewsletters(String category) {
        return Collections.emptyList();
    }
}
