package ReviewerInformation;

/**
 *  ReviewerInformationProvider
 *
 *  Interface class to the reviewer information
 *  so that the clients only have access to
 *  retrieve data and not set.
 *
 *  @author David Gwalthney
 *
 */
public interface ReviewerInformationProvider
{

  /**
   *  Retrieve the reviewer's name.
   *
   *  @return String (reviewer's name)
   */
  public String getName();

  /**
   *  Retrieve the reviewer's username.
   *
   *  @return String (reviewer's username)
   */
  public String getUsername();

  /**
   *  Retrieve the reviewer's picture path.
   *
   *  @return String (reviewer's picture path)
   */
  public String getPicturePath();

  /**
   *  Retrieve the reviewer's department.
   *
   *  @return String (reviewer's department)
   */
  public String getDepartment();

  /**
   *  Retrieve the reviewer's rank.
   *
   *  @return String (reviewer's rank)
   */
  public String getRank();
}
