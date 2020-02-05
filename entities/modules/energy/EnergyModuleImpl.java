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

   /* {moduleType} Module – {moduleId}
    {additionalParam}: {additionalParamValue}*/

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb
                .append(String.format("%s Module – %d", this.getClass().getSimpleName(), this.getId()))
                .append(System.lineSeparator())
                .append(String.format("Energy Output: %d", this.getEnergyOutput()));

        return sb.toString();
    }
}
