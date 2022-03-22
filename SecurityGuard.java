/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class SecurityGuard extends Thread {
    private Foyer foyer;
    private boolean isInsideFoyer;
    private Group waitingGroup;

    public SecurityGuard(Foyer foyer) {
        this.foyer = foyer;
        this.isInsideFoyer = true;
        this.waitingGroup = null;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // If foyer is empty, periodically goes outside for an interval
                // to check for arriving groups. If no arriving groups, periodically
                // goes inside for an interval to check for departing groups.
                if (this.isInsideFoyer) {
                    // Group departingGroup = this.foyer.guardCheckInside();
                    // if (departingGroup != null) {
                    if (waitingGroup != null) {
                        sleep(Params.SECURITY_TIME + Params.WALKING_TIME);
                        // this.foyer.sendOffHome(departingGroup);
                        this.foyer.sendOffHome(waitingGroup);
                        this.waitingGroup = null;
                    } else {
                        sleep(Params.operatePause() + Params.WALKING_TIME);
                        this.waitingGroup = foyer.guardCheckOutside();
                    }
                    this.isInsideFoyer = false;
                } else {
                    // Group groupWaitingOutside = this.foyer.guardCheckOutside();
                    // if (groupWaitingOutside != null) {
                    if (waitingGroup != null) {
                        sleep(Params.WALKING_TIME + Params.SECURITY_TIME);
                        // this.foyer.escortThroughSecurity(groupWaitingOutside);
                        this.foyer.escortThroughSecurity(waitingGroup);
                        this.waitingGroup = null;
                    } else {
                        sleep(Params.operatePause() + Params.WALKING_TIME);
                        this.waitingGroup = foyer.guardCheckInside();
                    }
                    this.isInsideFoyer = true;
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
