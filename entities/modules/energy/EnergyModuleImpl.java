package entities.modules.energy;

public abstract class EnergyModuleImpl implements EnergyModule {
    private int id;
    private int energyOutput;

    protected EnergyModuleImpl(int id, int energyOutput) {
        this.id = ++id;
        this.energyOutput = energyOutput;
    }

    @Override
    public int getEnergyOutput() {
        return this.energyOutput;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
