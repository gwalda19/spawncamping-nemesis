package ReviewerInformation;

public class ReviewerInformation implements ReviewerInformationProvider
{

  private String  username;
  private String  password;
  private String  name;
  private String  path_to_picture;
  private String  department;
  private String  rank;
  private Boolean logged_in;

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

  public void setUsername(String username)
  {
    this.username = username;
  }

  public String getUsername()
  {
    return username;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getPassword()
  {
    return password;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getName()
  {
    return name;
  }

  public void setPicturePath(String path)
  {
    path_to_picture = path;
  }

  public String getPicturePath()
  {
    return path_to_picture;
  }

  public void setDepartment(String department)
  {
    this.department = department;
  }

  public String getDepartment()
  {
    return department;
  }

  public void setRank(String rank)
  {
    this.rank = rank;
  }

  public String getRank()
  {
    return rank;
  }

  public void setUserLoggedIn()
  {
    logged_in = true;
  }

  public Boolean isUserLoggedIn()
  {
    return logged_in;
  }

}
