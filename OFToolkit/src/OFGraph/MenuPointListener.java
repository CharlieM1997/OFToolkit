/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OFGraph;

import java.awt.geom.Point2D;

/**
 * Used to set the point at which the mouse was clicked for those menu items
 * interested in this information.  Useful, for example, if you want to bring up
 * a dialog box right at the point the mouse was clicked.
 * The MenuMousePlugin checks to see if a menu component implements
 * this interface and if so calls it to set the point.
 * @author Dr. Greg M. Bernstein
 */
public interface MenuPointListener {
    /**
     * Sets the point of the mouse click.
     * @param point 
     */
 void   setPoint(Point2D point);
    
}
