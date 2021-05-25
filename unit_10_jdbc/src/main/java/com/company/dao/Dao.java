package com.company.dao;

import com.company.entity.Location;
import com.company.entity.Problem;
import com.company.entity.Route;
import com.company.entity.Solution;

import java.util.List;

public interface Dao {

    List<Location> getLocations();
    List<Route> getRouts();
    List<Problem> getProblems();
    List<Solution> getSolutions();
    void createSolution(Solution solution);
    void createProblem(Problem problem);
}
