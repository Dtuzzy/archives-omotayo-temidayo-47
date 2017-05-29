import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class SearchEngine {

	private JLabel search_txt;
	private JTextField search_field;
	private JButton search_butt;

	public void searchIndex() {

		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

			JFrame search_box = new JFrame();
			search_box.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());

			SearchHandler handle = new SearchHandler();
			search_txt = new JLabel("Type Course Code Here:");
			search_field = new JTextField();
			search_butt = new JButton("Search");
			search_butt.addActionListener(handle);

			search_box.add(search_txt);
			search_box.add(search_field);
			search_box.add(search_butt);
			search_box.setLayout(new GridLayout(3, 3));
			search_box.setSize(280, 120);
			search_box.setLocation(85, 570);
			search_box.setBackground(Color.blue);
			search_box.setVisible(true);
			search_box.setResizable(false);
			search_box.addComponentListener(new ComponentListener() {

				@Override
				public void componentResized(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentMoved(ComponentEvent e) {
					search_box.setLocation(85, 570);

				}

				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub

				}

			});
			search_box.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public class SearchHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//implement search strings
			
		}
	}
}
