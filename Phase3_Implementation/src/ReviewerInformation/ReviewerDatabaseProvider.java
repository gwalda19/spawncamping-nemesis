package ReviewerInformation;

/**
 *  ReviewerDatabaseProvider
 *
 *  Interface class to the reviewer database.
 *  Only allow clients to access the information
 *  that they need.
 *
 *  @author David Gwalthney
 *
 */
public interface ReviewerDatabaseProvider
{

  /**
   *  Checks to see if the requested reviewer
   *  is actually allowed to be logged in.
   *
   *  @param reviewer
   *  @return Boolean
   */
  public Boolean isValidReviewer(String reviewer);

  /**
   *  Checks to see if the reviewer's password
   *  matches with what was enter by the user.
   *
   *  @param reviewer
   *  @param password
   *  @return Boolean
   */
  public Boolean isValidPassword(String reviewer, char[] password);

  /**
   *  Retrieves the reviewers information for the
   *  user that is logged into the system.
   *
   *  @return ReviewerInformation
   */
  public ReviewerInformation getReviewerInformation();

  /**
   *  Sets the reviewer as the user is logged in.
   *
   *  @param reviewer
   */
  public void setLoggedInUser(String reviewer);

}
