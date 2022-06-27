/**
 * Represents the security guard of the museum, escorting groups into and out
 * of the museum and helping them pass through security checks.
 * 
 * @author Nico Dinata (770318)
 */
public class SecurityGuard extends Thread {
    /** The foyer instance of the museum. */
    private Foyer foyer;

    /** Whether the guard is currently inside the foyer. */
    private boolean isInsideFoyer;

    /**
     * Constructor for creating the security guard who starts in the foyer.
     */
    public SecurityGuard(Foyer foyer) {
        this.foyer = foyer;
        this.isInsideFoyer = true;
    }

    /**
     * Periodically patrols between the inside and outside of the museum,
     * escorting any groups that it finds waiting both into and out of the
     * museum.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                if (this.isInsideFoyer) {
                    // if currently inside the foyer, check for any groups
                    // waiting inside
                    Group departingGroup = this.foyer.groupWaitingInside();

                    // if there are no waiting groups, wait for a little and
                    // check again afterwards; otherwise, if there _is_ a
                    // waiting group, we don't wait and instead proceed
                    // straight away
                    if (departingGroup == null) {
                        sleep(Params.operatePause());
                        departingGroup = this.foyer.groupWaitingInside();
                    }

                    if (departingGroup != null) {
                        // if there is a group waiting inside, we escort it
                        // through security checks and towards the outside of
                        // the museum
                        sleep(Params.SECURITY_TIME + Params.WALKING_TIME);
                        this.foyer.sendOffHome(departingGroup);
                    } else {
                        // if there is no one inside the foyer, we walk back
                        // outside
                        sleep(Params.WALKING_TIME);
                        this.foyer.guardCheckOutside();
                    }

                    // regardless of whether we found a group waiting inside,
                    // we are now outside
                    this.isInsideFoyer = false;

                } else {
                    // if currently outside the foyer, check for any groups
                    // waiting outside
                    Group groupOutside = this.foyer.groupWaitingOutside();

                    // similar to when we are inside the museum, if there are
                    // no waiting groups, wait for a little and check again
                    // afterwards; otherwise, if there _is_ a waiting group,
                    // we don't wait and instead proceed straight away
                    if (groupOutside == null) {
                        sleep(Params.operatePause());
                        groupOutside = this.foyer.groupWaitingOutside();
                    }

                    if (groupOutside != null) {
                        // if there is a group waiting outside, we escort it
                        // into the museum and through security checks
                        sleep(Params.WALKING_TIME + Params.SECURITY_TIME);
                        this.foyer.escortThroughSecurity(groupOutside);
                    } else {
                        // if there is no one outside, we walk back in
                        sleep(Params.WALKING_TIME);
                        this.foyer.guardCheckInside();
                    }

                    // regardless of whether we found a group waiting outside,
                    // we are now back inside the foyer
                    this.isInsideFoyer = true;
                }
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
