package battlefieldAirmanPlayer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import ReviewerInformation.ReviewerInformationProvider;

/**
 *  UserPanel
 *
 *  Creates a Java Panel that will hold the battle field
 *  reviewers information that is logged in. It shows his
 *  information and his "profile" picture.
 *
 *  @author David Gwalthney
 *
 */
public class UserPanel extends JPanel
{
  private static final long serialVersionUID = 1L;

  /**
   *  UserPanel constructor.
   *  Creates the panel to hold the reviewers information.
   *
   *  @param reviewer_information
   */
  public UserPanel(ReviewerInformationProvider reviewer_information)
  {
    setLayout(new BorderLayout());

    JSplitPane split_pane = new JSplitPane();
    split_pane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    add(split_pane, BorderLayout.CENTER);

    JPanel left  = new JPanel();
    split_pane.setLeftComponent(left);

    JPanel right = new JPanel();
    split_pane.setRightComponent(right);

    JLabel user       = new JLabel("Logged in as: " + reviewer_information.getName());
    JLabel username   = new JLabel("Username: "     + reviewer_information.getUsername());
    JLabel department = new JLabel("Department: "   + reviewer_information.getDepartment());
    JLabel rank       = new JLabel("Rank: "         + reviewer_information.getRank());

    BufferedImage myPicture = null;
    try
    {
      myPicture = ImageIO.read(new File(reviewer_information.getPicturePath()));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }

    JLabel picLabel = new JLabel(new ImageIcon(myPicture));

    left.setLayout(new BorderLayout());
    left.add(picLabel, BorderLayout.CENTER);

    right.setLayout(new GridLayout(4,1));
    right.add(user);
    right.add(username);
    right.add(rank);
    right.add(department);
  }

}
