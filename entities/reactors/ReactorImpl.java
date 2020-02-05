package entities.reactors;

import entities.containers.ModuleContainer;
import entities.modules.absorbing.AbsorbingModule;
import entities.modules.energy.EnergyModule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class ReactorImpl implements Reactor {
    private int id;
    private ModuleContainer container;

    protected ReactorImpl(int id, int modulesCapacity) {
        this.id = id;
        this.container = new ModuleContainer(modulesCapacity);
    }

    @Override
    public int getModuleCount() {
        int moduleCount = 0;
        Method method = null;
        try {
            method = this.container.getClass().getDeclaredMethod("getCurrentCapacityLevel");
            method.setAccessible(true);
            moduleCount = ((int) method.invoke(this.container));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return moduleCount;
    }

    @Override
    public long getTotalEnergyOutput() {
        long totalEnergy = 0;

        if (this instanceof CryoReactor) {
            int cryoProductionIndex = ((CryoReactor) this).cryoProductionIndex;
            totalEnergy = this.container.getTotalEnergyOutput() * cryoProductionIndex;
        } else {
            totalEnergy = this.container.getTotalEnergyOutput();
        }
        //FIXME Should this logic be here?
        if (totalEnergy > this.getTotalHeatAbsorbing()) {
            totalEnergy = 0;
        }
        return totalEnergy;
    }

    @Override
    public long getTotalHeatAbsorbing() {
        long totalHeat = 0;

        if (this instanceof HeatReactor) {
            int heatReductionIndex = ((HeatReactor) this).heatReductionIndex;
            totalHeat = this.container.getTotalHeatAbsorbing() + heatReductionIndex;
        } else {
            totalHeat = this.container.getTotalHeatAbsorbing();
        }
        return totalHeat;
    }

    @Override
    public void addEnergyModule(EnergyModule energyModule) {
        this.container.addEnergyModule(energyModule);
    }

    @Override
    public void addAbsorbingModule(AbsorbingModule absorbingModule) {
        this.container.addAbsorbingModule(absorbingModule);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(String.format("%s - %d", this.getClass().getSimpleName(), this.getId()))
                .append(System.lineSeparator())
                .append(String.format("Energy Output: %d", this.getTotalEnergyOutput()))
                .append(System.lineSeparator())
                .append(String.format("Heat Absorbing: %d", this.getTotalHeatAbsorbing()))
                .append(System.lineSeparator())
                .append(String.format("Modules: %d", this.getModuleCount()));
        return sb.toString();
    }
}
