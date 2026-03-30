package Engine.Panels.EditorPanels.Popups;

import java.awt.*;
import javax.swing.*;

import Engine.Panels.EditorPanels.EditorManager;
import Engine.Panels.EditorPanels.EditorManager.EditorState;
import Engine.Panels.Utilities.COLORS;

public class FormNewMap extends JPanel {
    private EditorManager level_manager;

    public FormNewMap(EditorManager level_manager) {
        Instantiate(level_manager);
        SetPanelSettings();
        BuildForm();
    }

    private void Instantiate(EditorManager level_manager) {
        this.level_manager = level_manager;
    }

    private void SetPanelSettings() {
        setPreferredSize(new Dimension(400, 100));
    }

    private void BuildForm() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(COLORS.TOOLBAR_BACK);

        JLabel map_x_label = new JLabel("X: ");
        
        JTextField map_x_field = new JTextField();
        StyleField(map_x_field);

        JTextField map_y_field = new JTextField("Y: ");
        StyleField(map_y_field);

        JButton submit = new JButton("Create");
        StyleButton(submit);

        submit.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            level_manager.SetState(EditorState.DEFAULT);
        });

        add(map_x_label);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(map_x_field);
        add(Box.createVerticalGlue());
        add(map_y_field);
        add(submit);
    }

    private void StyleField(JTextField field) {
        field.setPreferredSize(new Dimension(25, 25));
        field.setForeground(Color.WHITE);

        field.setFont(new Font("SansSerif", Font.BOLD, 14));
        field.setBackground(COLORS.SIDEBAR_BACK);

        field.setBorder(null);
    }

    private void StyleButton(JButton button) {
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        button.setPreferredSize(new Dimension(25, 25));
        button.setForeground(Color.WHITE);

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(COLORS.TOOLBAR_BACK);

        button.setBorder(null);
        button.setFocusable(false);
    }
}
