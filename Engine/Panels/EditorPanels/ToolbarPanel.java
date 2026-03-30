package Engine.Panels.EditorPanels;

import java.awt.*;
import javax.swing.*;

import Engine.Panels.EditorPanels.EditorManager.EditorState;
import Engine.Panels.Utilities.COLORS;
import Engine.Core.GameManager;
import Engine.Core.GameManager.GameState;
import Engine.Core.Utils.CONSTANTS;

public class ToolbarPanel extends JPanel {
    private GameManager manager;
    private EditorManager level_manager;

    public ToolbarPanel(GameManager manager, EditorManager level_manager) {
        this.manager = manager;
        this.level_manager = level_manager;

        setBackground(COLORS.TOOLBAR_BACK);
        setPreferredSize(new Dimension(0, 75));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton create_map = new JButton("NEW MAP");
        StyleButton(create_map, new ImageIcon(getClass().getResource("/Assets/Editor/Icons/Placeholder32.png")));
        create_map.addActionListener(e -> {
            level_manager.SetState(EditorState.NEW_MAP);
        });

        JButton return_button = new JButton("RETURN");
        StyleButton(return_button, new ImageIcon(getClass().getResource("/Assets/Editor/Icons/Placeholder32.png")));
        return_button.addActionListener(e -> {manager.setState(GameState.MENU);});

        add(Box.createRigidArea(new Dimension(10, 0)));
        add(create_map);
        
        add(Box.createRigidArea(new Dimension(CONSTANTS.WIDTH-175, 0)));
        add(return_button);
    }

    private void StyleButton(JButton button, ImageIcon icon) {
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        button.setPreferredSize(new Dimension(100, 75));
        button.setForeground(Color.WHITE);

        button.setIcon(icon);

        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(COLORS.TOOLBAR_BACK);

        button.setBorder(null);
        button.setFocusable(false);
    }
}
