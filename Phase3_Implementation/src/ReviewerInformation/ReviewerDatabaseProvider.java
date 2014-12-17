package ReviewerInformation;

public interface ReviewerDatabaseProvider
{
  public Boolean isValidReviewer(String reviewer);

  public Boolean isValidPassword(String reviewer, char[] password);

  public ReviewerInformation getReviewerInformation();

  public void setLoggedInUser(String reviewer);

}
