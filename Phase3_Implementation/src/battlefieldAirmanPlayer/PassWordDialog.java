package battlefieldAirmanPlayer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ReviewerInformation.ReviewerDatabaseProvider;

/**
 *  PassWordDialog
 *
 *  Class to handle creating the frame and necessary actions
 *  for a user to log into the software. It disables the main
 *  playback software when the user has been inactive.
 *
 *  @author David Gwalthney
 *
 */
public class PassWordDialog extends JDialog
{
  private final JLabel         jlblUsername = new JLabel("Username");
  private final JLabel         jlblPassword = new JLabel("Password");

  private final JTextField     jtfUsername  = new JTextField(15);
  private final JPasswordField jpfPassword  = new JPasswordField();

  private final JButton        jbtOk        = new JButton("Login");
  private final JButton        jbtCancel    = new JButton("Cancel");

  private final JLabel         jlblStatus   = new JLabel(" ");

  private final ReviewerDatabaseProvider reviewer_database;
  private String user_logged_in = new String("");

  /**
   *  Default constructor
   */
  public PassWordDialog()
  {
    this(null, true, null);
  }

  /**
   *  Constructor that should be used when creating this class.
   *  It takes the parent's JFrame, modal(?), and a provider
   *  to the reviewer database.
   *
   *  @param parent
   *  @param modal
   *  @param reviewer_database
   */
  public PassWordDialog(final JFrame parent, boolean modal, ReviewerDatabaseProvider reviewer_database)
  {
    super(parent, modal);

    this.reviewer_database = reviewer_database;

    JPanel p3 = new JPanel(new GridLayout(2, 1));
    p3.add(jlblUsername);
    p3.add(jlblPassword);

    JPanel p4 = new JPanel(new GridLayout(2, 1));
    p4.add(jtfUsername);
    p4.add(jpfPassword);

    JPanel p1 = new JPanel();
    p1.add(p3);
    p1.add(p4);

    JPanel p2 = new JPanel();
    p2.add(jbtOk);
    p2.add(jbtCancel);

    JPanel p5 = new JPanel(new BorderLayout());
    p5.add(p2, BorderLayout.CENTER);
    p5.add(jlblStatus, BorderLayout.NORTH);
    jlblStatus.setForeground(Color.RED);
    jlblStatus.setHorizontalAlignment(SwingConstants.CENTER);

    setLayout(new BorderLayout());
    add(p1, BorderLayout.CENTER);
    add(p5, BorderLayout.SOUTH);
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    setTitle("Battlefield Airmen Data Login");

    addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });

    jbtOk.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        if ( validateCredentials() )
        {
          //parent.setVisible(true);
          setVisible(false);
        }
        else
        {
          jlblStatus.setText("Invalid username or password");
        }
      }
    });

    jbtCancel.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        setVisible(false);
        parent.dispose();
        System.exit(0);
      }
    });
  }

  /**
   *  Checks the username and password that was supplied by
   *  the user with what is in the database. If the password
   *  or username is longer than 14 characters, then it will
   *  be reject before even reaching the database check.
   *
   *  @return Boolean
   */
  private Boolean validateCredentials()
  {
    Boolean valid   = new Boolean(false);
    String username = jtfUsername.getText();
    char[] password = jpfPassword.getPassword();

    if( password.length >= 14 )
    {
      valid = false;
    }
    else if( username.length() >= 14 )
    {
      valid = false;
    }
    else
    {
      // Validate the input
      if( reviewer_database.isValidReviewer(username) )
      {
        user_logged_in = username;
        valid = reviewer_database.isValidPassword(username, password);
      }
    }

    if( valid )
    {
      user_logged_in = username;
      reviewer_database.setLoggedInUser(username);
    }

    return valid;
  }

  /**
   *  This will re-enable the log in screen, so that the user
   *  must log back in to continue working. It remembers
   *  who is logged in, so that it only needs the user's
   *  password to continue.
   *
   *  @param parent
   */
  public void activateLock(final JFrame parent)
  {
    setTitle("Battlefield Airmen Data Unlock");

    jtfUsername.setText(user_logged_in);
    jtfUsername.setEnabled(false);

    jpfPassword.setText(null);

    jbtOk.setText("Unlock");

    parent.setVisible(false);
    setVisible(true);
  }

}
