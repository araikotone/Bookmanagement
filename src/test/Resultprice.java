package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Resultprice extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField resulttextField;
	private JTable table;
	@SuppressWarnings("unused")
	private JSpinner g1Spinner;
	@SuppressWarnings("unused")
	private JSpinner p1Spinner;
	Resultprice rs;
	private String chkPrice = "";

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			Resultprice dialog = new Resultprice();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
     * Create the dialog.
     * @param val 呼び出し画面からの引数
     */
    public Resultprice(String chkPrice) {
    	this.chkPrice = chkPrice;
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\孝司\\Pictures\\tempsnip.png"));
        setBounds(100, 100, 450, 433);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblNewLabel = new JLabel("あなたの本の査定金額は");
            lblNewLabel.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
            lblNewLabel.setBounds(24, 81, 169, 33);
            contentPanel.add(lblNewLabel);
        }
        {
            resulttextField = new JTextField();
            resulttextField.setFont(new Font("Mongolian Baiti", Font.PLAIN, 14));
            resulttextField.setBounds(205, 85, 96, 27);
            contentPanel.add(resulttextField);
            resulttextField.setColumns(10);
            resulttextField.setText(this.chkPrice);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("円です");
            lblNewLabel_1.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 15));
            lblNewLabel_1.setBounds(327, 85, 75, 24);
            contentPanel.add(lblNewLabel_1);
        }
        {
            JLabel lblNewLabel_2 = new JLabel("結果");
            lblNewLabel_2.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 20));
            lblNewLabel_2.setBounds(189, 10, 40, 27);
            contentPanel.add(lblNewLabel_2);
        }
        {
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(12, 186, 410, 42);
            contentPanel.add(scrollPane);
            {
                table = new JTable();
                table.setForeground(new Color(255, 255, 255));
                table.setBackground(new Color(72, 61, 139));
                table.setFont(new Font("UD デジタル 教科書体 NP-R", Font.PLAIN, 12));
                table.setModel(new DefaultTableModel(
                    new Object[][] {
                        {"小数点以下は切り捨てとする", "70%", "65%", "60%", "50%"},
                    },
                    new String[] {
                        "買取価格目安表", "Java", "MySQL", "C++", "Other"
                    }
                ) {
                    boolean[] columnEditables = new boolean[] {
                        false, false, false, false, false
                    };
                    public boolean isCellEditable(int row, int column) {
                        return columnEditables[column];
                    }
                });
                table.getColumnModel().getColumn(0).setPreferredWidth(149);
                table.getColumnModel().getColumn(1).setPreferredWidth(40);
                table.getColumnModel().getColumn(2).setPreferredWidth(46);
                table.getColumnModel().getColumn(3).setPreferredWidth(41);
                table.getColumnModel().getColumn(4).setPreferredWidth(44);
                scrollPane.setViewportView(table);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setBackground(Color.DARK_GRAY);
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

        }
    }
}