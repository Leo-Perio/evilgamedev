package Engine.Panels;

import java.awt.BorderLayout;

import javax.swing.*;

import Engine.Core.GameManager;
import Engine.Panels.EditorPanels.EditorManager;
import Engine.Panels.EditorPanels.SidebarPanel;
import Engine.Panels.EditorPanels.ToolbarPanel;
import Engine.Panels.EditorPanels.ViewportPanel;

public class EditorPanel extends JPanel {
    private ToolbarPanel toolbar;
    private SidebarPanel sidebar;
    private ViewportPanel viewport;

    private GameManager manager;
    private EditorManager level_manager;

    public EditorPanel(GameManager manager, EditorManager level_manager) {
        SetEditorSettings(manager, level_manager);
        InstantiatePanels();
        AddToPanelsEditor();
    }

    private void SetEditorSettings(GameManager manager, EditorManager level_manager) {
        this.manager = manager;
        this.level_manager = level_manager;
        setLayout(new BorderLayout());
    }

    private void InstantiatePanels() {
        this.toolbar = new ToolbarPanel(manager, level_manager);
        this.sidebar = new SidebarPanel();
        this.viewport = new ViewportPanel();
    }

    private void AddToPanelsEditor() {
        add(toolbar, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(viewport, BorderLayout.CENTER);
    }
}
