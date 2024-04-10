package ca.mcmaster.se2aa4.mazerunner;
import java.util.*;
public class Path {
    private String inputPath;

    public Path(String inputPath){
        this.inputPath= inputPath;
    }
    

    //returns path in canonical form
    //e.g. "2F2R" --> "FFRR"
    public String expandPath(){
        //strips whitespace from input path
        String path = inputPath.replaceAll("\\s", "");
        String[] path_arr= path.split("");
        StringBuilder expanded = new StringBuilder();

        for (int i = 0; i < path_arr.length; i++) {
            if (!Character.isDigit(path_arr[i].charAt(0))) {
                expanded.append(path_arr[i]);
            } else {
                int count = 0;
                int digit = 0;
                do {
                    count *= (int) Math.pow(10, digit++);
                    count += Character.getNumericValue(path.charAt(i++));
                } while (Character.isDigit(path_arr[i].charAt(0)));

                for(int j=0;j<count;j++){
                    expanded.append(path_arr[i]);
                }
            }
        }
        return expanded.toString();
    }



    
    //outputs path in the factorized form "3F2R2F2LF"
    //"RRFF" --> "2R2F"
    public String getFactorizedPath( ){
        String path = inputPath;
        String[] path_arr= path.split("");
        StringBuilder factorizedPath= new StringBuilder();

        if(path_arr.length==0){
            return "";
        }

        int repetition=1;
        String track= path_arr[0];
        
        for(int i=1;i<path_arr.length;i++){
            if (path_arr[i].equals(track)){
                //increases count each time current move is repeated
                repetition+=1;
            }
            else{
                if(repetition>1){
                    factorizedPath.append(String.valueOf(repetition));
                    factorizedPath.append(track);
                }
                else{
                    factorizedPath.append(track);
                }
                //resets move count
                track= path_arr[i];
                repetition=1;
                factorizedPath.append(" ");

            }   
        }

        //if move is only repeated once, only the move is printed. repetition variable is ignored as "1F" is less factorized than "F"
        if(repetition>1){
            factorizedPath.append(repetition);
            factorizedPath.append(path_arr[path_arr.length - 1]);
        }
        else{
            factorizedPath.append(path_arr[path_arr.length - 1]);
        }
        String finalPath= factorizedPath.toString();
        return finalPath;
    }

}

