package maps;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import battlefieldAirmen.BattlefieldAirmen;
import battlefieldAirmen.JsonFileGenerator;
import battlefieldAirmen.ReadBattlefieldAirmenCSV;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;

/**
 * This sample demonstrates how to load a web page with Google Maps
 * and control it using JxBrowser API.
 */
public class GoogleMapsSample extends JFrame {

		
    public static final int MIN_ZOOM = 0;
    public static final int MAX_ZOOM = 21;
    static ArrayList<BattlefieldAirmen> array;
    /**
     * In map.html file default zoom value is set to 4.
     */
    private static int zoomValue = 4;

    public static void main(String[] args) {
    	JsonFileGenerator.makeJsonFile();
    	array = ReadBattlefieldAirmenCSV.read();

    	BrowserPreferences.setChromiumSwitches("--allow-file-access");
    	BrowserPreferences.setChromiumSwitches("--allow-file-access-from-files");
    	BrowserPreferences.setChromiumSwitches("--ash-enable-system-sounds");
    	BrowserPreferences.setChromiumSwitches("--disable-web-security");
    	
        final Browser browser = BrowserFactory.create();

        // create zoom in button
        JButton zoomInButton = makeZoomInButton(browser);
        // create zoom out button
        JButton zoomOutButton = makeZoomOutButton(browser);
        // create start button
        //JButton startButton = makeStartButton(browser);
                
        // create Show All button
        JButton showAllButton = makeShowAllButton(browser);

        JPanel toolBar = new JPanel();
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.add(showAllButton);

        JFrame frame = new JFrame();
        frame.setTitle("Google Maps");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(browser.getView().getComponent(), BorderLayout.CENTER);
        frame.add(toolBar, BorderLayout.SOUTH);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Load default start up start.html file
        GoogleMapsSample.loadMapUrl(browser);

    }

	private static JButton makeShowAllButton(final Browser browser) {
		JButton showAllButton = new JButton("Show All");
		showAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	browser.loadURL("file:///"+System.getProperty("user.dir")+"\\main.html");
            }
        });
		
		
		return showAllButton;
	}

	private static JButton makeZoomOutButton(final Browser browser) {
		JButton zoomOutButton = new JButton("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue > MIN_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + --zoomValue + ")");
                }
            }
        });
		return zoomOutButton;
	}

	private static JButton makeZoomInButton(final Browser browser) {
		JButton zoomInButton = new JButton("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (zoomValue < MAX_ZOOM) {
                    browser.executeJavaScript("map.setZoom(" + ++zoomValue + ")");
                }
            }
        });
		return zoomInButton;
	}

	private static JButton makeStartButton(final Browser browser) {
		JButton resetButton = new JButton("Start");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	zoomValue = 4;
            	browser.executeJavaScript("map.setZoom(" + zoomValue + ")");
            	double lat = 39.974738;
        		double lon = -74.976621;
        		String iconPath = "http://google.com/mapfiles/kml/paddle/go.png";
            	addOverlayLatLon(browser, lat, lon, iconPath,0);
            }
        });
		return resetButton;
	}

	private static void loadMapUrl(final Browser browser) {
		
		browser.loadURL("file:///"+System.getProperty("user.dir")+"\\start.html");
	}

	
private static void addOverlayLatLon(final Browser browser, double lat, double lon, String color, int index) {
	
	// Get Battlefield Airmen Data
	BattlefieldAirmen baData = array.get(index);
	
	StringBuilder builder = new StringBuilder();
	builder.append("[baDataID="+baData.getBaDataID());
	builder.append(", sourceID="+baData.getSourceID());
	builder.append(", timestamp="+baData.getTimestamp().getTime());
	builder.append(", lat="+lat);
	builder.append(", lon="+lon);
	builder.append(", recordingReason="+baData.getRecordingReason());
	builder.append(", destinationID="+baData.getDestinationID());
	//<a href="url">link text</a>
	builder.append(", audioPath=<a href=\""+baData.getAudioPath()+"\" target=\"_blank\">Play Audio</a>");
	builder.append(", test=<a href=\""+"http://www.phon.ucl.ac.uk/home/mark/audio/success.wav"+"\" target=\"_blank\">Test Link</a>");
	builder.append(", test=<a href=\""+"map.html"+"\" target=\"_blank\">Test Link2</a>");
	builder.append(", videoPath=<a href=\""+baData.getVideoPath()+"\" target=\"_blank\">Play Video</a>");
	builder.toString();	
	String msg = builder.toString();
	
	browser.loadHTML("<!DOCTYPE html>\n" +
			"<html>\n" +
			"  <head>\n" +
			"    <title>Simple Map</title>\n" +
			"    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">\n" +
			"    <meta charset=\"utf-8\">\n" +
			"    <style>\n" +
			"      html, body, #map-canvas {\n" +
			"        height: 100%;\n" +
			"        margin: 0px;\n" +
			"        padding: 0px\n" +
			"      }\n" +
			"    </style>\n" +
			"    <script src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyAc_LtQEKkpPyWIkNUwwRUwWqOymeWISt4\"></script>\n" +
			"    <script>\n" +
			"var map;\n" +
			"function initialize() {\n" +
			"  var mapOptions = {\n" +
			"    zoom: 4,\n" +
			"    center: new google.maps.LatLng("+ lat+"," +lon+")\n" +
			"  };\n" +
			"  map = new google.maps.Map(document.getElementById('map-canvas'),\n" +
			"      mapOptions);\n" +
			"var marker=new google.maps.Marker({\n" +
			"	  position:new google.maps.LatLng("+ lat+"," +lon+"),\n" +
			"	icon: '"+color + "' \n" +
			"	  });\n" +

			"	marker.setMap(map);\n" +
			"var infowindow = new google.maps.InfoWindow({\n" +
			"	  content:'" + msg + "'\n" +
			"	  });\n" +

			"google.maps.event.addListener(marker, 'click', function() {\n" +
			"infowindow.open(map,marker);\n" +
  			"});\n" +

			"}\n" +
			"\n" +
			"google.maps.event.addDomListener(window, 'load', initialize);\n" +
			"\n" +
			"    </script>\n" +
			"  </head>\n" +
			"  <body>\n" +
			"    <div id=\"map-canvas\"></div>\n" +
			"  </body>\n" +
			"</html>");
	}
}