package core;

import common.OutputMessages;
import entities.containers.ModuleContainer;
import entities.modules.absorbing.AbsorbingModule;
import entities.modules.absorbing.CooldownSystem;
import entities.modules.absorbing.HeatProcessor;
import entities.modules.energy.CryogenRod;
import entities.modules.energy.EnergyModule;
import entities.reactors.CryoReactor;
import entities.reactors.HeatReactor;
import entities.reactors.Reactor;
import entities.reactors.ReactorImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ManagerImpl implements Manager {
    private Map<Integer, Reactor> reactors;
    private int idCounter;

    public ManagerImpl() {
        this.reactors = new LinkedHashMap<>();
    }

    /**
     * There will be no non-existent ids or types in the input.
     */
    @Override
    public String reactorCommand(List<String> arguments) {
        String result = null;

        Reactor reactor = null;

        String type = arguments.get(0);
        int addParam = Integer.parseInt(arguments.get(1));
        int moduleCapacity = Integer.parseInt(arguments.get(2));

        if (type.equals("Cryo")) {
            reactor = new CryoReactor(++this.idCounter, moduleCapacity, addParam);
        } else {
            reactor = new HeatReactor(++this.idCounter, moduleCapacity, addParam);
        }
        this.reactors.put(reactor.getId(), reactor);

        return String.format(OutputMessages.CREATE_REACTOR, type, this.idCounter);
    }

    @Override
    public String moduleCommand(List<String> arguments) {
        String result = null;

        int reactorId = Integer.parseInt(arguments.get(0));
        String moduleType = arguments.get(1);
        int addParam = Integer.parseInt(arguments.get(2));

        Reactor reactor = this.reactors.get(reactorId);

        switch (moduleType) {
            case "CryogenRod": {
                EnergyModule module = new CryogenRod(++this.idCounter, addParam);
                reactor.addEnergyModule(module);
                break;
            }
            case "HeatProcessor": {
                AbsorbingModule module = new HeatProcessor(++this.idCounter, addParam);
                reactor.addAbsorbingModule(module);
                break;
            }
            default: {
                AbsorbingModule module = new CooldownSystem(++this.idCounter, addParam);
                reactor.addAbsorbingModule(module);
                break;
            }
        }
        return String.format(OutputMessages.ADD_MODULE, moduleType, this.idCounter, reactorId);
    }

    @Override
    public String reportCommand(List<String> arguments) {
        int id = Integer.parseInt(arguments.get(0));

        String result = null;

        if (this.reactors.containsKey(id)) {
            Reactor reactor = this.reactors.get(id);
            result = reactor.toString();
        } else {
            for (Reactor reactor : this.reactors.values()) {
                try {
                    Field field = ReactorImpl.class.getDeclaredField("container");
                    field.setAccessible(true);

                    ModuleContainer container = (ModuleContainer) field.get(reactor);
                    Method searchModuleById =
                            ModuleContainer.class.getDeclaredMethod("searchModuleById", int.class);
                    searchModuleById.setAccessible(true);
                    Object module = searchModuleById.invoke(container, id);
                    result = module.toString();

                } catch (NoSuchFieldException
                        | IllegalAccessException
                        | NoSuchMethodException
                        | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public String exitCommand(List<String> arguments) {
        return null;
    }
}
