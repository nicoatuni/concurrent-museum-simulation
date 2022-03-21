/**
 * TODO: add top-level docs
 *
 * @author Nico Dinata (770318)
 */
public interface Location {
    public void escortInto(Group group) throws InterruptedException;

    public Group escortOutOf() throws InterruptedException;
}
