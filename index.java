import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

public class index {

	private JFrame frm, frm_home, frm_return, frm_eng, frm_course, frm_reg;
	private JPanel bg1, bg2, auth;
	private JLabel get_name, get_course, get_level, get_mat;
	private JLabel lbl, adName, c_bkrg, l_bkrg;
	private JLabel usern, passw, confm_pass, mat_no, lvl;
	private JButton cr_1, cr_2, cr_3, cr_4, cr_5, cr_6, cr_7, cr_8, cr_9, cr_10, cr_11, cr_12;
	private ImageIcon jr, lt, sm, rc, lb, ad, hm, syn, edt, c_bk, l_bk, srch, crss;
	private JButton journal, library, lecture, seminar, recent;
	private JButton login, search, register_btn, privacy, register, back, home, synopsis, edit_pp, bk, eng, b_sc, b_law;
	private JButton mech, civil, pet, elect, chemical;
	private JTextField name, surname, username, matric_no, user_login;
	private JPasswordField password, confirm_pass, pass_login;
	private JLabel ret_dept, ret_level, ret_matric, ret_name;
	String names, dpt1, levs, mat;
	private static String[] level = { "100", "200", "300", "400", "500" };
	private static String[] dept = { "Mechanical Engineering", "Civil Engineering", "Electrical Engineering",
			"Petroleum Engineering" };
	private JComboBox<String> lev, dpt;
	// sqlite connection
	Connection connection = null;

