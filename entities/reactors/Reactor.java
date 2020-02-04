package entities.reactors;

import entities.modules.absorbing.AbsorbingModule;
import entities.modules.energy.EnergyModule;
import interfaces.Identifiable;

import java.lang.reflect.InvocationTargetException;

public interface Reactor extends Identifiable {

    long getTotalEnergyOutput();

    long getTotalHeatAbsorbing();

    int getModuleCount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    void addEnergyModule(EnergyModule energyModule);

    void addAbsorbingModule(AbsorbingModule absorbingModule);
}
