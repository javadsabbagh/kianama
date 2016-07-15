package com.kianama3.console.sections.server;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

import com.kianama3.console.common.KianamaResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Timer;
import java.util.TimerTask;

public class ServerManagementTabPage extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel m_stateLabel;
	private JLabel lblNewLabel;
	private JButton btnStart;
	private JLabel stateLabel;
	private JButton btnStop;
	private JButton btnRestart;


	public ServerManagementTabPage() {
		initialize();
	}


	private void initialize() {
        this.setLayout(null);

		lblNewLabel = new JLabel(KianamaResourceBundle.getString("current_state_label"));
		lblNewLabel.setBounds(6, 10, 134, 14);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		this.add(lblNewLabel);

		btnStart = new JButton(KianamaResourceBundle.getString("start_label"));
		btnStart.setBounds(6, 47, 69, 23);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startService();
			}
		});

		stateLabel = new JLabel("Server is running ...");
		stateLabel.setBounds(150, 10, 273, 14);
		m_stateLabel = stateLabel;
		int rs = queryService();
		if (rs != 4) {
			// TODO fetch the last stopped log date from database
			// Add watch timer to program
			stateLabel
					.setText("<html>Server is <font color='red'>not running</font> from ...</html>");
		} else {
			// TODO fetch the last running log date from database
			// Add watch timer to program
			stateLabel
					.setText("<html>Server is <font color='blue'>running</font> from ...</html>");
		}
		this.add(stateLabel);
		this.add(btnStart);

		btnStop = new JButton(KianamaResourceBundle.getString("stop_label"));
		btnStop.setBounds(85, 47, 55, 23);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopService();
			}
		});
		this.add(btnStop);

		btnRestart = new JButton(KianamaResourceBundle.getString("restart_label"));
		btnRestart.setBounds(161, 47, 69, 23);
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartService();
			}
		});
		this.add(btnRestart);

		Timer timer = new Timer("Printer");
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				int rs = queryService();
				switch (rs) {
				case 1:
					m_stateLabel.setText(KianamaResourceBundle.getString("server_stopped_label"));
					break;
				case 4:
					m_stateLabel.setText(KianamaResourceBundle.getString("server_running_label"));
					break;
				}
			}
		}, 0, 2000); // from now to every 2 seconds

	}

	public boolean startService() {
		int res = queryService();
		if (res == -100) {
			JOptionPane.showMessageDialog(this,KianamaResourceBundle.getString("service_is_not_installed_label")
					, KianamaResourceBundle.getString("error_label"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (res != 1) { // in non-stopped state
			JOptionPane.showMessageDialog(this,
					KianamaResourceBundle.getString("server_is_already_started_label"), KianamaResourceBundle.getString("error_label"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				String[] start = { "cmd.exe", "/c", "sc", "start", "Kianama" };
				Runtime runtime = Runtime.getRuntime();
				/* Process process = */runtime.exec(start);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						KianamaResourceBundle.getString("error_in_process_exec_label"), KianamaResourceBundle.getString("error_label"), 
						JOptionPane.ERROR_MESSAGE);
				return false;
			}
			// wait for 2 seconds
			long st = System.currentTimeMillis();
			long end = st + 2 * 1000; // 5 seconds * 1000 ms/sec
			while (System.currentTimeMillis() < end) {
				// do nothing
			}
			// query to see whether it is started or no
			res = queryService();
			if (res == 4) {
				JOptionPane.showMessageDialog(this,
						KianamaResourceBundle.getString("server_started_successfully_label"), KianamaResourceBundle.getString("information_label"),
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else {
				JOptionPane
						.showMessageDialog(
								this,
								KianamaResourceBundle.getString("wait_to_start_label"),
								KianamaResourceBundle.getString("information_label"), JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
	}
    
	@SuppressWarnings("finally")
	public int queryService() {
		int state = -1; // invalid state
		/*
		 * return the state of the service 1: STOPPED 2: START_PENDING 3:
		 * STOP_PENDING 4: RUNNING ....
		 */
		try {

			final String SERVICE_NAME = "Kianama";
			// query about a specific service
			String[] query = { "cmd.exe", "/c", "sc", "query", SERVICE_NAME,
					"|", "find", "/i", "\"state\"" };
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(query);
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			line = br.readLine();
			if (line == null)
				state = -100; // service is not installed
			else
				while (line != null) {
					Pattern pt = Pattern.compile("\\d+"); // just numbers
					Matcher mt = pt.matcher(line);

					// important note : groupCount() is not as you might think.
					// it's
					// the guarantee of $ operator

					if (mt.find()) {
						state = Integer.parseInt(mt.group()); // mt.group() =
																// line.substring(mt.start(),mt.end())
					}
					line = br.readLine();
				}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			return state;
		}
	}

	public boolean stopService() {
		int res = queryService();
		if (res == -100) {
			JOptionPane.showMessageDialog(this,
					KianamaResourceBundle.getString("service_is_not_installed_label"), 
					KianamaResourceBundle.getString("error_label"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		} else if (res != 4) { // in non-started state
			JOptionPane
					.showMessageDialog(this,
							KianamaResourceBundle.getString("service_is_already_stopped_label"), 
							KianamaResourceBundle.getString("error_label"),
							JOptionPane.ERROR_MESSAGE);
			return false;
		} else {
			try {
				String[] stop = { "cmd.exe", "/c", "sc", "stop", "Kianama" };
				Runtime runtime = Runtime.getRuntime();
				/* Process process = */runtime.exec(stop);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this,
						KianamaResourceBundle.getString("error_in_stopping_server_label"), 
						KianamaResourceBundle.getString("error_label"),
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			// wait for 2 seconds
			long st = System.currentTimeMillis();
			long end = st + 2 * 1000; // 2 seconds * 1000 ms/sec
			while (System.currentTimeMillis() < end) {
				// do nothing
			}

			// query to see whether it is stopped or not
			res = queryService();
			if (res == 1) {
				JOptionPane.showMessageDialog(this,
						KianamaResourceBundle.getString("server_stopped_successfully_label"),
						KianamaResourceBundle.getString("information_label"),
						JOptionPane.INFORMATION_MESSAGE);
				return true;
			} else {

				JOptionPane
						.showMessageDialog(
								this,
								KianamaResourceBundle.getString("wait_to_start_label"),
								KianamaResourceBundle.getString("information_label"),
							    JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
	}

	public boolean restartService() {
		try {
			String[] stop = { "cmd.exe", "/c", "sc", "stop", "Kianama" };
			String[] start = { "cmd.exe", "/c", "sc", "start", "Kianama" };
			Runtime runtime = Runtime.getRuntime();
			runtime.exec(stop);
			runtime.exec(start);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,
					KianamaResourceBundle.getString("error_in_restarting_server_label"),
					KianamaResourceBundle.getString("error_label"),
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// wait for 2 seconds
		long st = System.currentTimeMillis();
		long end = st + 2 * 1000; // 2 seconds * 1000 ms/sec
		while (System.currentTimeMillis() < end) {
			// do nothing
		}

		// query to see whether it is stopped or not
		int res = queryService();
		if (res == 4) {
			JOptionPane.showMessageDialog(this,
					KianamaResourceBundle.getString("server_restarted_successfully_label"), 
					KianamaResourceBundle.getString("information_labbel"),
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} else {

			JOptionPane.showMessageDialog(this,
					KianamaResourceBundle.getString("wait_to_start_label"),
					KianamaResourceBundle.getString("information_label"),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}
