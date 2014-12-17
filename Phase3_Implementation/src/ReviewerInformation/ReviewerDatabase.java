package ReviewerInformation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReviewerDatabase implements ReviewerDatabaseProvider
{
  private final ArrayList<ReviewerInformation> battlefield_data_reviewer;

  public ReviewerDatabase(String file_name)
  {
    battlefield_data_reviewer = new ArrayList<ReviewerInformation>();

    BufferedReader br = null;
    String line = "";

    try
    {
      battlefield_data_reviewer.clear();

      br = new BufferedReader(new FileReader(file_name));
      line = br.readLine(); // Read in first line since it is just the name of the file.

      while ((line = br.readLine()) != null)
      {

        if( line.contentEquals("EOF") )
        {
          break;
        }

        // split on space (,)
        String[] values = line.trim().split(",");

        String username   = rot13(values[0]);
        String password   = rot13(values[1]);
        String name       = rot13(values[2]);
        String rank       = rot13(values[3]);
        String department = rot13(values[4]);
        String pic_path   = rot13(values[5]);

        // create reviewer object to store values
        ReviewerInformation reviewer = new ReviewerInformation();

        reviewer.setUsername(username);
        reviewer.setPassword(password);
        reviewer.setName(name);
        reviewer.setRank(rank);
        reviewer.setDepartment(department);
        reviewer.setPicturePath(pic_path);

        // adding reviewer to the list
        battlefield_data_reviewer.add(reviewer);
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (br != null)
      {
        try
        {
          br.close();
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public ReviewerInformation getReviewerInformation()
  {
    for( ReviewerInformation item: battlefield_data_reviewer )
    {
      if( item.isUserLoggedIn() )
      {
        return item;
      }
    }

    return null;
  }

  @Override
  public Boolean isValidReviewer(String reviewer)
  {
    Boolean valid = new Boolean(false);

    for( ReviewerInformation item: battlefield_data_reviewer )
    {
      if( item.getUsername().equals(reviewer) )
      {
        return true;
      }
    }

    return valid;
  }

  @Override
  public Boolean isValidPassword(String reviewer, char[] password)
  {
    Boolean valid = new Boolean(false);

    for( ReviewerInformation item: battlefield_data_reviewer )
    {
      if( item.getUsername().equals(reviewer) )
      {
        valid = Arrays.equals(item.getPassword().toCharArray(), password);
        return valid;
      }
    }

    return valid;
  }

  @Override
  public void setLoggedInUser(String reviewer)
  {
    for( ReviewerInformation item: battlefield_data_reviewer )
    {
      if( item.getUsername().equals(reviewer) )
      {
        item.setUserLoggedIn();
      }
    }
  }

  public static String rot13(String input)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < input.length(); i++)
    {
      char c = input.charAt(i);
      if (c >= 'a' && c <= 'm')
        c += 13;
      else if (c >= 'A' && c <= 'M')
        c += 13;
      else if (c >= 'n' && c <= 'z')
        c -= 13;
      else if (c >= 'N' && c <= 'Z')
        c -= 13;
      sb.append(c);
    }
    return sb.toString();
  }


}
