package com.company.controller;

import com.company.dao.Dao;
import com.company.dao.DaoImpl;
import com.company.entity.AbsEntity;
import com.company.entity.Problem;
import com.company.entity.Route;
import com.company.entity.Solution;
import com.company.util.PathFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Controller {

    private static final Dao dao = new DaoImpl();

    private static final Scanner in = new Scanner(System.in);

    public void run(){
        printLocations();
        printRoutes();
        printProblems();
        printSolutions();

        resolveProblems();

        pointer:
        while (true){
            System.out.println("1 - create problem");
            System.out.println("2 - see all solutions");
            System.out.println("0 - exit");

            switch (in.nextLine()){
                case "1":
                    System.out.println("Enter from id");
                    int from = Integer.parseInt(in.nextLine());
                    System.out.println("Enter to id");
                    int to = Integer.parseInt(in.nextLine());

                    Problem problem = new Problem();
                    problem.setFromId(from);
                    problem.setToId(to);

                    dao.createProblem(problem);
                    break;
                case "2":
                    resolveProblems();
                    printSolutions();
                    break;
                case "0":
                    break pointer;
                default:
                    System.out.println("Unknown command");
            }
        }

        resolveProblems();

        printSolutions();
    }


    private static void resolveProblems(){
        List<Problem> problems = dao.getProblems();
        List<Solution> solutions = dao.getSolutions();

        List<Problem> unresolvedProblems = new ArrayList<>();

        pointer:
        for (Problem p : problems){
            for (Solution s : solutions){
                if (s.getId() == p.getId()){
                    continue pointer;
                }
            }
            unresolvedProblems.add(p);
        }

        for (Problem p : unresolvedProblems){
            Solution solution = new Solution();
            solution.setId(p.getId());
            solution.setCost(PathFinder.findPath(creteGraph(),p.getFromId() - 1,p.getToId() - 1));
            dao.createSolution(solution);
        }
    }
    private static int[][] creteGraph(){
        int size = dao.getLocations().size();
        int[][] graph = new int[size][size];

        List<Route> routs = dao.getRouts();

        for (Route r : routs){
            graph[r.getFromId() - 1][r.getToId() - 1] = r.getCost();
        }

        return graph;
    }

    private static void printLocations(){
        System.out.println("Locations:");
        printAllEntities(dao.getLocations());
        System.out.println();
    }

    private static void printProblems(){
        System.out.println("Problems:");
        printAllEntities(dao.getProblems());
        System.out.println();
    }

    private static void printRoutes(){
        System.out.println("Routes:");
        printAllEntities(dao.getRouts());
        System.out.println();
    }

    private static void printSolutions(){
        System.out.println("Solutions:");
        List<Problem> problems = dao.getProblems();
        List<Solution> solutions = dao.getSolutions();
        for (Solution s : solutions){
            for (Problem p : problems){
                if(s.getId() == p.getId()){
                    System.out.printf("from %d to %d = %d\n", p.getFromId(), p.getToId(), s.getCost());
                }
            }
        }
        System.out.println();
    }

    private static void printAllEntities(List<? extends AbsEntity> list){
        for (AbsEntity a : list){
            System.out.println(a);
        }
    }
}
