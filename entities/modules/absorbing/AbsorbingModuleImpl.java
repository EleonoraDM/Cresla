package entities.modules.absorbing;

public abstract class AbsorbingModuleImpl implements AbsorbingModule {
    private int id;
    private int heatAbsorbing;

    protected AbsorbingModuleImpl(int id, int heatAbsorbing) {
        this.id = id;
        this.heatAbsorbing = heatAbsorbing;
    }

    @Override
    public int getHeatAbsorbing() {
        return this.heatAbsorbing;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
