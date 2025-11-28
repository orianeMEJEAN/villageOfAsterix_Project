/**
 * Represents the result of a health evaluation.
 * A health result is composed of:
 *     a global indicator specifying whether the item is considered healthy,
 *     an immutable list of issues.
 *
 * The information provided during construction cannot be modified afterwards.
 *
 * @author Oriane
 * @version 1.0
 */
package food;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthResult
{
    private final boolean healthy;
    private final List<String> issues;

    /**
     * Creates a new health result.
     *
     * @param healthy true if the item is considered healthy,
     *                false otherwise
     * @param issues a list of detected issues
     */
    public HealthResult(boolean healthy, List<String> issues)
    {
        this.healthy = healthy;
        this.issues = new ArrayList<>(issues);
    }

    /**
     * Indicates whether the evaluated item is considered healthy.
     *
     * @return true if the item is healthy, otherwise false
     */
    public boolean isHealthy() { return healthy; }

    /**
     * Returns the list of issues detected during evaluation.
     *
     * @return an unmodifiable view of the issues list
     */
    public List<String> getIssues() { return Collections.unmodifiableList(issues); }

    /**
     * Provides a textual representation of the health result.
     *
     * @return a string describing the health state and any issues
     */
    public String toString()
    {
        return "HealthResult{Santé =" + healthy + ", Problème =" + issues + "}";
    }
}