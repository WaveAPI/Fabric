package org.waveapi.utils;

import org.waveapi.Dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyResolver {
    public static List<String> resolveDependencies(Map<String, List<Dependency>> deps) {
        List<String> resolvedOrder = new ArrayList<>();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Boolean> recursionStack = new HashMap<>();

        for (String id : deps.keySet()) {
            if (!visited.containsKey(id)) {
                if (hasCircularDependency(id, deps, visited, recursionStack)) {
                    throw new RuntimeException("Circular dependency detected!");
                }
                visit(id, deps, visited, resolvedOrder);
            }
        }

        return resolvedOrder;
    }

    private static boolean hasCircularDependency(String id, Map<String, List<Dependency>> deps,
                                                 Map<String, Boolean> visited, Map<String, Boolean> recursionStack) {
        visited.put(id, false);
        recursionStack.put(id, true);

        List<Dependency> dependencies = deps.get(id);
        if (dependencies != null) {
            for (Dependency dependency : dependencies) {
                String dependencyId = dependency.getLoadBefore();
                if (!visited.containsKey(dependencyId)) {
                    if (hasCircularDependency(dependencyId, deps, visited, recursionStack)) {
                        return true;
                    }
                } else if (recursionStack.get(dependencyId)) {
                    return true;
                }
            }
        }

        recursionStack.put(id, false);
        return false;
    }

    private static void visit(String id, Map<String, List<Dependency>> deps, Map<String, Boolean> visited,
                              List<String> resolvedOrder) {
        visited.put(id, true);

        List<Dependency> dependencies = deps.get(id);
        if (dependencies != null) {
            for (Dependency dependency : dependencies) {
                String dependencyId = dependency.getLoadBefore();
                if (!visited.containsKey(dependencyId)) {
                    visit(dependencyId, deps, visited, resolvedOrder);
                }
            }
        }

        resolvedOrder.add(id);
    }
}
