/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class Foyer implements Location {
    private boolean isGuardInside;
    // private SecurityGuard securityGuard;
    // private Group occupantGroup;

    private Group groupWaitingOutside;
    private Group groupLeavingOutside;

    // testing only
    private Group enteringGroup;
    private Group departingGroup;
    // testing only

    public Foyer() {
        this.isGuardInside = true;
        // this.securityGuard = null;
        // this.occupantGroup = null;
        this.groupWaitingOutside = null;
        this.groupLeavingOutside = null;

        // testing only
        this.enteringGroup = null;
        this.departingGroup = null;
        // testing only
    }

    @Override
    public synchronized void escortInto(Group group) throws InterruptedException {
        // while (this.occupantGroup != null || !this.isGuardInside) {
        // wait();
        // }
        // this.occupantGroup = group;

        // testing only
        while (this.enteringGroup != null || this.departingGroup != null || !this.isGuardInside) {
            wait();
        }
        this.departingGroup = group;
        // testing only

        System.out.println(group + " enters the foyer");
        notifyAll();
    }

    @Override
    public synchronized Group escortOutOf() throws InterruptedException {
        // while (this.occupantGroup == null) {
        // wait();
        // }
        // Group leavingGroup = this.occupantGroup;
        // this.occupantGroup = null;

        // testing only
        while (this.enteringGroup == null) {
            wait();
        }
        Group leavingGroup = this.enteringGroup;
        this.enteringGroup = null;
        // testing only

        System.out.println(leavingGroup + " leaves the foyer");
        notifyAll();
        return leavingGroup;
    }

    public synchronized Group guardCheckInside() {
        this.isGuardInside = true;
        notifyAll();
        System.out.println("security guard goes in");
        return this.departingGroup;
    }

    public synchronized Group guardCheckOutside() {
        this.isGuardInside = false;
        notifyAll();
        System.out.println("security guard goes out");
        return this.groupWaitingOutside;
    }

    public synchronized void escortThroughSecurity(Group group) {
        this.isGuardInside = true;
        this.enteringGroup = group;
        System.out.println(group + " arrives at the museum");
        notifyAll();
    }

    public synchronized void sendOffHome(Group group) {
        this.isGuardInside = false;
        this.groupLeavingOutside = group;
        System.out.println(group + " departs from the museum");
        notifyAll();
    }

    // TODO: remember to clear waiting groups
    public synchronized void arriveAtMuseum(Group group) throws InterruptedException {
        // wait while checking for:
        // - foyer is empty, and
        // - security guard is outside to escort the outside group in
        // while (this.occupantGroup != null || this.isGuardInside) {
        // wait();
        // }
        // this.occupantGroup = group;

        // testing only
        while (this.enteringGroup != null || this.departingGroup != null || this.isGuardInside) {
            this.groupWaitingOutside = group;
            wait();
        }
        // this.securityGuard.escortIn();
        // this.enteringGroup = group;
        // testing only

        // System.out.println(group + " arrives at the museum");
        // notifyAll();
    }

    // TODO: remember to clear waiting groups
    public synchronized void departFromMuseum() throws InterruptedException {
        // - a group is in the foyer, and
        // - security guard is inside foyer to escort them out
        // boolean isGroupInFoyer = this.departingGroup != null;
        // boolean isGuardInside = false;
        // while (!isGroupInFoyer || !isGuardInside) {
        // wait();
        // }
        // Group departingGroup = this.occupantGroup;
        // this.occupantGroup = null;

        // testing only
        while (this.groupLeavingOutside == null || this.isGuardInside) {
            wait();
        }
        this.groupLeavingOutside = null;
        // Group leavingGroup = this.departingGroup;
        // this.departingGroup = null;
        // System.out.println(leavingGroup + " departs from the museum");
        // testing only

        // System.out.println(departingGroup + " departs from the museum");
        // notifyAll();
    }
}
