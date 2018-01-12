package yutongz1997.auto2048.gui;

import java.util.Hashtable;
import java.awt.Container;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.WindowConstants;

import yutongz1997.auto2048.lib.Configurations;
import yutongz1997.auto2048.lib.GeneralGUIConstants;
import yutongz1997.auto2048.lib.SettingsConstants;


/**
 * The class of the settings dialog.
 * @author Yutong Zhang
 */
class SettingsDialog extends JDialog {
    // Some (shared between methods) GUI Components, e.g. panels, text field, etc..
    private JPanel panelGeneral;
    private JPanel panelAutoPlay;
    private JPanel subPanelButton;
    private JTextField fieldPlayerName;
    private JSlider sliderSleepTime;
    private JSpinner spinnerSearchLevel;
    // The configuration of the game. This will be passed back to the game's window
    // to apply all settings.
    private Configurations config;


    /**
     * Constructs the settings dialog.
     * @param config the current configurations of the game
     */
    SettingsDialog(Configurations config) {
        this.config = config;

        initPanelGeneral();
        initPanelAutoPlay();
        initSubPanelButton();

        final JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(SettingsConstants.TITLE_GENERAL, panelGeneral);
        tabbedPane.addTab(SettingsConstants.TITLE_AUTO_PLAY, panelAutoPlay);

        Container contentPane = getContentPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(subPanelButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(SettingsConstants.CAPTION_SETTINGS);
        setResizable(false);
    }


    /**
     * Constructs the general settings panel.
     */
    private void initPanelGeneral() {
        panelGeneral = new JPanel();
        panelGeneral.setLayout(new BorderLayout());
        panelGeneral.setBorder(GeneralGUIConstants.BORDER_SETTINGS);

        final JPanel subPanelPlayerName = new JPanel();
        subPanelPlayerName.setLayout(new BoxLayout(subPanelPlayerName, BoxLayout.Y_AXIS));
        subPanelPlayerName.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel labelPlayerName = new JLabel(SettingsConstants.LABEL_PLAYER_NAME);
        labelPlayerName.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPlayerName = new JTextField(config.getPlayerName());
        fieldPlayerName.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPlayerName.setLabelFor(fieldPlayerName);

        subPanelPlayerName.add(labelPlayerName);
        subPanelPlayerName.add(Box.createRigidArea(GeneralGUIConstants.DIM_VERTICAL_SMALL));
        subPanelPlayerName.add(fieldPlayerName);

        panelGeneral.add(subPanelPlayerName, BorderLayout.NORTH);
    }


    /**
     * Constructs the auto play settings panel.
     */
    private void initPanelAutoPlay() {
        panelAutoPlay = new JPanel();
        panelAutoPlay.setLayout(new BoxLayout(panelAutoPlay, BoxLayout.Y_AXIS));
        panelAutoPlay.setBorder(GeneralGUIConstants.BORDER_SETTINGS);

        final JPanel subPanelSleepTime = new JPanel();
        subPanelSleepTime.setLayout(new BoxLayout(subPanelSleepTime, BoxLayout.Y_AXIS));
        subPanelSleepTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel labelSleepTime = new JLabel(SettingsConstants.LABEL_SLEEP_TIME);
        labelSleepTime.setAlignmentX(Component.LEFT_ALIGNMENT);

        // The labels presented below the slider.
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(SettingsConstants.SLIDER_VALUE_MIN, new JLabel(SettingsConstants.SLIDER_LABEL_MIN));
        labelTable.put(SettingsConstants.SLIDER_VALUE_MAX, new JLabel(SettingsConstants.SLIDER_LABEL_MAX));
        labelTable.put(SettingsConstants.SLIDER_VALUE_MID, new JLabel(SettingsConstants.SLIDER_LABEL_MID));
        sliderSleepTime = new JSlider(JSlider.HORIZONTAL, SettingsConstants.SLIDER_VALUE_MIN,
                SettingsConstants.SLIDER_VALUE_MAX, config.getSleepTime());
        sliderSleepTime.setAlignmentX(Component.LEFT_ALIGNMENT);
        sliderSleepTime.setMajorTickSpacing(SettingsConstants.SLIDER_MAJOR_TICK_SPACING);
        sliderSleepTime.setMinorTickSpacing(SettingsConstants.SLIDER_MINOR_TICK_SPACING);
        sliderSleepTime.setPaintTicks(true);
        sliderSleepTime.setLabelTable(labelTable);
        sliderSleepTime.setPaintLabels(true);
        labelSleepTime.setLabelFor(sliderSleepTime);

        subPanelSleepTime.add(labelSleepTime);
        subPanelSleepTime.add(Box.createRigidArea(GeneralGUIConstants.DIM_VERTICAL_SMALL));
        subPanelSleepTime.add(sliderSleepTime);

        final JPanel subPanelSearchLevel = new JPanel();
        subPanelSearchLevel.setLayout(new BoxLayout(subPanelSearchLevel, BoxLayout.Y_AXIS));
        subPanelSearchLevel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel labelSearchLevel = new JLabel(SettingsConstants.LABEL_SEARCH_LEVEL);
        labelSearchLevel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final SpinnerNumberModel numberModel = new SpinnerNumberModel(config.getSearchTreeLevel(),
                SettingsConstants.SEARCH_TREE_LEVEL_MIN,
                SettingsConstants.SEARCH_TREE_LEVEL_MAX, 1);
        spinnerSearchLevel = new JSpinner(numberModel);
        spinnerSearchLevel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ((JSpinner.DefaultEditor) (spinnerSearchLevel.getEditor())).getTextField()
                .setEditable(false);
        labelSearchLevel.setLabelFor(spinnerSearchLevel);

        subPanelSearchLevel.add(labelSearchLevel);
        subPanelSearchLevel.add(Box.createRigidArea(GeneralGUIConstants.DIM_VERTICAL_SMALL));
        subPanelSearchLevel.add(spinnerSearchLevel);

        panelAutoPlay.add(subPanelSleepTime);
        panelAutoPlay.add(Box.createRigidArea(GeneralGUIConstants.DIM_VERTICAL_SMALL));
        panelAutoPlay.add(subPanelSearchLevel);
    }


    /**
     * Constructs the buttons panel.
     */
    private void initSubPanelButton() {
        final JButton buttonReset = new JButton(SettingsConstants.BUTTON_RESET);
        buttonReset.addActionListener((ActionEvent event) -> {
            int option = JOptionPane.showConfirmDialog(null,
                    SettingsConstants.MESSAGE_RESET_DIALOG,
                    SettingsConstants.CAPTION_RESET_DIALOG,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                config.reset();
                fieldPlayerName.setText(config.getPlayerName());
                sliderSleepTime.setValue(config.getSleepTime());
                spinnerSearchLevel.setValue(config.getSearchTreeLevel());
            }
        });

        final JButton buttonOK = new JButton(SettingsConstants.BUTTON_OK);
        buttonOK.addActionListener((ActionEvent event) -> {
            String text = fieldPlayerName.getText();
            config.setPlayerName((text.length() == 0) ? SettingsConstants.DEFAULT_PLAYER_NAME : text);
            config.setSearchTreeLevel(Integer.parseInt(spinnerSearchLevel.getValue().toString()));
            config.setSleepTime(sliderSleepTime.getValue());
            dispose();
        });
        getRootPane().setDefaultButton(buttonOK);

        final JButton buttonCancel = new JButton(SettingsConstants.BUTTON_CANCEL);
        buttonCancel.addActionListener((ActionEvent event) -> dispose());

        subPanelButton = new JPanel();
        subPanelButton.setLayout(new BoxLayout(subPanelButton, BoxLayout.LINE_AXIS));
        subPanelButton.setBorder(GeneralGUIConstants.BORDER_SETTINGS);
        subPanelButton.add(buttonReset);
        subPanelButton.add(Box.createHorizontalGlue());
        subPanelButton.add(buttonOK);
        subPanelButton.add(Box.createRigidArea(GeneralGUIConstants.DIM_HORIZONTAL_MEDIUM));
        subPanelButton.add(buttonCancel);
    }
}
