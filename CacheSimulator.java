import java.io.*;

public class CacheSimulator {
    public static Cache cache;
    public static void main(String[] args) {

        //command-line args
        int num_sets = Integer.parseInt(args[0]);
        int num_blocks = Integer.parseInt(args[1]);
        int num_addresses = Integer.parseInt(args[2]);
        int write_allocate = Integer.parseInt(args[3]);
        int write_through = Integer.parseInt(args[4]);
        int least_recently_used = Integer.parseInt(args[5]);
        String fileName = args[6];        

        cache = new Cache (num_sets, num_blocks, num_addresses);
        cache.init();
        String line = null;

        try {
            FileReader fileReader = 
                new FileReader(fileName);

            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                simulate(line);
            }   

            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
        }
    }

    public static void simulate (String line) {
        String[] parts = line.split(" ");
        String instr;
        String address;
        if (parts.length >= 2) {
            //write
            if (parts[0].compareTo("s") == 0) {

            } else if (parts[0].compareTo("l") == 0) { //read
                cache.read(Integer.parseInt(parts[1]));

            } else {
                System.out.println("Error! First argument must be s or l.");
            }
        }
        else {
            System.out.println("Error! Too few arguments on line.");
        }
    }
}

