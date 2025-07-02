import java.io.FileWriter;
import java.io.IOException;

public class ScoreSaver {
    public static void saveScores(Player p1, Player p2, int draws) {
        try (FileWriter writer = new FileWriter("scores.txt", true)) {
            writer.write(p1.getName() + " (X): " + p1.getScore() + "\n");
            writer.write(p2.getName() + " (O): " + p2.getScore() + "\n");
            writer.write("Draws: " + draws + "\n");
            writer.write("-----\n");
        } catch (IOException e) {
            System.out.println("Could not save scores: " + e.getMessage());
        }
    }
}
