package entities.containers;

import entities.modules.absorbing.AbsorbingModule;
import entities.modules.energy.EnergyModule;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModuleContainer implements Container {

    private int moduleCapacity;
    private Map<Integer, EnergyModule> energyModules;
    private Map<Integer, AbsorbingModule> absorbingModules;

    public ModuleContainer(int moduleCapacity) {
        this.moduleCapacity = moduleCapacity;
        this.energyModules = new LinkedHashMap<>();
        this.absorbingModules = new LinkedHashMap<>();
    }

    private int getCurrentCapacityLevel() {
        int modulesCount = this.energyModules.size() + this.absorbingModules.size();
        return modulesCount;
    }

    public void addEnergyModule(EnergyModule energyModule) {
        if (energyModule == null) {
            throw new IllegalArgumentException();
        }
        if (this.getCurrentCapacityLevel() == this.moduleCapacity) {
            this.removeOldestModule();
        }
        this.energyModules.put(energyModule.getId(), energyModule);
    }

    public void addAbsorbingModule(AbsorbingModule absorbingModule) {
        if (absorbingModule == null) {
            throw new IllegalArgumentException();
        }

        if (this.getCurrentCapacityLevel() == this.moduleCapacity) {
            this.removeOldestModule();
        }

        this.absorbingModules.put(absorbingModule.getId(), absorbingModule);
    }

    @Override
    public long getTotalEnergyOutput() {
        return this.energyModules
                .values()
                .stream()
                .mapToInt(EnergyModule::getEnergyOutput)
                .sum();
    }

    @Override
    public long getTotalHeatAbsorbing() {
        return this.absorbingModules
                .values()
                .stream()
                .mapToInt(AbsorbingModule::getHeatAbsorbing)
                .sum();
    }

    private void removeOldestModule() {
        Integer oldestEnergyId = this.energyModules.keySet()
                .stream()
                .min(Integer::compareTo)
                .orElse(null);
        Integer oldestAbsorbId = this.absorbingModules.keySet()
                .stream()
                .min(Integer::compareTo)
                .orElse(null);

        if (oldestAbsorbId != null && oldestEnergyId != null){
            if (oldestAbsorbId < oldestEnergyId){
                this.absorbingModules.remove(oldestAbsorbId);
            }else {
                this.energyModules.remove(oldestEnergyId);
            }
        }
    }
}