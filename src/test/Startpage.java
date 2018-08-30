package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Startpage extends JFrame {

	Oldbook ob = new Oldbook();
	Newbook nb = new Newbook();
	Mainpage window = new Mainpage();

	private JPanel contentPane;
	static Startpage sp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Startpage frame = new Startpage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Startpage() {
		setForeground(new Color(240, 128, 128));
		setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 16));
		setTitle("INTERFACE 書籍管理システム");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 240, 245));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//会社ロゴ
		JLabel logo1Label = new JLabel("");
		logo1Label.setIcon(new ImageIcon("C:\\workspacekotone\\test\\Saved Pictures\\IF_Logo_200.png"));
		logo1Label.setBounds(114, 10, 209, 44);
		contentPane.add(logo1Label);

		//社内管理ラベル
		JLabel maintitleLabel = new JLabel("社内書籍管理システム");
		maintitleLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		maintitleLabel.setBounds(136, 53, 161, 28);
		contentPane.add(maintitleLabel);

		//本を売るボタン
		JButton sellButton = new JButton("本を売る");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ob.setVisible(true);


			}
		});
		sellButton.setBackground(new Color(255, 182, 193));
		sellButton.setForeground(Color.WHITE);
		sellButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		sellButton.setBounds(28, 156, 99, 50);
		contentPane.add(sellButton);

		//書籍一覧ラベル
		JButton jumptomainButton = new JButton("書籍一覧");
		jumptomainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mainpage window = new Mainpage();
				window.frmInterface.setVisible(true);


			}
		});
		jumptomainButton.setForeground(new Color(255, 255, 255));
		jumptomainButton.setBackground(new Color(255, 182, 193));
		jumptomainButton.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		jumptomainButton.setBounds(167, 157, 99, 50);
		contentPane.add(jumptomainButton);

		//書籍追加ボタン
		JButton jumptoadd = new JButton("書籍追加");
		jumptoadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				nb.setVisible(true);

			}
		});
		jumptoadd.setBackground(new Color(255, 182, 193));
		jumptoadd.setForeground(new Color(255, 255, 255));
		jumptoadd.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
		jumptoadd.setBounds(304, 157, 99, 50);
		contentPane.add(jumptoadd);
	}
}
