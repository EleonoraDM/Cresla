package entities.containers;

import entities.modules.absorbing.AbsorbingModule;
import entities.modules.energy.EnergyModule;

public interface Container {

    long getTotalEnergyOutput();

    long getTotalHeatAbsorbing();

    void addEnergyModule(EnergyModule energyModule);

    void addAbsorbingModule(AbsorbingModule absorbingModule);
}
