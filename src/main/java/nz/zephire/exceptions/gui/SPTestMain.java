package nz.zephire.exceptions.gui;

import nz.zephire.exceptions.Utils;
import nz.zephire.exceptions.postback.SharePointPostback;
import nz.zephire.exceptions.postback.fields.FieldName;
import nz.zephire.exceptions.postback.fields.SPField;
import nz.zephire.exceptions.postback.fields.SPHeader;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SPTestMain {

    private JTextField username;
    private JTextField password;
    private JFrame testWindow;

    public static void main(String[] args) {
        new SPTestMain();
    }

    private SPTestMain() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        testWindow = new JFrame("SPTest");
        testWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel labels = new JPanel(new GridLayout(0, 1));

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        labels.add(usernameLabel);
        labels.add(passwordLabel);

        testWindow.getContentPane().add(labels, BorderLayout.WEST);

        JPanel fields = new JPanel(new GridLayout(0, 1));

        username = new JTextField(50);
        password = new JPasswordField(50);

        fields.add(username);
        fields.add(password);

        testWindow.getContentPane().add(fields, BorderLayout.EAST);

        JButton submit = new JButton("Submit");
        submit.addActionListener(getSubmitListener());

        testWindow.getContentPane().add(submit, BorderLayout.SOUTH);

        testWindow.pack();
        testWindow.setVisible(true);
    }

    private ActionListener getSubmitListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    Utils.setAuth(username.getText(), password.getText().toCharArray());

                    Utils.debugLog("submit");
                    //                SharePointPostback sp = new SharePointPostback(new FakeBackend());
                    SharePointPostback sp = new SharePointPostback();

                    try {
                        Utils.debugLog("start init");
                        sp.init();
                        Utils.debugLog("init complete");
                    } catch (IOException except) {
                        Utils.debugLog("init failed");
                        Utils.debugLog(except);

                        JOptionPane.showMessageDialog(testWindow, except.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
                    }

                    sp.add(new SPHeader(sp.getFormData()));

                    // Add fields
                    sp.add(new SPField(FieldName.SUBMITTED_FOR, "brynley.mcdonald"));
                    sp.add(new SPField(FieldName.SUBMITTED_BY, "brynley.mcdonald"));
                    sp.add(new SPField(FieldName.APPROVER, "Deneshan%20Naidoo"));
                    sp.add(new SPField(FieldName.DATE, "2017-10-29"));
                    sp.add(new SPField(FieldName.REASON, "Other"));
                    sp.add(new SPField(FieldName.DESCRIPTION, "TestPlzIgnore"));
                    sp.add(new SPField(FieldName.NEW_START, "12%3A00%3A00"));
                    sp.add(new SPField(FieldName.NEW_END, "12%3A30%3A00"));
                    sp.add(new SPField(FieldName.SUBMIT, null));

                    try {
                        Utils.debugLog("start post");
                        sp.post();
                        Utils.debugLog("post complete");

                        JOptionPane.showMessageDialog(testWindow, "Success!");
                    } catch (IOException except) {
                        Utils.debugLog("post failed");
                        Utils.debugLog(except);
                        JOptionPane.showMessageDialog(testWindow, except.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception except) {
                    Utils.debugLog("Unexpected error");
                    Utils.debugLog(except);
                    JOptionPane.showMessageDialog(testWindow, except.getMessage(), "Failure", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
    }
}
