package core;

import common.OutputMessages;
import entities.containers.ModuleContainer;
import entities.modules.Module;
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
     * Using reflection in the report command
     * - not allowed to add more public logic than the one provided by the interfaces!
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
        StringBuilder sb = new StringBuilder();

        long cryoReactorsCount = getCountOfReactorsByType(this.reactors, "CryoReactor");
        long heatReactorsCount = getCountOfReactorsByType(this.reactors, "HeatReactor");

        long energyModulesCount = getCountOfModulesByType(this.reactors, "EnergyModule");
        long absorbingModulesCount = getCountOfModulesByType(this.reactors, "AbsorbingModule");

        long totalEnergyOutput = sumTotalEnergyOutput(this.reactors);
        long totalHeatOutput = sumTotalHeatOutput(this.reactors);

        sb
                .append(String.format("Cryo Reactors: %d", cryoReactorsCount))
                .append(System.lineSeparator())
                .append(String.format("Heat Reactors: %d", heatReactorsCount))
                .append(System.lineSeparator())
                .append(String.format("Energy Modules: %d", energyModulesCount))
                .append(System.lineSeparator())
                .append(String.format("Absorbing Modules: %d", absorbingModulesCount))
                .append(System.lineSeparator())
                .append(String.format("Total Energy Output: %d", totalEnergyOutput))
                .append(System.lineSeparator())
                .append(String.format("Total Heat Absorbing: %d", totalHeatOutput));

        return sb.toString();
    }

    private long getCountOfReactorsByType(Map<Integer, Reactor> reactors, String className) {
        return this.reactors
                .values()
                .stream()
                .filter(r -> r.getClass().getSimpleName().equals(className))
                .count();
    }

    @SuppressWarnings("unchecked")
    private long getCountOfModulesByType(Map<Integer, Reactor> reactors, String className) {
        long modulesCount = 0;
        for (Reactor reactor : reactors.values()) {
            try {
                Field field = ReactorImpl.class.getDeclaredField("container");
                field.setAccessible(true);
                ModuleContainer container = (ModuleContainer) field.get(reactor);

                Method method = ModuleContainer.class.getDeclaredMethod("getAllRegisteredModules");
                method.setAccessible(true);

                List<Module> allModules = (List<Module>) method.invoke(container);
                modulesCount = allModules
                        .stream()
                        .filter(m -> m.getClass().getSimpleName().equals(className))
                        .count();

            } catch (NoSuchFieldException
                    | IllegalAccessException
                    | NoSuchMethodException
                    | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return modulesCount;
    }

    private long sumTotalEnergyOutput(Map<Integer, Reactor> reactors) {
        long sum = 0;
        for (Reactor reactor : reactors.values()) {
            sum += reactor.getTotalEnergyOutput();
        }
        return sum;
    }

    private long sumTotalHeatOutput(Map<Integer, Reactor> reactors) {
        long sum = 0;
        for (Reactor reactor : reactors.values()) {
            sum += reactor.getTotalHeatAbsorbing();
        }
        return sum;
    }
}
