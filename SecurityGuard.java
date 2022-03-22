/**
 * TODO: add top-level docs
 * 
 * @author Nico Dinata (770318)
 */
public class SecurityGuard extends Thread {
    private Foyer foyer;
    private boolean isInsideFoyer;

    public SecurityGuard(Foyer foyer) {
        this.foyer = foyer;
        this.isInsideFoyer = true;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // If foyer is empty, periodically goes outside for an interval
                // to check for arriving groups. If no arriving groups, periodically
                // goes inside for an interval to check for departing groups.
                // sleep(Params.operatePause());
                if (this.isInsideFoyer) {
                    Group departingGroup = this.foyer.groupWaitingInside();
                    if (departingGroup == null) {
                        sleep(Params.operatePause());
                        departingGroup = this.foyer.groupWaitingInside();
                    }

                    if (departingGroup != null) {
                        sleep(Params.SECURITY_TIME + Params.WALKING_TIME);
                        this.foyer.sendOffHome(departingGroup);
                    } else {
                        sleep(Params.WALKING_TIME);
                        this.foyer.guardCheckOutside();
                    }
                    this.isInsideFoyer = false;
                } else {
                    Group groupWaitingOutside = this.foyer.groupWaitingOutside();
                    if (groupWaitingOutside == null) {
                        sleep(Params.operatePause());
                        groupWaitingOutside = this.foyer.groupWaitingOutside();
                    }

                    if (groupWaitingOutside != null) {
                        sleep(Params.WALKING_TIME + Params.SECURITY_TIME);
                        this.foyer.escortThroughSecurity(groupWaitingOutside);
                    } else {
                        sleep(Params.WALKING_TIME);
                        this.foyer.guardCheckInside();
                    }
                    this.isInsideFoyer = true;
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
