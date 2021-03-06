/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunifast.esprit.Utils;

/**
 *
 * @author mohamedazizhaddad
 */
import java.awt.BorderLayout;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.swing.MapView;

import com.teamdev.jxmaps.*;

public class Mapa extends MapView {

    private double lat1;
    private double long1;
    private double lat2;
    private double long2;
    /**
     * The map object
     */
    private static Map map;

    /**
     * Editable circle options
     */
    private CircleOptions settingsCircle;
    /**
     * Editable LineOptions
     */
    private PolylineOptions settingsLine;

    public CircleOptions getSettingsCircle() {
        return settingsCircle;
    }

    public void setSettingsCircle(CircleOptions settingsCircle) {
        this.settingsCircle = settingsCircle;
    }

    /**
     * Generate a marker on the LatLongPoint
     *
     * @param pos of the wanted marker
     * @return Marker
     */
    public Marker generateMarker(LatLng pos) {
        Marker marker = null;

        marker = new Marker(map);
        marker.setPosition(pos);
        map.setCenter(pos);

        System.out.println("Graficado marker");

        return marker;
    }

    /**
     * Generate a simple nibe between two LatLong points
     *
     * @param start Start point of the line
     * @param end End point of the line
     * @param markers Do you wanna put a marker on the LatLong points
     */
    public void generateSimplePath(LatLng start, LatLng end, Boolean markers) {
        LatLng[] path = {start, end};
        Polyline polyline = new Polyline(map);
        polyline.setPath(path);
        if (markers) {
            generateMarker(start);
            generateMarker(end);
        }
    }

    public Map darMap() {
        return map;
    }

    /**
     * Generate a circle area on the map
     *
     * @param center LatLong of the center of the map
     * @param radius on meters
     */
    public void generateArea(LatLng center, Double radius) {
        Circle circle = new Circle(map);
        circle.setCenter(center);
        circle.setRadius(radius);
        circle.setVisible(true);
        circle.setOptions(settingsCircle);
    }

    /**
     * Generate a line on the Map on the selected breakpoints
     *
     * @param markers do you wanna put a marker on each vertex
     * @param path Group of points of the Line
     */
    public void GenerateLine(boolean markers, LatLng... path) {
        if (markers) {
            for (LatLng p : path) {
                generateMarker(p);
            }
        }
        Polyline polyline = new Polyline(map);
        polyline.setPath(path);
    }

    public void test(LatLng lnlt) {
        lat1 = lnlt.getLat();
        long1 = lnlt.getLng();
        System.out.println(lat1 + "*****" + long1);
    }    
        

//        String endpointUrl = "http://open.mapquestapi.com/nominatim/v1/reverse.php";
//        System.out.println("j");
//Double latitude = 40.7470;
//Double longitude = -73.9860;
//MapPoint mapPoint = new MapPoint().buildMapPoint(latitude, longitude);
//  
//Address address = NominatimAPI.with(endpointUrl).getAddressFromMapPoint(mapPoint);
//        
    

    /**
     * Create a new Map panel whit the param Name
     *
     * @param pString Name for the map
     */
    public Mapa(String pString) {

        JFrame frame = new JFrame("Chicago-Data: " + pString);

        settingsCircle = new CircleOptions();
        settingsCircle.setFillColor("#FF0000");
        settingsCircle.setRadius(2000000);
        settingsCircle.setFillOpacity(0.35);

        settingsLine = new PolylineOptions();
        settingsLine.setGeodesic(true);
        settingsLine.setStrokeColor("#FF0000");
        settingsLine.setStrokeOpacity(1.0);
        settingsLine.setStrokeWeight(2.0);

        // Setting of a ready handler to MapView object. onMapReady will be called when map initialization is done and
        // the map object is ready to use. Current implementation of onMapReady customizes the map object.
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                // Check if the map is loaded correctly
                if (status == MapStatus.MAP_STATUS_OK) {
                    // Getting the associated map object
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    controlOptions.setPosition(ControlPosition.BOTTOM_LEFT);
                    mapOptions.setMapTypeControlOptions(controlOptions);

                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(36.8065, 10.1815));
                    map.setZoom(10);

                    map.addEventListener("click", new MapMouseEvent() {
                        @Override
                        public void onEvent(MouseEvent mouseEvent) {
                            // Closing initially created info window
                            System.out.println("hii");
                            // Creating a new marker
                            final Marker marker = new Marker(map);
                            // Move marker to the position where user clicked
                             test(mouseEvent.latLng());
                            marker.setPosition(mouseEvent.latLng());
                           

                            marker.addEventListener("click", new MapMouseEvent() {
                                @Override
                                public void onEvent(MouseEvent mouseEvent) {
                                    // Removing marker from the map
                                    marker.remove();

                                }
                            });
                        }
                    });

                }
            }
        });
        //System.out.print("Espere mientras se genera el mapa ");
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(".");
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        System.out.println("|");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        final Mapa example = new Mapa("test");
        example.generateMarker(map.getCenter());
        //         example.generateMarker(pos)

    }

}
