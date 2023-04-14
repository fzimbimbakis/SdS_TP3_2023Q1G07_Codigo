import utils.JsonConfigReader;
import utils.Ovito;

public class Main {
    private static final String JSON_CONFIG_PATH = "./src/main/java/config.json";

    public static void main(String[] args) {

        ParticleCollisionSystem particleCollisionSystem = new ParticleCollisionSystem(new JsonConfigReader(JSON_CONFIG_PATH));

        particleCollisionSystem.run();

        Ovito.writeListToFIle(particleCollisionSystem.getEventTimes(), "times", "txt");

    }
}
