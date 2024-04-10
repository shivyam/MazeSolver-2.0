package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.*;

public class Main {

    private static final Logger logger = LogManager.getLogger();
    private static long fileLoadTime;
    private static long regMethodRunTime;
    private static long baselineRunTime;
    private static String expandedRegPath;
    private static String expandedBaselinePath;



    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try{
            cmd = parser.parse(getParserOptions(), args);
            String inputFilePath = cmd.getOptionValue('i');
            long fileStartTime= System.currentTimeMillis();
            Maze mazeTest= new Maze(inputFilePath);
            long fileEndTime = System.currentTimeMillis();
            
            fileLoadTime= fileEndTime- fileStartTime;

            if(cmd.getOptionValue("baseline") != null){

                System.out.printf("Time spent loading the maze from the file: %.2f milliseconds", (double)fileLoadTime);
                System.out.println();

                String baselineMethod = cmd.getOptionValue("baseline");
                String regMethod = cmd.getOptionValue("method");

                solveMaze(regMethod,mazeTest, true, false);
                solveMaze(baselineMethod,mazeTest, false, true);

                System.out.printf("Time spent exploring the maze using the provide -method: %.2f milliseconds", (double)regMethodRunTime);
                System.out.println();

                System.out.printf("Time spent exploring the maze using the provide -baseline: %.2f milliseconds", (double)baselineRunTime);
                System.out.println();

                double speedup= expandedBaselinePath.length() / expandedRegPath.length();
                System.out.printf("Improvement on the path as a speedup: %.2f", speedup);
                System.out.println();

            }

            else if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                String userPath = cmd.getOptionValue("p");
                PathValidator check= new PathValidator(mazeTest, userPath);
                logger.info(check.checkPath());
            } 

            else {
                logger.info("Computing path");
                String method = cmd.getOptionValue("method", "righthand");
                String path= solveMaze(method, mazeTest,false,false);
                System.out.println(path);
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
        
    }


    private static String solveMaze(String method, Maze maze, boolean isRegMethod, boolean isBaselineMethod) throws Exception {
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                long righthandStartTime = System.currentTimeMillis();
                RightHandSolver path= new RightHandSolver(maze);
                Path finalPath= path.solve();
                long righthandEndTime = System.currentTimeMillis(); 

                if(isRegMethod){
                    regMethodRunTime= righthandEndTime- righthandStartTime;
                    expandedRegPath= finalPath.expandPath();
                }
                else if (isBaselineMethod){
                    baselineRunTime= righthandEndTime- righthandStartTime;
                    expandedBaselinePath= finalPath.expandPath();
                }

                return finalPath.getFactorizedPath();
            }
            case "bfs" -> {
                logger.debug("BFS graph algorithm chosen.");
                long bfsStartTime = System.currentTimeMillis();
                BreadthFirstSearch path= new BreadthFirstSearch(maze);
                Path finalPath= path.solve();
                long bfsEndTime = System.currentTimeMillis();

                if(isRegMethod){
                    regMethodRunTime= bfsEndTime- bfsStartTime;
                    expandedRegPath= finalPath.expandPath();
                }
                else if (isBaselineMethod){
                    baselineRunTime= bfsEndTime- bfsStartTime;
                    expandedBaselinePath= finalPath.expandPath();
                }

                return finalPath.getFactorizedPath();
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

        options.addOption(new Option("baseline", true, "Method to be compared with baseline method"));
        return options;
    }
}