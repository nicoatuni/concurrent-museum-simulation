/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class Foyer implements Location {
    private boolean isGuardInside;
    private Group enteringGroup;
    private Group checkedOutGroup;
    private Group checkedInGroup;
    private Group departingGroup;

    public Foyer() {
        this.isGuardInside = true;
        this.enteringGroup = null;
        this.checkedOutGroup = null;
        this.checkedInGroup = null;
        this.departingGroup = null;
    }

    @Override
    public synchronized void escortInto(Group group) throws InterruptedException {
        while (this.checkedInGroup != null || this.departingGroup != null || !this.isGuardInside) {
            wait();
        }
        this.departingGroup = group;

        System.out.println(group + " enters the foyer");
        notifyAll();
    }

    @Override
    public synchronized Group escortOutOf() throws InterruptedException {
        while (this.checkedInGroup == null) {
            wait();
        }
        Group movingOnGroup = this.checkedInGroup;
        this.checkedInGroup = null;

        System.out.println(movingOnGroup + " leaves the foyer");
        notifyAll();
        return movingOnGroup;
    }

    public synchronized void guardCheckInside() {
        this.isGuardInside = true;
        System.out.println("security guard goes in");
        notifyAll();
    }

    public synchronized Group groupWaitingInside() {
        return this.departingGroup;
    }

    public synchronized void guardCheckOutside() {
        this.isGuardInside = false;
        System.out.println("security guard goes out");
        notifyAll();
    }

    public synchronized Group groupWaitingOutside() {
        return this.enteringGroup;
    }

    public synchronized void escortThroughSecurity(Group group) {
        this.isGuardInside = true;
        this.enteringGroup = null;
        this.checkedInGroup = group;
        System.out.println(this.checkedInGroup + " arrives at the museum");
        notifyAll();
    }

    public synchronized void sendOffHome(Group group) {
        this.isGuardInside = false;
        this.departingGroup = null;
        this.checkedOutGroup = group;
        notifyAll();
    }

    public synchronized void arriveAtMuseum(Group group) throws InterruptedException {
        while (this.checkedInGroup != null || this.departingGroup != null || this.isGuardInside) {
            this.enteringGroup = group;
            wait();
        }
        // System.out.println(this.checkedInGroup + " arrives at the museum");
    }

    public synchronized void departFromMuseum() throws InterruptedException {
        while (this.checkedOutGroup == null || this.isGuardInside) {
            wait();
        }
        System.out.println(this.checkedOutGroup + " departs from the museum");
        this.checkedOutGroup = null;
    }
}
