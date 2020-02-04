package entities.reactors;

public class HeatReactor extends ReactorImpl {
    protected int heatReductionIndex;


    public HeatReactor(int id, int modulesCapacity, int heatReductionIndex) {
        super(id, modulesCapacity);
        this.heatReductionIndex = heatReductionIndex;
    }
}
