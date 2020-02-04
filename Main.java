import entities.modules.absorbing.AbsorbingModule;
import entities.modules.absorbing.CooldownSystem;
import entities.reactors.CryoReactor;
import entities.reactors.Reactor;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        Reactor reactor = new CryoReactor(1, 10, 5);
        AbsorbingModule module = new CooldownSystem(0, 4);
        reactor.addAbsorbingModule(module);
        int moduleCount = 0;

        try {
            moduleCount = reactor.getModuleCount();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        System.out.println(moduleCount);

    }
}
