/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class Guide extends Thread {
    /** The original waiting location of the guide. */
    private Location originStation;

    /** The location that the guide escorts groups to. */
    private Location targetStation;

    // constructor for creating a new guide
    public Guide(Location originStation, Location targetStation) {
        this.originStation = originStation;
        this.targetStation = targetStation;
    }

    /** Picks up and escorts groups from one location to the next. */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // try to escort the origin station's group to the next location
                Group leavingGroup = originStation.escortOutOf();

                // escorting group to the next room...
                sleep(Params.WALKING_TIME);

                targetStation.escortInto(leavingGroup);

                // walking back to original waiting station
                sleep(Params.WALKING_TIME);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
