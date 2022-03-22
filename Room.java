/**
 * Represents each room in the museum, which is identifiable by its index and
 * can be occupied by at most a single group. It implements the `Location`
 * interface, which specifies how groups are escorted into and out of it.
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

    /**
     * Block while the room is currently occupied, otherwise we escort the new
     * group into it.
     */
    @Override
    public synchronized void escortInto(Group group)
            throws InterruptedException {
        // if this room is currently occupied, don't escort more groups into it
        while (this.occupantGroup != null) {
            wait();
        }

        // the room is empty and a new group can be escorted into it
        this.occupantGroup = group;
        System.out.println(group + " enters room " + this.index);
        notifyAll();
    }

    /**
     * Block while the room is empty, otherwise we escort the occupying group
     * out of it.
     */
    @Override
    public synchronized Group escortOutOf() throws InterruptedException {
        // wait until the room is actually occupied
        while (this.occupantGroup == null) {
            wait();
        }

        // the room is occupied, let's escort the group out of this room
        Group leavingGroup = this.occupantGroup;
        this.occupantGroup = null;
        System.out.println(leavingGroup + " leaves room " + this.index);
        notifyAll();
        return leavingGroup;
    }
}
