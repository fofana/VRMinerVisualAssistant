/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualassistantfacv;

/**
 *
 * @author Abdelheq
 */
import java.awt.*;
import java.awt.image.*;

import javax.media.j3d.*;

/**
 *
 * <p>Fichier : OffScreenCanvas3D.java</p>
 * <p>Description :</p>
 * Cette classe permet de rendre une image 3D dans un canvas 3D offscreen a
 * partir d'un canvas 3D onscreen.
 * Un canvas 3D offscreen permet ensuite de recuperer l'image 3D afin de la
 * sauvegarder en PNG ou JPEG par exemple ou de l'imprimer.
 * <p>Copyright : Copyright (c) 04/2004</p>
 * <p>Company :</p>
 * @author A. MARI
 * @version 1.0
 */
public class OffScreenCanvas3D extends Canvas3D {

  private Screen3D onScreen = null;
  private Screen3D offScreen = null;

  /**
   * Construit un canvas 3D offscreen a partir d'un canvas 3D onscreen
   * @param onScreenCanvas3D canvas 3D onscreen
   */
  public OffScreenCanvas3D(Canvas3D onScreenCanvas3D) {

    super(onScreenCanvas3D.getGraphicsConfiguration(), true);

    // On regle le screen 3D (offscreen) a la taille du screen 3D (onscreen)
    onScreen = onScreenCanvas3D.getScreen3D();
    offScreen = this.getScreen3D();
    offScreen.setSize(onScreen.getSize());
    offScreen.setPhysicalScreenWidth(onScreen.getPhysicalScreenWidth());
    offScreen.setPhysicalScreenHeight(onScreen.getPhysicalScreenHeight());
  }

  /**
   * Recupere l'image 3D affichee dans le canvas 3D onscreen, en l'affichant
   * dans une canvas 3D offscreen
   * et en la redimensionnant a la dimension dim
   * @param dim dimension de l'image que l'on recupere
   * @return image qui est affichee dans le canvas 3D (onscreen)
   */
  public BufferedImage getOffScreenImage(Dimension dim) {
    // Creation de l'image BufferedImage que l'on va recuperer
    BufferedImage bImage = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
    ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bImage);
    buffer.setCapability(ImageComponent2D.ALLOW_IMAGE_READ);

    // On copie le canvas 3D onscreen dans le canvas 3D offscreen, et on
    // recupere l'image dans bImage
    setOffScreenBuffer(buffer);
    renderOffScreenBuffer();
    waitForOffScreenRendering();
    bImage = getOffScreenBuffer().getImage();

    return bImage;
  }
}

