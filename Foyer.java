/**
 * Represents the foyer of the museum, which handles groups entering and
 * leaving the museum and also manages the movements of a security guard. It
 * also implements the `Location` interface which specifies how groups can be
 * escorted into and out of it.
 * 
 * @author Nico Dinata (770318)
 */
public class Foyer implements Location {
    /** Whether the security guard is inside the foyer. */
    private boolean isGuardInside;

    /** The group outside the museum that is waiting to be escorted in. */
    private Group enteringGroup;

    /** The group outside the museum waiting to... be "consumed". */
    private Group checkedOutGroup;

    /**
     * The group that has passed security checks and is waiting to enter the
     * first room.
     */
    private Group checkedInGroup;

    /**
     * The group that has just arrived from the last room and is waiting to get
     * their bags back from the security guard.
     */
    private Group departingGroup;

    /** Constructor for creating an empty foyer with the guard inside. */
    public Foyer() {
        this.isGuardInside = true;
        this.enteringGroup = null;
        this.checkedOutGroup = null;
        this.checkedInGroup = null;
        this.departingGroup = null;
    }

    /**
     * Block while the foyer is occupied (by a group) or while the guard is
     * outside. Otherwise, the group that just left the last room enters the
     * foyer.
     */
    @Override
    public synchronized void escortInto(Group group)
            throws InterruptedException {
        while (this.checkedInGroup != null ||
                this.departingGroup != null ||
                !this.isGuardInside) {
            wait();
        }
        this.departingGroup = group;
        System.out.println(group + " enters the foyer");
        notifyAll();
    }

    /**
     * Block while there is no group to be escorted into the first room,
     * otherwise we escort the checked-in group out of the foyer towards the
     * first room.
     */
    @Override
    public synchronized Group escortOutOf() throws InterruptedException {
        while (this.checkedInGroup == null) {
            wait();
        }

        // the checked-in group leaves the foyer
        Group movingOnGroup = this.checkedInGroup;
        this.checkedInGroup = null;
        System.out.println(movingOnGroup + " leaves the foyer");
        notifyAll();
        return movingOnGroup;
    }

    /**
     * Block while the foyer is occupied or while the guard is inside.
     * Otherwise, consider that the group waiting outside is ready to be
     * escorted into the museum (is ready to enter the museum).
     */
    public synchronized void arriveAtMuseum(Group group)
            throws InterruptedException {
        while (this.checkedInGroup != null ||
                this.departingGroup != null ||
                this.isGuardInside) {
            this.enteringGroup = group;
            wait();
        }
        this.enteringGroup = group;
    }

    /**
     * Block while there are no groups outside the museum ready to leave or
     * while the guard is inside. Otherwise, consider that the departing group
     * outside has been "consumed" / has departed from the museum.
     */
    public synchronized void departFromMuseum() throws InterruptedException {
        while (this.checkedOutGroup == null || this.isGuardInside) {
            wait();
        }

        // the departing group gets "consumed"
        System.out.println(this.checkedOutGroup + " departs from the museum");
        this.checkedOutGroup = null;
    }

    /** Manage the security guard to go check inside the museum. */
    public synchronized void guardCheckInside() {
        this.isGuardInside = true;
        System.out.println("security guard goes in");
        notifyAll();
    }

    /** Manage the security guard to go check outside the museum. */
    public synchronized void guardCheckOutside() {
        this.isGuardInside = false;
        System.out.println("security guard goes out");
        notifyAll();
    }

    /** Return the group waiting to be escorted to leave the museum. */
    public synchronized Group groupWaitingInside() {
        return this.departingGroup;
    }

    /** Return the group waiting to be escorted in to enter the museum. */
    public synchronized Group groupWaitingOutside() {
        return this.enteringGroup;
    }

    /**
     * Manage the security guard to escort the group waiting outside into the
     * museum and through the security check, leaving the group ready to be
     * escorted into the first room.
     */
    public synchronized void escortThroughSecurity(Group group) {
        this.isGuardInside = true;

        // the group waiting outside is now checked in (passed security checks)
        this.enteringGroup = null;
        this.checkedInGroup = group;
        System.out.println(this.checkedInGroup + " arrives at the museum");
        notifyAll();
    }

    /**
     * Manage the security guard to escort the departing group outside the
     * museum, where they are ready to be "consumed" out of the system.
     */
    public synchronized void sendOffHome(Group group) {
        this.isGuardInside = false;

        // the group waiting inside the museum has left the building
        this.departingGroup = null;
        this.checkedOutGroup = group;
        notifyAll();
    }
}
