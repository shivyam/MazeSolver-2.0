package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try{
            cmd = parser.parse(getParserOptions(), args);
            String inputFilePath = cmd.getOptionValue('i');
            Maze mazeTest= new Maze(inputFilePath);

            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                String userPath = cmd.getOptionValue("p");
                PathValidator check= new PathValidator(mazeTest, userPath);
                logger.info(check.checkPath());
                
            } else {
                String method = cmd.getOptionValue("method", "righthand");
                String path= solveMaze(method, mazeTest);
                System.out.println(path);
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
        
    }


    private static String solveMaze(String method, Maze maze) throws Exception {
        logger.info("Computing path");
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                RightHandSolver path= new RightHandSolver(maze);
                return path.findFactorizedPath();
            }
            case "bfs" -> {
                logger.debug("BFS graph algorithm chosen.");
                GraphAlgorithmSolver path= new GraphAlgorithmSolver(maze);
                return path.findFactorizedPath();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        
       
    }



    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));

        return options;
    }
}