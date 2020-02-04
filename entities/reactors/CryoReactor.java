package entities.reactors;

public class CryoReactor extends ReactorImpl {
    protected int cryoProductionIndex;


    public CryoReactor(int id, int modulesCapacity, int cryoProductionIndex) {
        super(id, modulesCapacity);
        this.cryoProductionIndex = cryoProductionIndex;
    }

}
