package views;

import java.awt.BorderLayout;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MainPanel extends JPanel{
	
	private MainOptionsPanel mainOptionsPanel;
	private LabelPanel labelPanel;
	
	public MainPanel(boolean admin) {
		initComponents(admin);
	}
	
	private void initComponents(boolean admin) {
		setLayout(new BorderLayout());
		mainOptionsPanel = new MainOptionsPanel(admin);
		int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		labelPanel = new LabelPanel("/img/SabroPanLogo.jpeg",width/6,height/6);
		add(labelPanel, BorderLayout.NORTH);
		add(mainOptionsPanel, BorderLayout.CENTER);
	}

}
