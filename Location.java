/**
 * Interface for each location in the museum, which specifies two methods that
 * have to be implemented concerning how groups are escorted into it.
 *
 * @author Nico Dinata (770318)
 */
public interface Location {
    /** Escort a group into the location (intended to be thread-safe). */
    public void escortInto(Group group) throws InterruptedException;

    /** Escort a group out of the location (intended to be thread-safe). */
    public Group escortOutOf() throws InterruptedException;
}
