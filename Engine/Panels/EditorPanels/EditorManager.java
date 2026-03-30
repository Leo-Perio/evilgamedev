package Engine.Panels.EditorPanels;

import javax.swing.*;
import Engine.Panels.EditorPanels.Popups.*;

public class EditorManager {
    public enum EditorState {
        DEFAULT,
        NEW_MAP
    };

    private EditorState current_state;

    public EditorManager() {
        this.current_state = EditorState.DEFAULT;
    }

    public void SetState(EditorState state) {
        switch(state) {
            case DEFAULT:
                break;
            case NEW_MAP:
                ShowNewMap();
                break;
        }
    }

    public void ShowNewMap() {
        JDialog dialog = new JDialog((JFrame)null, "New Map", true);
        
        FormNewMap form = new FormNewMap(this);

        dialog.setContentPane(form);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
