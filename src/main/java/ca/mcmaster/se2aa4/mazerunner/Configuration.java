package ca.mcmaster.se2aa4.mazerunner;
import org.apache.commons.cli.*;


public record Configuration(String inputFilePath, String userPath){

    public static Configuration load(String[] args){
        Options options = new Options();
        options.addOption("i", true, "input maze filepath");
        options.addOption("p", true, "user path guess");

        String userPath= "";
        String inputFilePath="";

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            inputFilePath = cmd.getOptionValue("righthand");
            
            //checks if user uses -p flag
            if(cmd.hasOption("p")) {
                    userPath = cmd.getOptionValue("p");
            }
            return new Configuration(inputFilePath, userPath);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        } 
        

    }
}