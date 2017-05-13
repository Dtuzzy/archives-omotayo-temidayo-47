import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

public class Splash_Screen extends JWindow {

	private int duration;
	static final int MY_MINIMUM = 0;
	static final int MY_MAXIMUM = 100;

	public Splash_Screen(int d) {
		duration = d;
	}

	public void viewSplash() {
		JPanel content = (JPanel) getContentPane();
		ImageIcon yo = new ImageIcon(this.getClass().getResource("images/launcher.png"));
		JLabel hv = new JLabel(yo);
		content.add(hv);
		int width = 350;
		int height = 400;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

		Color oraRed = new Color(156, 20, 20, 255);
		content.setBorder(BorderFactory.createLineBorder(oraRed, 2));

		setVisible(true);

		try {
			Thread.sleep(duration);
		} catch (Exception e) {
			setVisible(false);
		}
	}

	public void showSplashAndExit() {
		viewSplash();
		dispose();
	}

	public Splash_Screen() {
		// Throw a nice little title page up on the screen first.
		for (int i = MY_MINIMUM; i < MY_MAXIMUM; i++) {

			try {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {

					}
				});
				java.lang.Thread.sleep(10);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
