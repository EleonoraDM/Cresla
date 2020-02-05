import core.Manager;
import core.ManagerImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Manager manager = new ManagerImpl();
        List<String> arguments1 = new ArrayList<>(Arrays.asList("Cryo", "10", "10"));
        manager.reactorCommand(arguments1);

        List<String> arguments2 = new ArrayList<>(Arrays.asList("1", "Cryogenrod", "100"));
        manager.moduleCommand(arguments2);
        List<String> arguments3 = new ArrayList<>(Arrays.asList("1", "Cryogenrod", "100"));
        manager.moduleCommand(arguments3);


        List<String> arguments4 = new ArrayList<>(Arrays.asList("2", "2"));
        String result = manager.reportCommand(arguments4);


        System.out.println(result);

    }
}