	public index() {
		// initialize connection
		connection = createTable.dbConnector();
		// frame
		frm = new JFrame();
		frm.setTitle("Edusoft");
		frm.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		bg1 = new JPanel();
		ImageIcon yo = new ImageIcon(this.getClass().getResource("images/edu_login_page.png"));
		JLabel hv = new JLabel(yo);

		// authentication
		ImageIcon authIcon = new ImageIcon(this.getClass().getResource("images/edu_help.png"));
		JButton au = new JButton(authIcon);
		au.setBorderPainted(false);
		au.setContentAreaFilled(false);
		au.setBounds(1000, 120, 200, 50);
		frm.add(au);

		// privacy
		ImageIcon priv = new ImageIcon(this.getClass().getResource("images/edu_privacy.png"));
		privacy = new JButton(priv);
		privacy.setBorderPainted(false);
		privacy.setContentAreaFilled(false);
		privacy.setBounds(900, 675, 200, 50);
		frm.add(privacy);

		user_login = new JTextField();
		user_login.setBounds(1000, 205, 250, 40);
		user_login.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		// password
		pass_login = new JPasswordField();
		pass_login.setBounds(1000, 265, 250, 40);
		pass_login.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		// return statement labels
		ret_name = new JLabel();
		ret_name.setBounds(97, 260, 250, 40);
		ret_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		// return statement labels
		ret_dept = new JLabel();
		ret_dept.setBounds(98, 370, 200, 50);
		ret_dept.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		// return statement labels
		ret_level = new JLabel();
		ret_level.setBounds(98, 410, 200, 50);
		ret_level.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		// return statement labels
		ret_matric = new JLabel();
		ret_matric.setBounds(98, 450, 300, 50);
		ret_matric.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));

		// Register
		register_btn = new JButton("Click Here If You're Not A Registered User");
		register_btn.setBorderPainted(false);
		register_btn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 14));
		register_btn.setContentAreaFilled(false);
		register_btn.setBounds(930, 380, 400, 40);
		register_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					frm.dispose();
					openRegPage();
				}catch(Exception ex){
					System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
				}
			}
		});
		// login
		ImageIcon lgn = new ImageIcon(this.getClass().getResource("images/edu_login_logo.png"));
		JButton login = new JButton(lgn);
		login.setBounds(1000, 325, 100, 40);
		login.setBorderPainted(false);
		login.setContentAreaFilled(false);
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (user_login.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");
				else if (pass_login.getPassword().length == 0) {
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

					// else if (validate_login() && queryMe() == false ){
					// JOptionPane.showMessageDialog(null, "Incorrect Username
					// or password");

				} else {
					validate_login();
					queryMe();

				}
			}

			private boolean queryMe() {

				try {
					String query = ("SELECT * FROM STUDENT");

					PreparedStatement prep = connection.prepareStatement(query);
					ResultSet result = prep.executeQuery();
					while (result.next()) {
						names = result.getString("Username").trim();
						dpt1 = result.getString("Dept").trim();
						levs = result.getString("Level").trim();
						mat = result.getString("Matric").trim();

						ret_name.setText(names);
						ret_dept.setText(dpt1);
						ret_level.setText(levs + "Level");
						ret_matric.setText("Matric No: " + mat);

						System.out.println(names);
						System.out.println(dpt1);
						System.out.println(levs);
						System.out.println(mat);
					}

					result.close();
					prep.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;

			}

			private boolean validate_login() {
				try {
					String query = "SELECT * FROM STUDENT where username=? and password=?";
					PreparedStatement pst = connection.prepareStatement(query);

					pst.setString(1, user_login.getText());
					pst.setString(2, pass_login.getText());

					ResultSet rs = pst.executeQuery();
					int count = 0;
					while (rs.next()) {
						count = count + 1;
					}
					if (count == 1) {
						frm.dispose();
						openHomePage();

					} else if (count > 1) {
						JOptionPane.showMessageDialog(null, "Duplicate Username or Password...please try again!!");
					} else {
						JOptionPane.showMessageDialog(null, "Incorrect Username or Password...please try again!!");
						user_login.setText("");
						pass_login.setText("");
					}
					rs.close();
					pst.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;

			}
		});
		frm.add(register_btn);
		frm.add(user_login);
		frm.add(pass_login);
		frm.add(login);

		bg1.add(hv);
		// frm.add(auth);
		frm.add(bg1);

		frm.setSize(1370, 750);
		frm.setVisible(true);
		frm.setBackground(Color.blue);
		frm.setLocationRelativeTo(null);
		frm.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Registreation Page
	protected void openRegPage() {
		
		frm_reg = new JFrame();
		frm_reg.setTitle("Edusoft");
		frm_reg.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		bg1 = new JPanel();
		
		// authentication
		ImageIcon logo = new ImageIcon(this.getClass().getResource("images/edu_cvn_img.png"));
		JLabel au = new JLabel(logo);
		au.setBounds(40, 20, 400, 150);
		frm_reg.add(au);

		// name = new JTextField();
		// name.setBounds(120, 180, 250, 30);
		// name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));

		// password
		// surname = new JTextField();
		// surname.setBounds(120, 220, 250, 30);
		/// surname.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC,
		// 16));

		usern = new JLabel("Username :");
		usern.setBounds(10, 180, 200, 30);
		username = new JTextField();
		username.setBounds(120, 180, 250, 30);
		username.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));

		passw = new JLabel("Password :");
		passw.setBounds(10, 220, 200, 30);
		password = new JPasswordField(6);
		password.setBounds(120, 220, 250, 30);
		password.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));

		confm_pass = new JLabel("Confirm Password:");
		confm_pass.setBounds(10, 260, 200, 30);
		confirm_pass = new JPasswordField(6);
		confirm_pass.setBounds(120, 260, 250, 30);
		confirm_pass.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));

		mat_no = new JLabel("Matric No :");
		mat_no.setBounds(10, 300, 200, 30);
		matric_no = new JTextField();
		matric_no.setBounds(120, 300, 250, 30);
		matric_no.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));

		JLabel depp = new JLabel("Department :");
		depp.setBounds(10, 340, 200, 30);
		dpt = new JComboBox(dept);
		dpt.setBounds(120, 340, 250, 30);
		dpt.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));
		dpt.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

			}
		});
		lvl = new JLabel("Level :");
		lvl.setBounds(10, 380, 200, 30);
		lev = new JComboBox(level);
		lev.setBounds(120, 380, 250, 30);
		lev.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));
		lev.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

			}
		});

		// Register
		ImageIcon reg = new ImageIcon(this.getClass().getResource("images/register.png"));
		register = new JButton(reg);
		register.setBounds(120, 420, 250, 100);
		register.setBorderPainted(false);
		register.setContentAreaFilled(false);
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				char[] p1 = password.getPassword();

				if (username.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else if (password.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else if (confirm_pass.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else if (matric_no.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else if (password.getPassword().length != 6)
					JOptionPane.showMessageDialog(null, "Password must be 6 characters");

				else if (!confirm_pass.getPassword().equals(p1)) {
					connection = createTable.dbConnector();
					createTable c = new createTable();
					c.insertTable();
					validate();
					JOptionPane.showMessageDialog(null, "Registration Successfull..");
					frm_reg.dispose();
					frm.setVisible(true);
				} else {

					JOptionPane.showMessageDialog(null, "Registration Failed!! please try again..");

				}
			}

			private boolean validate() {
				try {

					String sql = "INSERT INTO STUDENT" + "(Username,Password,Passtwo,Matric,Dept,Level) VALUES"
							+ "(?,?,?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(sql);

					pst.setString(1, username.getText());
					pst.setString(2, password.getText());
					pst.setString(3, confirm_pass.getText());
					pst.setString(4, matric_no.getText());
					pst.setString(5, (String) dpt.getSelectedItem());
					pst.setString(6, (String) lev.getSelectedItem());
					pst.executeUpdate();

					connection.commit();
					pst.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Registration Failed!! please try again..");
				}
				return false;
			}
		});

		// already a user
		register_btn = new JButton("Click Here If You're A Registered User");
		register_btn.setBorderPainted(false);
		register_btn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 14));
		register_btn.setContentAreaFilled(false);
		register_btn.setBounds(50, 510, 400, 40);
		register_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_reg.dispose();
				frm.setVisible(true);
			}
		});

		frm_reg.add(usern);
		frm_reg.add(passw);
		frm_reg.add(confm_pass);
		frm_reg.add(mat_no);
		frm_reg.add(lvl);
		frm_reg.add(username);
		frm_reg.add(password);
		frm_reg.add(confirm_pass);
		frm_reg.add(matric_no);
		frm_reg.add(lev);
		frm_reg.add(depp);
		frm_reg.add(dpt);
		frm_reg.add(register);
		frm_reg.add(register_btn);

		//bg1.add(hv);
		// frm.add(auth);
		frm_reg.add(bg1);

		frm_reg.setSize(500, 600);
		frm_reg.setVisible(true);
		frm_reg.setResizable(false);
		frm_reg.setBackground(Color.blue);
		frm_reg.setLocationRelativeTo(null);
		frm_reg.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm_reg.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {

				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

					String ObjButtons[] = { "Yes", "No", "Login" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (PromptResult == JOptionPane.CANCEL_OPTION) {
						frm_reg.dispose();
						frm.setVisible(true);
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	// homepage menu Items
	protected void openHomePage() {

		frm_home = new JFrame();
		frm_home.setTitle("Edusoft");
		frm_home.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		bg2 = new JPanel();

		ImageIcon yo = new ImageIcon(this.getClass().getResource("background/edu_edu.png"));
		JLabel hb = new JLabel(yo);

		ButtonHandler handler = new ButtonHandler();

		// logout
		ad = new ImageIcon(this.getClass().getResource("images/edu_log_out.png"));
		JButton adm = new JButton(ad);
		adm.setBounds(1155, 90, 112, 37);
		adm.setBorderPainted(false);
		adm.setContentAreaFilled(false);
		adm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_home.dispose();
				frm.setVisible(true);
			}
		});
		frm_home.add(adm);

		ImageIcon lg = new ImageIcon(this.getClass().getResource("images/Edusoft_logo.png"));
		JLabel des = new JLabel(lg);
		des.setBounds(1100, 650, 250, 50);
		// frm_home.add(des);

		// privacy
		ImageIcon priv = new ImageIcon(this.getClass().getResource("images/edu_privacy.png"));
		privacy = new JButton(priv);
		privacy.setBorderPainted(false);
		privacy.setContentAreaFilled(false);
		privacy.setBounds(900, 675, 200, 50);
		frm_home.add(privacy);
		// Callin from login
		frm_home.add(ret_name);
		frm_home.add(ret_dept);
		frm_home.add(ret_level);
		frm_home.add(ret_matric);
		// administration
		JLabel nn = new JLabel();
		nn.setText("Welcome");
		nn.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 35));
		nn.setBounds(97, 220, 250, 40);
		frm_home.add(nn);

		adName = new JLabel();
		adName.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 30));
		adName.setBounds(97, 260, 250, 40);
		adName.setText(ret_name.getText());
		// frm_home.add(adName);

		// home
		hm = new ImageIcon(this.getClass().getResource("images/home_02.png"));
		home = new JButton(hm);
		home.setBounds(990, 88, 120, 37);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		// frm_home.add(home);

		// name
		get_name = new JLabel();
		get_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		get_name.setText(ret_name.getText());
		get_name.setBounds(97, 260, 250, 40);
		// frm_home.add(get_name);

		// course background

		c_bkrg = new JLabel();
		c_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		c_bkrg.setText(ret_dept.getText());
		c_bkrg.setBounds(90, 330, 200, 50);
		// frm_home.add(c_bkrg);

		// level background

		l_bkrg = new JLabel();
		l_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		l_bkrg.setText(ret_level.getText());
		l_bkrg.setBounds(94, 370, 200, 50);
		// frm_home.add(l_bkrg);

		// Matric
		get_mat = new JLabel();
		get_mat.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		get_mat.setText(ret_matric.getText());
		get_mat.setBounds(94, 410, 300, 50);
		// frm_home.add(get_mat);
		// Synopsis
		syn = new ImageIcon(this.getClass().getResource("images/synopsis.png"));
		synopsis = new JButton(syn);
		synopsis.setBounds(90, 490, 220, 50);
		synopsis.setBorderPainted(false);
		synopsis.setContentAreaFilled(false);
		frm_home.add(synopsis);

		// Search
		srch = new ImageIcon(this.getClass().getResource("images/search.png"));
		search = new JButton(srch);
		search.setBounds(80, 550, 250, 50);
		search.setBorderPainted(false);
		search.setContentAreaFilled(false);
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchEngine se = new SearchEngine();
				se.searchIndex();
			}
		});
		frm_home.add(search);

		// back
		// ImageIcon bb = new
		// ImageIcon(this.getClass().getResource("images/Edusoft_arrow.png"));
		// back = new JButton(bb);
		// back.setBounds(20, 30, 40, 40);
		// back.addActionListener(handler);

		// ImageIcon mo = new
		// ImageIcon(this.getClass().getResource("images/Edusoft_a.png"));
		// JLabel bk = new JLabel(mo);
		// bk.setBounds(70, 30, 40, 40);

		// engineering
		ImageIcon y = new ImageIcon(this.getClass().getResource("colleges/edu_engineering.png"));
		eng = new JButton(y);
		eng.setBounds(440, 270, 145, 160);
		eng.setBorderPainted(false);
		eng.setContentAreaFilled(false);
		eng.addActionListener(handler);
		frm_home.add(eng);

		// Law
		ImageIcon lw = new ImageIcon(this.getClass().getResource("colleges/edu_leader.png"));
		b_law = new JButton(lw);
		b_law.setBounds(615, 265, 160, 170);
		b_law.setBorderPainted(false);
		b_law.setContentAreaFilled(false);
		b_law.addActionListener(handler);
		frm_home.add(b_law);

		// sciences
		ImageIcon sc = new ImageIcon(this.getClass().getResource("colleges/edu_science.png"));
		b_sc = new JButton(sc);
		b_sc.setBounds(800, 270, 145, 160);
		b_sc.setBorderPainted(false);
		b_sc.setContentAreaFilled(false);
		b_sc.addActionListener(handler);
		frm_home.add(b_sc);

		// The lower level of the menus involving other faculties
		// business
		ImageIcon bs = new ImageIcon(this.getClass().getResource("colleges/edu_business.png"));
		JButton b_bs = new JButton(bs);
		b_bs.setBounds(980, 270, 145, 160);
		b_bs.setBorderPainted(false);
		b_bs.setContentAreaFilled(false);
		frm_home.add(b_bs);

		// leadership
		ImageIcon ld = new ImageIcon(this.getClass().getResource("colleges/edu_post.png"));
		JButton b_lead = new JButton(ld);
		b_lead.setBounds(1160, 265, 160, 170);
		b_lead.setBorderPainted(false);
		b_lead.setContentAreaFilled(false);
		frm_home.add(b_lead);

		// Quick Links
		jr = new ImageIcon(this.getClass().getResource("links/edu_journal.png"));
		journal = new JButton(jr);
		journal.setBounds(440, 500, 145, 160);
		journal.setBorderPainted(false);
		journal.setContentAreaFilled(false);
		frm_home.add(journal);

		lb = new ImageIcon(this.getClass().getResource("links/edu_library.png"));
		library = new JButton(lb);
		library.setBounds(590, 500, 145, 160);
		library.setBorderPainted(false);
		library.setContentAreaFilled(false);
		frm_home.add(library);

		lt = new ImageIcon(this.getClass().getResource("links/edu_lecture.png"));
		lecture = new JButton(lt);
		lecture.setBounds(730, 500, 145, 160);
		lecture.setBorderPainted(false);
		lecture.setContentAreaFilled(false);
		frm_home.add(lecture);

		sm = new ImageIcon(this.getClass().getResource("links/edu_seminar.png"));
		seminar = new JButton(sm);
		seminar.setBounds(870, 500, 145, 160);
		seminar.setBorderPainted(false);
		seminar.setContentAreaFilled(false);
		frm_home.add(seminar);

		rc = new ImageIcon(this.getClass().getResource("links/edu_recent.png"));
		recent = new JButton(rc);
		recent.setBounds(1010, 500, 145, 160);
		recent.setBorderPainted(false);
		recent.setContentAreaFilled(false);
		frm_home.add(recent);

		bg2.add(hb);
		// frm_home.add(back);
		// frm_home.add(bk);
		frm_home.add(bg2);
		frm_home.setSize(1370, 759);
		frm_home.setBackground(Color.blue);
		frm_home.setVisible(true);
		frm_home.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm_home.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	// set actions to all buttons in frame
	public class ButtonHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == back) {
				frm_home.setVisible(false);
				frm.setVisible(true);
				queryMe();
			} else if (e.getSource() == eng) {
				frm_home.dispose();
				openEng();

			} else if (e.getSource() == b_law) {

			} else if (e.getSource() == b_sc) {

			}

		}

	}

	// engineering
	public void openEng() {

		frm_eng = new JFrame();
		frm_eng.setTitle("Edusoft");
		frm_eng.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		bg2 = new JPanel();

		ImageIcon yo = new ImageIcon(this.getClass().getResource("background/edu_department.png"));
		JLabel hv = new JLabel(yo);

		// privacy
		ImageIcon priv = new ImageIcon(this.getClass().getResource("images/edu_privacy.png"));
		privacy = new JButton(priv);
		privacy.setBorderPainted(false);
		privacy.setContentAreaFilled(false);
		privacy.setBounds(900, 675, 200, 50);
		frm_eng.add(privacy);
		// logout
		ad = new ImageIcon(this.getClass().getResource("images/edu_log_out.png"));
		JButton logout = new JButton(ad);
		logout.setBounds(1155, 90, 112, 37);
		logout.setBorderPainted(false);
		logout.setContentAreaFilled(false);
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_eng.dispose();
				frm.setVisible(true);

			}
		});
		frm_eng.add(logout);

		// Search
		srch = new ImageIcon(this.getClass().getResource("images/search.png"));
		search = new JButton(srch);
		search.setBounds(80, 550, 250, 50);
		search.setBorderPainted(false);
		search.setContentAreaFilled(false);
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchEngine se = new SearchEngine();
				se.searchIndex();
			}
		});
		frm_eng.add(search);

		// home
		hm = new ImageIcon(this.getClass().getResource("images/home_02.png"));
		home = new JButton(hm);
		home.setBounds(990, 90, 120, 37);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_eng.dispose();
				returnHomePage();

			}
		});
		frm_eng.add(home);

		// edit profile picture
		// edt = new
		// ImageIcon(this.getClass().getResource("images/edit_pp.png"));
		// edit_pp = new JButton(edt);
		// edit_pp.setBounds(90, 330, 200, 50);
		// edit_pp.setBorderPainted(false);
		// edit_pp.setContentAreaFilled(false);
		// frm_eng.add(edit_pp);

		// Calling from login

		// name
		get_name = new JLabel();
		get_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		get_name.setText(ret_name.getText());
		get_name.setBounds(97, 260, 250, 40);
		frm_eng.add(get_name);

		// course background

		c_bkrg = new JLabel();
		c_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		c_bkrg.setText(ret_dept.getText());
		c_bkrg.setBounds(90, 330, 200, 50);
		frm_eng.add(c_bkrg);

		// level background

		l_bkrg = new JLabel();
		l_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		l_bkrg.setText(ret_level.getText());
		l_bkrg.setBounds(94, 370, 200, 50);
		frm_eng.add(l_bkrg);

		// Matric
		get_mat = new JLabel();
		get_mat.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		get_mat.setText(ret_matric.getText());
		get_mat.setBounds(94, 410, 300, 50);
		frm_eng.add(get_mat);

		// Synopsis
		syn = new ImageIcon(this.getClass().getResource("images/synopsis.png"));
		synopsis = new JButton(syn);
		synopsis.setBounds(90, 460, 220, 50);
		synopsis.setBorderPainted(false);
		synopsis.setContentAreaFilled(false);
		frm_eng.add(synopsis);

		ButtonHandler handler = new ButtonHandler();
		// adimn panel
		JLabel nn = new JLabel();
		nn.setText("Welcome");
		nn.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 35));
		nn.setBounds(97, 220, 250, 40);
		frm_eng.add(nn);

		adName = new JLabel();
		adName.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 30));
		adName.setBounds(97, 260, 250, 40);
		// frm_eng.add(adName);

		// back
		ImageIcon bb = new ImageIcon(this.getClass().getResource("images/Edusoft_arrow.png"));
		back = new JButton(bb);
		back.setBounds(20, 30, 40, 40);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// needs fixing variable
				frm_eng.dispose();
				returnHomePage();
			}
		});

		ImageIcon mo = new ImageIcon(this.getClass().getResource("images/Edusoft_a.png"));
		JLabel bk = new JLabel(mo);
		bk.setBounds(70, 30, 40, 40);

		// Mechanical
		ImageIcon y = new ImageIcon(this.getClass().getResource("departments/edu_mech.png"));
		mech = new JButton(y);
		mech.setBounds(440, 240, 145, 160);
		mech.setBorderPainted(false);
		mech.setContentAreaFilled(false);
		mech.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openOption();
			}
		});
		frm_eng.add(mech);

		// Civil
		ImageIcon lw = new ImageIcon(this.getClass().getResource("departments/edu_civil.png"));
		civil = new JButton(lw);
		civil.setBounds(615, 240, 160, 170);
		civil.setBorderPainted(false);
		civil.setContentAreaFilled(false);
		civil.addActionListener(handler);
		frm_eng.add(civil);

		// Petrol
		ImageIcon sc = new ImageIcon(this.getClass().getResource("departments/edu_petrol.png"));
		pet = new JButton(sc);
		pet.setBounds(800, 240, 145, 160);
		pet.setBorderPainted(false);
		pet.setContentAreaFilled(false);
		pet.addActionListener(handler);
		frm_eng.add(pet);

		// The lower level of the menus involving other faculties
		// electrical
		ImageIcon bs = new ImageIcon(this.getClass().getResource("departments/edu_electrical.png"));
		elect = new JButton(bs);
		elect.setBounds(980, 240, 145, 180);
		elect.setBorderPainted(false);
		elect.setContentAreaFilled(false);
		frm_eng.add(elect);

		// chemical
		ImageIcon ld = new ImageIcon(this.getClass().getResource("departments/edu_chemical.png"));
		chemical = new JButton(ld);
		chemical.setBounds(1140, 240, 170, 170);
		chemical.setBorderPainted(false);
		chemical.setContentAreaFilled(false);
		frm_eng.add(chemical);

		// Quick Links
		jr = new ImageIcon(this.getClass().getResource("links/yellow_journal.png"));
		journal = new JButton(jr);
		journal.setBounds(440, 480, 145, 160);
		journal.setBorderPainted(false);
		journal.setContentAreaFilled(false);
		frm_eng.add(journal);

		lb = new ImageIcon(this.getClass().getResource("links/yellow_library.png"));
		library = new JButton(lb);
		library.setBounds(590, 480, 145, 160);
		library.setBorderPainted(false);
		library.setContentAreaFilled(false);
		frm_eng.add(library);

		lt = new ImageIcon(this.getClass().getResource("links/yellow_lecture.png"));
		lecture = new JButton(lt);
		lecture.setBounds(730, 480, 145, 160);
		lecture.setBorderPainted(false);
		lecture.setContentAreaFilled(false);
		frm_eng.add(lecture);

		sm = new ImageIcon(this.getClass().getResource("links/yellow_seminar.png"));
		seminar = new JButton(sm);
		seminar.setBounds(870, 480, 145, 160);
		seminar.setBorderPainted(false);
		seminar.setContentAreaFilled(false);
		frm_eng.add(seminar);

		rc = new ImageIcon(this.getClass().getResource("links/yellow_recent.png"));
		recent = new JButton(rc);
		recent.setBounds(1010, 480, 145, 160);
		recent.setBorderPainted(false);
		recent.setContentAreaFilled(false);
		frm_eng.add(recent);

		bg2.add(hv);
		frm_eng.add(back);
		frm_eng.add(bk);
		bg2.setBackground(new Color(233, 255, 189));
		frm_eng.add(bg2);
		frm_eng.setSize(1370, 750);
		frm_eng.setVisible(true);
		frm_eng.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm_eng.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No", "Return To Home" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (PromptResult == JOptionPane.CANCEL_OPTION) {
						frm_eng.dispose();
						frm_home.setVisible(true);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	// Query from database

	public boolean queryMe() {
		try {
			String query = ("SELECT * FROM STUDENT");

			PreparedStatement prep = connection.prepareStatement(query);
			ResultSet result = prep.executeQuery();
			while (result.next()) {
				String names = result.getString("Username").trim();
				String dpt = result.getString("Dept").trim();
				String levs = result.getString("Level").trim();
				String mat = result.getString("Matric").trim();

				ret_name.setText(names);
				ret_dept.setText(dpt);
				ret_level.setText(levs + "Level");
				ret_matric.setText("Matric No: " + mat);

				System.out.println(names);
				System.out.println(dpt);
				System.out.println(levs);
				System.out.println(mat);
			}

			result.close();
			prep.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;

	}

	// level
	public void openOption() {
		try {

			// ui
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

			String ObjButtons[] = { "First", "Second", "Third", "Fourth" };

			int PromptResult = JOptionPane.showOptionDialog(null, "Choose Year", "Edusoft", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);

			if (PromptResult == JOptionPane.YES_OPTION) {
				openSemester();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// semester
	public void openSemester() {
		try {
			// ui
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");

			String ObjButtons[] = { "First", "Second" };

			int PromptResult = JOptionPane.showOptionDialog(null, "Choose Semester", "Edusoft",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, ObjButtons, ObjButtons[1]);

			if (PromptResult == JOptionPane.YES_OPTION) {
				frm_eng.dispose();
				openCourses();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// courses
	public void openCourses() {
	
		frm_course = new JFrame();
		frm_course.setTitle("Edusoft");
		frm_course.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		JPanel bg4 = new JPanel();

		crss = new ImageIcon(this.getClass().getResource("background/course_background.png"));
		JLabel hv = new JLabel(crss);

		// privacy
		ImageIcon priv = new ImageIcon(this.getClass().getResource("images/edu_privacy.png"));
		privacy = new JButton(priv);
		privacy.setBorderPainted(false);
		privacy.setContentAreaFilled(false);
		privacy.setBounds(900, 675, 200, 50);
		frm_course.add(privacy);
		// Callin from login
		ret_dept.setText(ret_dept.getText());

		frm_course.add(ret_name);
		frm_course.add(ret_dept);
		frm_course.add(ret_level);
		frm_course.add(ret_matric);
		// logout
		ad = new ImageIcon(this.getClass().getResource("images/edu_log_out.png"));
		JButton logout = new JButton(ad);
		logout.setBounds(1155, 90, 112, 37);
		logout.setBorderPainted(false);
		logout.setContentAreaFilled(false);
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_course.dispose();
				frm.setVisible(true);
			}
		});
		frm_course.add(logout);

		// home
		hm = new ImageIcon(this.getClass().getResource("images/home.png"));
		home = new JButton(hm);
		home.setBounds(990, 87, 120, 37);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_course.dispose();
				frm_return.setVisible(true);
			}
		});
		frm_course.add(home);

		// Search
		srch = new ImageIcon(this.getClass().getResource("images/search.png"));
		search = new JButton(srch);
		search.setBounds(80, 550, 250, 50);
		search.setBorderPainted(false);
		search.setContentAreaFilled(false);
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchEngine se = new SearchEngine();
				se.searchIndex();
			}
		});
		frm_course.add(search);

		ButtonHandler handler = new ButtonHandler();
		// adimn panel
		ImageIcon ad = new ImageIcon(this.getClass().getResource("images/Edusoft_user.png"));
		JLabel adm = new JLabel(ad);
		adm.setBounds(1200, 30, 40, 40);
		// frm_course .add(adm);

		JLabel nn = new JLabel();
		nn.setText("Welcome");
		nn.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 35));
		nn.setBounds(97, 220, 250, 40);
		frm_course.add(nn);

		adName = new JLabel();
		adName.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 30));
		adName.setBounds(97, 260, 250, 40);
		// frm_course.add(adName);

		// edit profile picture
		edt = new ImageIcon(this.getClass().getResource("images/edit_pp.png"));
		edit_pp = new JButton(edt);
		edit_pp.setBounds(90, 330, 200, 50);
		edit_pp.setBorderPainted(false);
		edit_pp.setContentAreaFilled(false);
		frm_course.add(edit_pp);

		// course background
		c_bk = new ImageIcon(this.getClass().getResource("course_img/course_bkrg.png"));
		c_bkrg = new JLabel(c_bk);
		c_bkrg.setBounds(90, 370, 200, 50);
		// frm_course.add(c_bkrg);

		// level background
		l_bk = new ImageIcon(this.getClass().getResource("images/level_back.png"));
		l_bkrg = new JLabel(l_bk);
		l_bkrg.setBounds(53, 410, 200, 50);
		// frm_course.add(l_bkrg);

		// Synopsis
		syn = new ImageIcon(this.getClass().getResource("images/synopsis.png"));
		synopsis = new JButton(syn);
		synopsis.setBounds(90, 490, 220, 50);
		synopsis.setBorderPainted(false);
		synopsis.setContentAreaFilled(false);
		frm_course.add(synopsis);

		// back
		ImageIcon bb = new ImageIcon(this.getClass().getResource("images/Edusoft_arrow.png"));
		back = new JButton(bb);
		back.setBounds(20, 30, 40, 40);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				frm_course.dispose();
				frm_eng.setVisible(true);
			}
		});

		ImageIcon mo = new ImageIcon(this.getClass().getResource("images/Edusoft_a.png"));
		JLabel bk = new JLabel(mo);
		bk.setBounds(70, 30, 40, 40);

		// course and its backgrounds
		// Course 1
		ImageIcon y = new ImageIcon(this.getClass().getResource("course_img/course_01.png"));
		cr_1 = new JButton(y);
		cr_1.setBounds(471, 225, 602, 28);
		cr_1.setBorderPainted(false);
		cr_1.setContentAreaFilled(false);
		cr_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				courses crs = new courses();
				crs.openThatCourse();
			}
		});
		frm_course.add(cr_1);

		// Course 2
		ImageIcon lw = new ImageIcon(this.getClass().getResource("course_img/course_02.png"));
		cr_2 = new JButton(lw);
		cr_2.setBounds(471, 253, 602, 28);
		cr_2.setBorderPainted(false);
		cr_2.setContentAreaFilled(false);
		cr_2.addActionListener(handler);
		frm_course.add(cr_2);

		// Course3
		ImageIcon sc = new ImageIcon(this.getClass().getResource("course_img/course_03.png"));
		cr_3 = new JButton(sc);
		cr_3.setBounds(471, 281, 602, 28);
		cr_3.setBorderPainted(false);
		cr_3.setContentAreaFilled(false);
		cr_3.addActionListener(handler);
		frm_course.add(cr_3);

		// Course 4
		ImageIcon bs = new ImageIcon(this.getClass().getResource("course_img/course_04.png"));
		cr_4 = new JButton(bs);
		cr_4.setBounds(471, 310, 602, 28);
		cr_4.setBorderPainted(false);
		cr_4.setContentAreaFilled(false);
		frm_course.add(cr_4);

		// Course 5
		ImageIcon ld = new ImageIcon(this.getClass().getResource("course_img/course_05.png"));
		cr_5 = new JButton(ld);
		cr_5.setBounds(471, 338, 602, 28);
		cr_5.setBorderPainted(false);
		cr_5.setContentAreaFilled(false);
		frm_course.add(cr_5);

		// Course 6
		ImageIcon cr6 = new ImageIcon(this.getClass().getResource("course_img/course_06.png"));
		cr_6 = new JButton(cr6);
		cr_6.setBounds(471, 366, 602, 28);
		cr_6.setBorderPainted(false);
		cr_6.setContentAreaFilled(false);
		frm_course.add(cr_6);

		// Course 7
		ImageIcon cr7 = new ImageIcon(this.getClass().getResource("course_img/course_07.png"));
		cr_7 = new JButton(cr7);
		cr_7.setBounds(471, 394, 602, 28);
		cr_7.setBorderPainted(false);
		cr_7.setContentAreaFilled(false);
		cr_7.addActionListener(handler);
		frm_course.add(cr_7);

		// Course 8
		ImageIcon cr8 = new ImageIcon(this.getClass().getResource("course_img/course_08.png"));
		cr_8 = new JButton(cr8);
		cr_8.setBounds(471, 422, 602, 28);
		cr_8.setBorderPainted(false);
		cr_8.setContentAreaFilled(false);
		cr_8.addActionListener(handler);
		frm_course.add(cr_8);

		// Course 9
		ImageIcon cr9 = new ImageIcon(this.getClass().getResource("course_img/course_09.png"));
		cr_9 = new JButton(cr9);
		cr_9.setBounds(471, 450, 602, 28);
		cr_9.setBorderPainted(false);
		cr_9.setContentAreaFilled(false);
		frm_course.add(cr_9);

		// Course 10
		ImageIcon cr10 = new ImageIcon(this.getClass().getResource("course_img/course_10.png"));
		cr_10 = new JButton(cr10);
		cr_10.setBounds(471, 478, 602, 28);
		cr_10.setBorderPainted(false);
		cr_10.setContentAreaFilled(false);
		frm_course.add(cr_10);

		
		// Quick Links
		jr = new ImageIcon(this.getClass().getResource("links/small_journal.png"));
		journal = new JButton(jr);
		journal.setBounds(1210, 200, 70, 60);
		journal.setBorderPainted(false);
		journal.setContentAreaFilled(false);
		frm_course.add(journal);

		lb = new ImageIcon(this.getClass().getResource("links/small_library.png"));
		library = new JButton(lb);
		library.setBounds(1210, 280, 70, 60);
		library.setBorderPainted(false);
		library.setContentAreaFilled(false);
		frm_course.add(library);

		lt = new ImageIcon(this.getClass().getResource("links/small_lecture.png"));
		lecture = new JButton(lt);
		lecture.setBounds(1210, 360, 70, 60);
		lecture.setBorderPainted(false);
		lecture.setContentAreaFilled(false);
		frm_course.add(lecture);

		sm = new ImageIcon(this.getClass().getResource("links/small_seminar.png"));
		seminar = new JButton(sm);
		seminar.setBounds(1210, 445, 70, 60);
		seminar.setBorderPainted(false);
		seminar.setContentAreaFilled(false);
		frm_course.add(seminar);

		rc = new ImageIcon(this.getClass().getResource("links/small_recent.png"));
		recent = new JButton(rc);
		recent.setBounds(1210, 530, 80, 63);
		recent.setBorderPainted(false);
		recent.setContentAreaFilled(false);
		frm_course.add(recent);

		bg4.add(hv);
		frm_course.add(back);
		frm_course.add(bk);
		bg4.setBackground(new Color(233, 255, 189));
		frm_course.add(bg4);
		frm_course.setSize(1370, 750);
		frm_course.setVisible(true);
		frm_course.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm_course.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No", "Return To Home" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					} else if (PromptResult == JOptionPane.CANCEL_OPTION) {
						frm_course.dispose();
						returnHomePage();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	// homepage menu Items
	protected void returnHomePage() {

		frm_return = new JFrame();
		frm_return.setTitle("Edusoft");
		frm_return.setIconImage(new ImageIcon(getClass().getResource("images/edu_ico.png")).getImage());
		bg2 = new JPanel();

		ImageIcon yo = new ImageIcon(this.getClass().getResource("background/edu_edu.png"));
		JLabel hb = new JLabel(yo);

		HandleMe handler = new HandleMe();

		// privacy
		ImageIcon priv = new ImageIcon(this.getClass().getResource("images/edu_privacy.png"));
		privacy = new JButton(priv);
		privacy.setBorderPainted(false);
		privacy.setContentAreaFilled(false);
		privacy.setBounds(900, 675, 200, 50);
		frm_return.add(privacy);
		// logout
		ad = new ImageIcon(this.getClass().getResource("images/edu_log_out.png"));
		JButton adm = new JButton(ad);
		adm.setBounds(1155, 90, 112, 37);
		adm.setBorderPainted(false);
		adm.setContentAreaFilled(false);
		adm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frm_return.dispose();
				frm.setVisible(true);
			}
		});
		frm_return.add(adm);

		ImageIcon lg = new ImageIcon(this.getClass().getResource("images/Edusoft_logo.png"));
		JLabel des = new JLabel(lg);
		des.setBounds(1100, 650, 250, 50);
		// frm_home.add(des);

		// administration
		JLabel nn = new JLabel();
		nn.setText("Welcome");
		nn.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 35));
		nn.setBounds(97, 220, 250, 40);
		frm_return.add(nn);

		adName = new JLabel();
		adName.setFont(new Font("SanSerif", Font.BOLD | Font.ITALIC, 30));
		adName.setBounds(97, 260, 250, 40);
		adName.setText(ret_name.getText());
		// frm_home.add(adName);

		// home
		hm = new ImageIcon(this.getClass().getResource("images/home_02.png"));
		home = new JButton(hm);
		home.setBounds(990, 88, 120, 37);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		// frm_home.add(home);

		// Search
		srch = new ImageIcon(this.getClass().getResource("images/search.png"));
		search = new JButton(srch);
		search.setBounds(80, 550, 250, 50);
		search.setBorderPainted(false);
		search.setContentAreaFilled(false);
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SearchEngine se = new SearchEngine();
				se.searchIndex();
			}
		});
		frm_return.add(search);

		// name
		get_name = new JLabel();
		get_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		get_name.setText(ret_name.getText());
		get_name.setBounds(97, 260, 250, 40);
		frm_return.add(get_name);

		// course background

		c_bkrg = new JLabel();
		c_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		c_bkrg.setText(ret_dept.getText());
		c_bkrg.setBounds(90, 330, 200, 50);
		frm_return.add(c_bkrg);

		// level background

		l_bkrg = new JLabel();
		l_bkrg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		l_bkrg.setText(ret_level.getText());
		l_bkrg.setBounds(94, 370, 200, 50);
		frm_return.add(l_bkrg);

		// Matric
		get_mat = new JLabel();
		get_mat.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		get_mat.setText(ret_matric.getText());
		get_mat.setBounds(94, 410, 300, 50);
		frm_return.add(get_mat);
		// Synopsis
		syn = new ImageIcon(this.getClass().getResource("images/synopsis.png"));
		synopsis = new JButton(syn);
		synopsis.setBounds(90, 460, 220, 50);
		synopsis.setBorderPainted(false);
		synopsis.setContentAreaFilled(false);
		frm_return.add(synopsis);

		// back
		// ImageIcon bb = new
		// ImageIcon(this.getClass().getResource("images/Edusoft_arrow.png"));
		// back = new JButton(bb);
		// back.setBounds(20, 30, 40, 40);
		// back.addActionListener(handler);

		// ImageIcon mo = new
		// ImageIcon(this.getClass().getResource("images/Edusoft_a.png"));
		// JLabel bk = new JLabel(mo);
		// bk.setBounds(70, 30, 40, 40);

		// engineering
		ImageIcon y = new ImageIcon(this.getClass().getResource("colleges/edu_engineering.png"));
		eng = new JButton(y);
		eng.setBounds(440, 270, 145, 160);
		eng.setBorderPainted(false);
		eng.setContentAreaFilled(false);
		eng.addActionListener(handler);
		frm_return.add(eng);

		// Law
		ImageIcon lw = new ImageIcon(this.getClass().getResource("colleges/edu_leader.png"));
		b_law = new JButton(lw);
		b_law.setBounds(615, 265, 160, 170);
		b_law.setBorderPainted(false);
		b_law.setContentAreaFilled(false);
		b_law.addActionListener(handler);
		frm_return.add(b_law);

		// sciences
		ImageIcon sc = new ImageIcon(this.getClass().getResource("colleges/edu_science.png"));
		b_sc = new JButton(sc);
		b_sc.setBounds(800, 270, 145, 160);
		b_sc.setBorderPainted(false);
		b_sc.setContentAreaFilled(false);
		b_sc.addActionListener(handler);
		frm_return.add(b_sc);

		// The lower level of the menus involving other faculties
		// business
		ImageIcon bs = new ImageIcon(this.getClass().getResource("colleges/edu_business.png"));
		JButton b_bs = new JButton(bs);
		b_bs.setBounds(980, 270, 145, 160);
		b_bs.setBorderPainted(false);
		b_bs.setContentAreaFilled(false);
		frm_return.add(b_bs);

		// leadership
		ImageIcon ld = new ImageIcon(this.getClass().getResource("colleges/edu_post.png"));
		JButton b_lead = new JButton(ld);
		b_lead.setBounds(1160, 265, 160, 170);
		b_lead.setBorderPainted(false);
		b_lead.setContentAreaFilled(false);
		frm_return.add(b_lead);

		// Quick Links
		jr = new ImageIcon(this.getClass().getResource("links/edu_journal.png"));
		journal = new JButton(jr);
		journal.setBounds(440, 500, 145, 160);
		journal.setBorderPainted(false);
		journal.setContentAreaFilled(false);
		frm_return.add(journal);

		lb = new ImageIcon(this.getClass().getResource("links/edu_library.png"));
		library = new JButton(lb);
		library.setBounds(590, 500, 145, 160);
		library.setBorderPainted(false);
		library.setContentAreaFilled(false);
		frm_return.add(library);

		lt = new ImageIcon(this.getClass().getResource("links/edu_lecture.png"));
		lecture = new JButton(lt);
		lecture.setBounds(730, 500, 145, 160);
		lecture.setBorderPainted(false);
		lecture.setContentAreaFilled(false);
		frm_return.add(lecture);

		sm = new ImageIcon(this.getClass().getResource("links/edu_seminar.png"));
		seminar = new JButton(sm);
		seminar.setBounds(870, 500, 145, 160);
		seminar.setBorderPainted(false);
		seminar.setContentAreaFilled(false);
		frm_return.add(seminar);

		rc = new ImageIcon(this.getClass().getResource("links/edu_recent.png"));
		recent = new JButton(rc);
		recent.setBounds(1010, 500, 145, 160);
		recent.setBorderPainted(false);
		recent.setContentAreaFilled(false);
		frm_return.add(recent);

		bg2.add(hb);
		// frm_home.add(back);
		// frm_home.add(bk);
		frm_return.add(bg2);
		frm_return.setSize(1370, 759);
		frm_return.setBackground(Color.blue);
		frm_return.setVisible(true);
		frm_return.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frm_return.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?", "Edusoft",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	// set actions to all buttons in frame
	public class HandleMe implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == back) {
				frm_home.setVisible(false);
				frm.setVisible(true);
				queryMe();
			} else if (e.getSource() == eng) {
				frm_return.dispose();
				openEng();

			} else if (e.getSource() == b_law) {

			} else if (e.getSource() == b_sc) {

			}
		}
	}

	public static void main(String[] args) {
		Splash_Screen spl = new Splash_Screen(8000);
		spl.showSplashAndExit();
		try {
			// UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			index ld = new index();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
