package com.kianama3.console.sections.clients.management;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import com.kianama3.console.common.KianamaResourceBundle;
import com.kianama3.console.common.RemoteServerAccess;
import com.kianama3.console.calendar.PersianDateChooser;
import com.kianama3.server.remote.common.ClientData;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddEditClientDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTextField nameField;
	private JTextField serialField;
	private JTextPane descriptionField;
	private JButton btnCancel;
	private JButton btnAdd;
	private JLabel lblSerial;
	private JLabel lblDescription;
	private JLabel lblName;
	private JTextField disabledDateField;
	private JButton disabledDateButton;
	private JCheckBox disabledCheckBox;
	private final JDialog dlg = this;
	private GridBagLayout gridBagLayout;
	public enum DialogType {AddDialog,EditDialog};
	public enum ActionType {Approved,Canceled};
	private DialogType dialogType;
	public ActionType userAction;
	private String clientName;

	public AddEditClientDialog(){
		buildDialog();
		
		setTitle(KianamaResourceBundle.getString("add_client_title"));
		btnAdd.setText(KianamaResourceBundle.getString("add_label"));
		dialogType = DialogType.AddDialog;
	}
	
	public AddEditClientDialog(ClientData rec){
		buildDialog();
		nameField.setText(rec.clientName);
		descriptionField.setText(rec.clientDesc);
		serialField.setText(rec.clientSerial);
		disabledCheckBox.setSelected(rec.clientState == 2);
		disabledDateField.setText(rec.disableDateTime);
		
		setTitle(KianamaResourceBundle.getString("edit_client_title"));
		btnAdd.setText(KianamaResourceBundle.getString("save_label"));
		dialogType = DialogType.EditDialog;
		clientName = rec.clientName;
	}

	private void buildDialog() {
		builldLayout();
		buildNameLabel();
		buildNameField();
		buildDescriptionLabel();
		buildDescriptionField();
		buildSeialLabel();
		buildSeialField();
		buildDisabledCheckBox();
		buildDisabledDateField();
		buildDisabledDateButton();	
		buildAddButton();
		buildCancelButton();
		
		//pack();
		setResizable(false);
		getRootPane().setDefaultButton(btnAdd);
		setModal(true);
	}

	private void builldLayout() {
		gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{51, 16, 108, 71, 78, 0};
		gridBagLayout.rowHeights = new int[]{20, 87, 20, 0, 23, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private void buildNameLabel() {
		lblName = new JLabel(KianamaResourceBundle.getString("client_name_label"));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		getContentPane().add(lblName, gbc_lblName);
	}

	private void buildNameField() {
		nameField = new JTextField();
		nameField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.anchor = GridBagConstraints.NORTH;
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(5, 0, 5, 5);
		gbc_nameField.gridwidth = 4;
		gbc_nameField.gridx = 1;
		gbc_nameField.gridy = 0;
		getContentPane().add(nameField, gbc_nameField);
		nameField.setColumns(10);
	}

	private void buildDescriptionLabel() {
		lblDescription = new JLabel(KianamaResourceBundle.getString("description_label"));
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 1;
		getContentPane().add(lblDescription, gbc_lblDescription);
	}

	private void buildDescriptionField() {
		descriptionField = new JTextPane();
		GridBagConstraints gbc_descriptionField = new GridBagConstraints();
		gbc_descriptionField.fill = GridBagConstraints.BOTH;
		gbc_descriptionField.insets = new Insets(0, 0, 5, 0);
		gbc_descriptionField.gridwidth = 4;
		gbc_descriptionField.gridx = 1;
		gbc_descriptionField.gridy = 1;
		getContentPane().add(descriptionField, gbc_descriptionField);
	}

	private void buildSeialLabel() {
		lblSerial = new JLabel(KianamaResourceBundle.getString("serial_label"));
		GridBagConstraints gbc_lblSerial = new GridBagConstraints();
		gbc_lblSerial.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblSerial.insets = new Insets(0, 0, 5, 5);
		gbc_lblSerial.gridx = 0;
		gbc_lblSerial.gridy = 2;
		getContentPane().add(lblSerial, gbc_lblSerial);
	}

	private void buildSeialField() {
		serialField = new JTextField();
		serialField.setBackground(new Color(255, 255, 153));
		GridBagConstraints gbc_serialField = new GridBagConstraints();
		gbc_serialField.anchor = GridBagConstraints.NORTH;
		gbc_serialField.fill = GridBagConstraints.HORIZONTAL;
		gbc_serialField.insets = new Insets(0, 0, 5, 5);
		gbc_serialField.gridwidth = 4;
		gbc_serialField.gridx = 1;
		gbc_serialField.gridy = 2;
		getContentPane().add(serialField, gbc_serialField);
		serialField.setColumns(10);
	}

	private void buildDisabledCheckBox() {
		disabledCheckBox = new JCheckBox(KianamaResourceBundle.getString("disabled_label"));
		disabledCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					disabledDateField.setEnabled(true);
					disabledDateButton.setEnabled(true);
				}else{
					disabledDateField.setEnabled(false);
					disabledDateButton.setEnabled(false);
				}
			}
		});
		GridBagConstraints gbc_disabledCheckBox = new GridBagConstraints();
		gbc_disabledCheckBox.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_disabledCheckBox.insets = new Insets(0, 5, 5, 5);
		gbc_disabledCheckBox.gridx = 0;
		gbc_disabledCheckBox.gridy = 3;
		getContentPane().add(disabledCheckBox, gbc_disabledCheckBox);
	}

	private void buildDisabledDateField() {
		disabledDateField = new JTextField();
		disabledDateField.setToolTipText(KianamaResourceBundle.getString("disable_date_tip"));
		disabledDateField.setEnabled(false);
		GridBagConstraints gbc_disabledDateField = new GridBagConstraints();
		gbc_disabledDateField.gridwidth = 2;
		gbc_disabledDateField.insets = new Insets(0, 0, 5, 5);
		gbc_disabledDateField.fill = GridBagConstraints.HORIZONTAL;
		gbc_disabledDateField.gridx = 1;
		gbc_disabledDateField.gridy = 3;
		getContentPane().add(disabledDateField, gbc_disabledDateField);
		disabledDateField.setColumns(10);
	}

	private void buildDisabledDateButton() {
		disabledDateButton = new JButton("");
		disabledDateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersianDateChooser dateChooser = new PersianDateChooser((JDialog)null, "Select Date");
				disabledDateField.setText(dateChooser.select());
			}
		});
		disabledDateButton.setEnabled(false);
		disabledDateButton.setIcon(new ImageIcon(KianamaResourceBundle.getResource("1347532287_config-date.png")));
		GridBagConstraints gbc_disabledDateButton = new GridBagConstraints();
		gbc_disabledDateButton.insets = new Insets(0, 0, 5, 5);
		gbc_disabledDateButton.gridx = 3;
		gbc_disabledDateButton.gridy = 3;
		getContentPane().add(disabledDateButton, gbc_disabledDateButton);
	}

	private void buildAddButton() {
		btnAdd = new JButton(KianamaResourceBundle.getString("add_label"));
		btnAdd.addActionListener(onAddButtonClicked());
		GridBagConstraints gbc_btnOk = new GridBagConstraints();
		gbc_btnOk.anchor = GridBagConstraints.NORTH;
		gbc_btnOk.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOk.insets = new Insets(0, 0, 5, 5);
		gbc_btnOk.gridx = 3;
		gbc_btnOk.gridy = 4;
		getContentPane().add(btnAdd, gbc_btnOk);
	}

	private void buildCancelButton() {
		btnCancel = new JButton(KianamaResourceBundle.getString("cancel_label"));
		btnCancel.addActionListener(onCancelButtonClicked());
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancel.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnCancel.gridx = 4;
		gbc_btnCancel.gridy = 4;
		getContentPane().add(btnCancel, gbc_btnCancel);
	}

	private ActionListener onCancelButtonClicked(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				userAction = ActionType.Canceled;
				dlg.dispose();
			}
		};
	}

	private ActionListener onAddButtonClicked() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(nameField.getText().trim().isEmpty()||
						serialField.getText().trim().isEmpty())
						JOptionPane.showMessageDialog(null, 
								KianamaResourceBundle.getString("notify_mandatory_fields_text"),
								KianamaResourceBundle.getString("error_title"),
								JOptionPane.ERROR_MESSAGE);
					else{						
						ClientData c = new ClientData();
						c.clientName = nameField.getText();
						c.clientDesc = descriptionField.getText();
						c.clientSerial = serialField.getText();
						c.clientState = disabledCheckBox.isSelected() ? 2 : 1;
						c.disableDateTime = disabledDateField.getText();
						
						switch(dialogType){
							case AddDialog:
								RemoteServerAccess.addNewClient(c);
								break;
							case EditDialog:
								RemoteServerAccess.editClient(clientName,c);
								break;
						}
						userAction = ActionType.Approved;
						dlg.dispose();
					}
				}catch(Exception exp){
					//already handled, just to bypass dlg.dispose() method
				}
			}
		};
	}
}
