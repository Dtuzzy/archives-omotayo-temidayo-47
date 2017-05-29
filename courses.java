import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;


public class courses {

	public void openThatCourse() {
		JFrame rad = new JFrame();
		rad.setTitle("Edusoft");
		rad.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		java.net.URL filePath = this.getClass().getResource("courses/thermo.pdf");
		//pdf library to open the course goes in here....

		rad.add(vcp);
		rad.setResizable(true);
		rad.setSize(1370, 750);
		rad.setVisible(true);
		rad.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		rad.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No", "Previous Page" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (PromptResult == JOptionPane.CANCEL_OPTION) {
						rad.dispose();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
