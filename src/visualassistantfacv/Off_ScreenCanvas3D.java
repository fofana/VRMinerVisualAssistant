/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualassistantfacv;

import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Raster;

class Off_ScreenCanvas3D extends Canvas3D {

  Raster drawRaster;

  boolean printing = false;

  public Off_ScreenCanvas3D(GraphicsConfiguration gconfig, boolean offscreenflag, Raster drawRaster) {

    super(gconfig, offscreenflag);
    this.drawRaster = drawRaster;
  }

  public void print(boolean toWait) {

    if (!toWait)
      printing = true;

    // Creation de l'image BufferedImage que l'on va recuperer
    BufferedImage bImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
    ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bImage);
    buffer.setCapability(ImageComponent2D.ALLOW_IMAGE_READ);

    // On copie le canvas 3D onscreen dans le canvas 3D offscreen, et on recupere l'image dans bImage
    // la méthode setOffScreenBuffer() indique au moteur de rendu que l'image 3D sera stockée en mémoire dans l'objet buffer
    this.setOffScreenBuffer(buffer);
    // On effectue le rendu
    this.renderOffScreenBuffer();

    // on attend que le rendu soit terminé
    if (toWait) {
      this.waitForOffScreenRendering();
      drawOffScreenBuffer();
    }

  }

  public void postSwap() {

    if (printing) {
      super.postSwap();
      drawOffScreenBuffer();
      printing = false;
    }

  }

  void drawOffScreenBuffer() {

    //On récupère l'image rendue dans le canvas 3D offscreen
    BufferedImage bImage = this.getOffScreenBuffer().getImage();
    ImageComponent2D newImageComponent = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bImage);
    drawRaster.setImage(newImageComponent);
  }

}
