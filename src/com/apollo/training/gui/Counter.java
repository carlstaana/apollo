package com.apollo.training.gui;

import java.awt.Button;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Counter extends Frame {

	private Label lbl;
	
	private TextField txtOutput;
	
	private Button btnUp;
	
	private Button btnDown;
	
	private Button btnReset;
	
	private int count = 0;
	
	public Counter() {
		setLayout(new FlowLayout());
		// frame properties
		setTitle("Carl's First Frame");
		setSize(600, 400);
		setLocation(200, 200);
		setVisible(true);
		setResizable(false);
		
		// listener for CLOSE
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// add components to the panel
		lbl = new Label("Counter");
		add(lbl);
		
		txtOutput = new TextField("0", 10);
		txtOutput.setEditable(false);
		add(txtOutput);
		
		btnUp = new Button("Count up!");
		add(btnUp);
		btnDown = new Button("Count down");
		btnDown.setEnabled(false);
		add(btnDown);
		btnReset = new Button("Reset");
		btnReset.setEnabled(false);
		add(btnReset);
		
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("/home/apollo/Desktop/virtualdirectory/poea3.jpg"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		add(new JLabel(new ImageIcon(image)));
		
		// Button Listener
		BtnListener btnListener = new BtnListener();
		btnUp.addActionListener(btnListener);
		btnDown.addActionListener(btnListener);
		btnReset.addActionListener(btnListener);
	}
	
	private class BtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "Count up!":
				count++;
				txtOutput.setText(String.valueOf(count));
				if (count > 0) {
					btnDown.setEnabled(true);
					btnReset.setEnabled(true);
				}
				break;
			case "Count down":
				count--;
				txtOutput.setText(String.valueOf(count));
				if (count <= 0) {
					btnDown.setEnabled(false);
					btnReset.setEnabled(false);
				}
				break;
			case "Reset":
				count = 0;
				txtOutput.setText("0");
				btnDown.setEnabled(false);
				btnReset.setEnabled(false);
				break;
			}
		}
	}

	public static void main(String[] args) {
		new Counter();
	}
}
