import javax.swing.*;
import java.awt.*;

public class ThemeManager {
    public static void applyLightTheme(Component comp) {
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Label.foreground", Color.BLACK);
        SwingUtilities.updateComponentTreeUI(comp);
    }

    public static void applyDarkTheme(Component comp) {
        UIManager.put("Button.background", Color.DARK_GRAY);
        UIManager.put("Panel.background", Color.DARK_GRAY);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Label.foreground", Color.WHITE);
        SwingUtilities.updateComponentTreeUI(comp);
    }
}
