/**
 * TODO: add top-level docs
 * Top-level location type that the foyer and rooms extend.
 * Implements the "monitor" construct by hiding its occupied status and exposing
 * only synchronised methods.
 * 
 * @author Nico Dinata (770318)
 */
public class Location {
    /** Index of the Location — `null` if this Location is the Foyer. */
    private Integer index;

    /** The Group currently occupying this Location (can be null). */
    private Group occupantGroup;

    // constructor for creating and initialising a new empty Location
    public Location(Integer index) {
        this.index = index;
        this.occupantGroup = null;
    }

    /**
     * TODO
     * 
     * @param group
     * @throws InterruptedException
     */
    public synchronized void enter(Group group) throws InterruptedException {
        while (this.occupantGroup != null) {
            wait();
        }
        this.occupantGroup = group;
        System.out.println(group + " enters " + this.roomName());
        notifyAll();
    }

    /**
     * TODO
     * 
     * @return
     * @throws InterruptedException
     */
    public synchronized Group leave() throws InterruptedException {
        while (this.occupantGroup == null) {
            wait();
        }
        Group leavingGroup = this.occupantGroup;
        this.occupantGroup = null;
        System.out.println(leavingGroup + " leaves " + this.roomName());
        notifyAll();
        return leavingGroup;
    }

    /** Returns whether this location is currently occupied by a Group. */
    public synchronized boolean hasOccupant() {
        return this.occupantGroup != null;
    }

    /** Returns the string representation of this Location's name. */
    private String roomName() {
        return (this.index == null) ? "the foyer" : ("room " + this.index);
    }
}
