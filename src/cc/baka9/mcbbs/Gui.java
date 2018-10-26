package cc.baka9.mcbbs;

import java.awt.EventQueue;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cc.baka9.mcbbs.commands.CommandHandler;
import cc.baka9.mcbbs.utils.TimeUtil;

public class Gui {
	public static JFrame menu;
	public static JPanel panel;
	public static JTextArea text;
	public static JScrollPane scroll;
	public static JTextField cmdInput;

	public static void showMneu(String title, String msg) {
		menu = new JFrame(title);
		try {
			menu.setIconImage(ImageIO.read(Gui.class.getResource("/Img/Icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		panel = new JPanel();
		text = new JTextArea(12, 35);
		scroll = new JScrollPane(text);
		cmdInput = new JTextField(35);
		text.setText(msg);
		cmdInput.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					String cmdStr = cmdInput.getText();
					cmdInput.setText("");
					println(cmdStr);
					println(CommandHandler.execute(cmdStr));
				}

			}
		});
		final JScrollBar jscrollBar = scroll.getVerticalScrollBar();
		jscrollBar.addAdjustmentListener(new AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent evt) {
				if (evt.getAdjustmentType() == AdjustmentEvent.TRACK) {
					jscrollBar.setValue(jscrollBar.getMaximum() - jscrollBar.getModel().getExtent());
				}
			}

		});

		menu.setSize(400, 300);
		menu.setResizable(false);
		menu.setLocationRelativeTo(null);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.add(scroll);
		panel.add(cmdInput);
		menu.add(panel);
		text.setLineWrap(true);
		text.setEditable(false);
		menu.setVisible(true);
	}

	private static File log = new File(new File("").getAbsolutePath(), "logger.log");

	public static void println(final String text) {
		if (text == null || text.trim().isEmpty()) return;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				String forMatText = TimeUtil.getFormatNow() + ": " + text + "\n";
				Gui.text.append(forMatText);
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(log, true))) {
					bw.write(forMatText);
					bw.newLine();
				} catch (Exception e) {
				}
			}
		});
	}

	public static void syncPrintln(String threadName, final String text) {
		println(threadName + " " + text);
	}
}
