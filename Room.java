/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class Room implements Location {
    /** Index of the room. */
    private int index;

    /** The group currently occupying this room (can be null). */
    private Group occupantGroup;

    // constructor for creating a new empty room of the given index
    public Room(int index) {
        this.index = index;
        this.occupantGroup = null;
    }

    @Override
    public synchronized void escortInto(Group group) throws InterruptedException {
        while (this.occupantGroup != null) {
            wait();
        }
        this.occupantGroup = group;
        System.out.println(group + " enters room " + this.index);
        notifyAll();
    }

    @Override
    public synchronized Group escortOutOf() throws InterruptedException {
        while (this.occupantGroup == null) {
            wait();
        }
        Group leavingGroup = this.occupantGroup;
        this.occupantGroup = null;
        System.out.println(leavingGroup + " leaves room " + this.index);
        notifyAll();
        return leavingGroup;
    }
}
