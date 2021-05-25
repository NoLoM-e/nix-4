package com.company.dao;

import com.company.controller.Controller;
import com.company.entity.Location;
import com.company.entity.Problem;
import com.company.entity.Route;
import com.company.entity.Solution;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DaoImpl implements Dao{

    private final Properties props = loadProperties();

    private final String url = props.getProperty("url");

    private final String name = props.getProperty("name");

    private final String pass = props.getProperty("password");

    @Override
    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM locations");

                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");

                    Location location = new Location();
                    location.setId(id);
                    location.setName(name);

                    locations.add(location);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return locations;
    }

    @Override
    public List<Route> getRouts() {
        List<Route> routes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM routes");

                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int fromId = resultSet.getInt("from_id");
                    int toId = resultSet.getInt("to_id");
                    int cost = resultSet.getInt("cost");

                    Route route = new Route();
                    route.setId(id);
                    route.setFromId(fromId);
                    route.setToId(toId);
                    route.setCost(cost);

                    routes.add(route);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return routes;
    }

    @Override
    public List<Problem> getProblems() {
        List<Problem> problems = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM problems");

                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int fromId = resultSet.getInt("from_id");
                    int toId = resultSet.getInt("to_id");

                    Problem problem = new Problem();
                    problem.setId(id);
                    problem.setFromId(fromId);
                    problem.setToId(toId);

                    problems.add(problem);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return problems;
    }

    @Override
    public List<Solution> getSolutions() {
        List<Solution> solutions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (Statement statement = connection.createStatement()){
                ResultSet resultSet = statement.executeQuery("SELECT * FROM solutions");

                while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    int cost = resultSet.getInt("cost");

                    Solution solution = new Solution();
                    solution.setId(id);
                    solution.setCost(cost);

                    solutions.add(solution);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return solutions;
    }

    @Override
    public void createSolution(Solution solution) {
        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO solutions (id, cost) values (?, ?)")){
                statement.setInt(1, solution.getId());
                statement.setInt(2, solution.getCost());

                int rows = statement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void createProblem(Problem problem) {
        try (Connection connection = DriverManager.getConnection(url, name, pass)){
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO problems (from_id, to_id) values (?, ?)")){
                statement.setInt(1, problem.getFromId());
                statement.setInt(2, problem.getToId());

                int rows = statement.executeUpdate();
            } catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static Properties loadProperties() {

        Properties props = new Properties();

        try(InputStream input = Controller.class.getResourceAsStream("/jdbc.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        return props;
    }
}
