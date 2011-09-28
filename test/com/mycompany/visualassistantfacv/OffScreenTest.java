/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.visualassistantfacv;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Raster;
import javax.media.j3d.Screen3D;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

/**
 * OffScreenTest issues renderOffScreenBuffer from the postSwap callback of the
 * OnScreen canvas.
 */
public class OffScreenTest extends Applet {

  private SimpleUniverse u = null;

  public BranchGroup createSceneGraph(Raster drawRaster) {
    // Create the root of the branch graph
    BranchGroup objRoot = new BranchGroup();

    // spin object has composited transformation matrix
    Transform3D spin = new Transform3D();
    Transform3D tempspin = new Transform3D();

    spin.rotX(Math.PI / 4.0d);
    tempspin.rotY(Math.PI / 5.0d);
    spin.mul(tempspin);
    spin.setScale(0.7);
    spin.setTranslation(new Vector3d(-0.4, 0.3, 0.0));

    TransformGroup objTrans = new TransformGroup(spin);
    objRoot.addChild(objTrans);

    // Create a simple shape leaf node, add it to the scene graph.
    // ColorCube is a Convenience Utility class
    objTrans.addChild(new ColorCube(0.4));

    //Create a raster
    Shape3D shape = new Shape3D(drawRaster);
    objRoot.addChild(shape);

    // Let Java 3D perform optimizations on this scene graph.
    objRoot.compile();

    return objRoot;
  }

  public OffScreenTest() {
  }

  public void init() {
    setLayout(new BorderLayout());
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

    BufferedImage bImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

    ImageComponent2D buffer = new ImageComponent2D(ImageComponent.FORMAT_RGBA, bImage);
    buffer.setCapability(ImageComponent2D.ALLOW_IMAGE_READ);
    //Raster graphics image is a data structure representing a rectangular grid of pixels, or points of color, viewable via a monitor.
    Raster drawRaster = new Raster(new Point3f(0.0f, 0.0f, 0.0f), Raster.RASTER_COLOR, 0, 0, 200, 200, buffer, null);

    drawRaster.setCapability(Raster.ALLOW_IMAGE_WRITE);

    // create the main scene graph
    BranchGroup scene = createSceneGraph(drawRaster);

    // create the on-screen canvas
    OnScreenCanvas3D d = new OnScreenCanvas3D(config, false);
    add("Center", d);

    // create a simple universe
    u = new SimpleUniverse(d);

    //Canvas3D c = new Canvas3D(config, true);

    // This will move the ViewPlatform back a bit so the
    // objects in the scene can be viewed.
    u.getViewingPlatform().setNominalViewingTransform();

    // create an off Screen Canvas
    OffScreenCanvas3D c = new OffScreenCanvas3D(config, true, drawRaster);

    // set the offscreen to match the onscreen
    // On regle le screen 3D (offscreen) a la taille du screen 3D (onscreen)
    Screen3D sOn = d.getScreen3D();
    Screen3D sOff = c.getScreen3D();
    sOff.setSize(sOn.getSize());
    sOff.setPhysicalScreenWidth(sOn.getPhysicalScreenWidth());
    sOff.setPhysicalScreenHeight(sOn.getPhysicalScreenHeight());

    // attach the same view to the offscreen canvas
    View v = u.getViewer().getView();
    v.addCanvas3D(c);

    // tell onscreen about the offscreen so it knows to
    // render to the offscreen at postswap
    d.setOffScreenCanvas(c);

    u.addBranchGraph(scene);
    v.stopView();
    // Make sure that image are render completely
    // before grab it in postSwap().
    d.setImageReady();
    v.startView();

  }

  public void destroy() {
    u.cleanup();
  }

  public static void main(String argv[]) {
    new MainFrame(new OffScreenTest(), 500, 500);
  }
}

class OffScreenCanvas3D extends Canvas3D {

  Raster drawRaster;

  boolean printing = false;

  public OffScreenCanvas3D(GraphicsConfiguration gconfig, boolean offscreenflag, Raster drawRaster) {

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

class OnScreenCanvas3D extends Canvas3D {

  OffScreenCanvas3D c;

  boolean print = false;

  boolean imageReady = false;

  public OnScreenCanvas3D(GraphicsConfiguration gconfig, boolean offscreenflag) {
    super(gconfig, offscreenflag);
  }

  public void setOffScreenCanvas(OffScreenCanvas3D c) {
    this.c = c;
  }

  public void setImageReady() {
    imageReady = true;
  }

  public void postSwap() {
    if (imageReady && !print) {
      c.print(false);
      print = true;
    }
  }
}

