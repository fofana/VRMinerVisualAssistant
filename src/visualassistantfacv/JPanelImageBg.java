/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visualassistantfacv;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class JPanelImageBg extends JComponent
{
	private int mode;
	private TexturePaint texture;
	private BufferedImage bufferedImage;

	public static final int CENTRE = 0;
	public static final int TEXTURE = 1;

	JPanelImageBg( String fileName, int mode )
	{	this.mode = mode;
		this.bufferedImage = this.toBufferedImage(Toolkit.getDefaultToolkit().getImage(fileName));
		this.texture = new TexturePaint(bufferedImage,new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight()));
	}

	public void paintComponent(Graphics g)
	{	switch( mode )
		{	case TEXTURE :
				Graphics2D g2d = (Graphics2D)g;
				g2d.setPaint(texture);
				g2d.fillRect(0, 0, getWidth(), getHeight() );
				break;
			case CENTRE :
				g.setColor(this.getBackground());
				g.fillRect(0,0,getWidth(), getHeight() );
				g.drawImage(bufferedImage,(getWidth()-bufferedImage.getWidth())/2,(getHeight()-bufferedImage.getHeight())/2,null);
				break;
			default :
				super.paintComponents(g);
		}
	}


	private BufferedImage toBufferedImage(Image image)
	{	image = new ImageIcon(image).getImage();

		BufferedImage bufferedImage = new BufferedImage( image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();

		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(null),
		image.getHeight(null));
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bufferedImage;
	}

}

