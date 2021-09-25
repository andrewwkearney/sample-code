/*
 * Copyright 2021. Androsaces. All rights reserved.
 */

package com.androsaces.javaessentials.issue263;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelReader {
    private final List<String> mEntityName = new ArrayList<>();
    private final ModelEntity mModelEntity = new ModelEntity("", "");

    public List<String> getEntityNames() {
        return mEntityName;
    }

    public ModelEntity getModelEntity(String name) {
        return mModelEntity;
    }

    public Map<String, TreeSet<String>> getEntitiesByPackage(
            Set<String> packageFilterSet, Set<String> entityFilterSet) {
        Map<String, TreeSet<String>> entitiesByPackage = new HashMap<>();
        //put the entityNames TreeSets in a HashMap by packageName
        Iterator<String> ecIter = this.getEntityNames().iterator();
        while (ecIter.hasNext()) {
            String entityName = ecIter.next();
            ModelEntity entity = this.getModelEntity(entityName);
            String packageName = entity.getPackageName();
            if (!packageFilterSet.isEmpty()) {
                // does it match any of these?
                boolean foundMatch = false;
                for (String packageFilter : packageFilterSet) {
                    if (packageName.contains(packageFilter)) {
                        foundMatch = true;
                    }
                }
                if (!foundMatch) {
                    continue;
                }
            }
            if (!entityFilterSet.isEmpty()
                    && !entityFilterSet.contains(entityName)) {
                continue;
            }
            TreeSet<String> entities = entitiesByPackage.get(packageName);
            if (entities == null) {
                entities = new TreeSet<>();
                entitiesByPackage.put(packageName, entities);
            }
            entities.add(entityName);
        }
        return entitiesByPackage;
    }

    public Map<String, TreeSet<String>> improvedGetEntitiesByPackage(Set<String> packageFilterSet, Set<String> entityFilterSet) {
        return this.getEntityNames().stream()
                .map(_entityName -> new Object() {
                    final String entityName = _entityName;
                    final ModelEntity entity = getModelEntity(_entityName);
                    final String packageName = entity.getPackageName();
                })
                .filter(tuple -> matchesPackageFilter(packageFilterSet, tuple.packageName))
                .filter(tuple -> matchesEntityFilter(entityFilterSet, tuple.entityName))
                .collect(Collectors.groupingBy(tuple -> tuple.packageName,
                        Collectors.mapping(tuple -> tuple.entityName,
                                Collectors.toCollection(TreeSet::new))));
    }

    public Map<String, TreeSet<String>> evenMoreImprovedGetEntitiesByPackage(Set<String> packageFilterSet, Set<String> entityFilterSet) {
        var tuples = this.getEntityNames().stream()
                .map(_entityName -> new Object() {
                    final String entityName = _entityName;
                    final ModelEntity entity = getModelEntity(_entityName);
                    final String packageName = entity.getPackageName();
                });
        var filteredTuples = tuples
                .filter(tuple -> matchesPackageFilter(packageFilterSet, tuple.packageName))
                .filter(tuple -> matchesEntityFilter(entityFilterSet, tuple.entityName));
        return filteredTuples
                .collect(Collectors.groupingBy(tuple -> tuple.packageName,
                        Collectors.mapping(tuple -> tuple.entityName,
                                Collectors.toCollection(TreeSet::new))));
    }


    private boolean matchesPackageFilter(Set<String> packageFilter, String packageName) {
        return packageFilter.isEmpty() ||
                packageFilter.stream().anyMatch(packageName::contains);
    }

    private boolean matchesEntityFilter(Set<String> entityFilter, String entityName) {
        return entityFilter.isEmpty() ||
                entityFilter.stream().anyMatch(entityName::contains);
    }

    private static class ModelEntity {
        private final String mPackageName;
        private final String mEntityName;

        public ModelEntity(String packageName, String entityName) {
            mPackageName = packageName;
            mEntityName = entityName;
        }

        public String getPackageName() {
            return mPackageName;
        }

        public String getEntityName() {
            return mEntityName;
        }
    }
}
