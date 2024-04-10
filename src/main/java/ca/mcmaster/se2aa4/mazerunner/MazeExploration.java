package ca.mcmaster.se2aa4.mazerunner;
import java.util.*;
abstract class MazeExploration{

    abstract String solve();

    //uses findCanonicalPath method to find maze path, then outputs path in the form "3F2R2F2LF"
    public String findFactorizedPath(){
        String path = solve();
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
        
        return factorizedPath.toString();
    }


    public List<String> expandPath(String factorizedPath){
        //strips whitespace from user input path
        String path = factorizedPath.replaceAll("\\s", "");
        String[] path_arr= path.split("");
        List<String> expanded = new ArrayList<String>();

        for (int i = 0; i < path_arr.length; i++) {
            if (!Character.isDigit(path_arr[i].charAt(0))) {
                expanded.add(path_arr[i]);
            } else {
                int count = 0;
                int digit = 0;
                do {
                    count *= (int) Math.pow(10, digit++);
                    count += Character.getNumericValue(path.charAt(i++));
                } while (Character.isDigit(path_arr[i].charAt(0)));

                for(int j=0;j<count;j++){
                    expanded.add(path_arr[i]);
                }
            }
        }
        return expanded;
    }
}