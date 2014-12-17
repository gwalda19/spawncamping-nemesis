package ReviewerInformation;

/**
 *  ReviewerInformation
 *
 *  Data structure to hold the all the reviewer's
 *  information.
 *
 *  Implements the ReviewerInformatinProvider interface
 *
 *  @author David Gwalthney
 *
 */
public class ReviewerInformation implements ReviewerInformationProvider
{

  private String  username;
  private String  password;
  private String  name;
  private String  path_to_picture;
  private String  department;
  private String  rank;
  private Boolean logged_in;

  /**
   *  Constructor for the ReviewerInformation class.
   */
  public ReviewerInformation()
  {
    username        = new String();
    password        = new String();
    name            = new String();
    path_to_picture = new String();
    department      = new String();
    rank            = new String();
    logged_in       = new Boolean(false);
  }

  /**
   *  set the reviewer's username.
   *
   *  @parm String (reviewer's username)
   */
  public void setUsername(String username)
  {
    this.username = username;
  }

  /**
   *  Retrieve the reviewer's username.
   *
   *  @return String (reviewer's username)
   */
  @Override
  public String getUsername()
  {
    return username;
  }

  /**
   *  set the reviewer's password.
   *
   *  @parm String (reviewer's password)
   */
  public void setPassword(String password)
  {
    this.password = password;
  }

  /**
   *  Retrieve the reviewer's password.
   *
   *  @return String (reviewer's password)
   */
  public String getPassword()
  {
    return password;
  }

  /**
   *  set the reviewer's name.
   *
   *  @parm String (reviewer's name)
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   *  Retrieve the reviewer's name.
   *
   *  @return String (reviewer's name)
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   *  set the reviewer's picture path.
   *
   *  @parm String (reviewer's picture path)
   */
  public void setPicturePath(String path)
  {
    path_to_picture = path;
  }

  /**
   *  Retrieve the reviewer's picture path.
   *
   *  @return String (reviewer's picture path)
   */
  @Override
  public String getPicturePath()
  {
    return path_to_picture;
  }

  /**
   *  set the reviewer's department.
   *
   *  @parm String (reviewer's department)
   */
  public void setDepartment(String department)
  {
    this.department = department;
  }

  /**
   *  Retrieve the reviewer's department.
   *
   *  @return String (reviewer's department)
   */
  @Override
  public String getDepartment()
  {
    return department;
  }

  /**
   *  set the reviewer's rank.
   *
   *  @parm String (reviewer's rank)
   */
  public void setRank(String rank)
  {
    this.rank = rank;
  }

  /**
   *  Retrieve the reviewer's rank.
   *
   *  @return String (reviewer's rank)
   */
  @Override
  public String getRank()
  {
    return rank;
  }

  /**
   *  set that the user is logged in.
   */
  public void setUserLoggedIn()
  {
    logged_in = true;
  }

  /**
   *  Returns if the user in logged in.
   *
   *  @return Boolean
   */
  Boolean isUserLoggedIn()
  {
    return logged_in;
  }

}
