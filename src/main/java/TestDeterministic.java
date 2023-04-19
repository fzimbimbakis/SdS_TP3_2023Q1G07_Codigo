import FloatImpl.JsonConfigReaderFloat;
import FloatImpl.ParticleCollisionSystemFloat;
import FloatImpl.models.ParticleFloat;
import models.Particle;
import utils.JsonConfigReader;
import utils.Ovito;
import utils.ParticleUtils;

import java.util.ArrayList;
import java.util.List;

public class TestDeterministic {

    private static final String JSON_CONFIG_PATH = "./src/main/java/config.json";

    public static void main(String[] args) {
        // Read JSON
        JsonConfigReader config = new JsonConfigReader(JSON_CONFIG_PATH);

        // generate particles lists
        List<Particle> particlesA = ParticleUtils.generateInitialParticles(config);
        List<Particle> particlesB = new ArrayList<>();
        for (Particle p : particlesA) {
            particlesB.add(Particle.copy(p));
        }
        List<ParticleFloat> particlesFloatA = new ArrayList<>();
        for( Particle p : particlesA){
            particlesFloatA.add(
                    new ParticleFloat(
                            (float) p.getX(), (float)p.getY(), (float)p.getVx(), (float)p.getVy(), (float)p.getRadius(), (float)p.getMass(), p.isFixed(), ParticleFloat.Color.BLUE, p.getNumber()
                    )
            );
        }
        List<ParticleFloat> particlesFloatB = new ArrayList<>();
        for (ParticleFloat p : particlesFloatA) {
            particlesFloatB.add(ParticleFloat.copy(p));
        }


        // Double
        String file_path_doubleA = Ovito.createFile("animation_times_double_", "txt");
        String total_time_path_doubleA = Ovito.createFile("animation_total_time_double_", "txt");
        String file_path_doubleB = Ovito.createFile("animation_times_double_", "txt");
        String total_time_path_doubleB = Ovito.createFile("animation_total_time_double_", "txt");

        // A
        double totalTimeDoubleA = 0;
        ParticleCollisionSystem particleCollisionSystemA = new ParticleCollisionSystem(particlesA, config, true);

        particleCollisionSystemA.run();

        totalTimeDoubleA += particleCollisionSystemA.getFinalTime();

        List<Double> combined = new ArrayList<>(particleCollisionSystemA.getEventTimes());

        Ovito.writeListToFIle(particleCollisionSystemA.getEventTimes(), file_path_doubleA, true);
        Ovito.writeToFIle(totalTimeDoubleA, total_time_path_doubleA);

        // B
        double totalTimeDoubleB = 0;
        ParticleCollisionSystem particleCollisionSystemB = new ParticleCollisionSystem(particlesB, config, true);

        particleCollisionSystemB.run();

        totalTimeDoubleB += particleCollisionSystemB.getFinalTime();
        Ovito.writeListToFIle(particleCollisionSystemB.getEventTimes(), file_path_doubleB, true);
        Ovito.writeToFIle(totalTimeDoubleB, total_time_path_doubleB);

        // Float
        String file_path_floatA = Ovito.createFile("animation_times_float_", "txt");
        String total_time_path_floatA = Ovito.createFile("animation_total_time_float_", "txt");

        String file_path_floatB = Ovito.createFile("animation_times_float_", "txt");
        String total_time_path_floatB = Ovito.createFile("animation_total_time_float_", "txt");

        JsonConfigReaderFloat configFloat = new JsonConfigReaderFloat(JSON_CONFIG_PATH);

        float totalTimeFloatA = 0;

        ParticleCollisionSystemFloat particleCollisionSystemFloatA =
                new ParticleCollisionSystemFloat( particlesFloatA, configFloat, true);

        particleCollisionSystemFloatA.getEventTimes().forEach(
                time -> combined.add((double)time)
        );

        particleCollisionSystemFloatA.run();

        totalTimeFloatA += particleCollisionSystemFloatA.getFinalTime();
        Ovito.writeListToFIle(combined, file_path_floatA, true);
        Ovito.writeToFIle(totalTimeFloatA, total_time_path_floatA);

        float totalTimeFloatB = 0;

        ParticleCollisionSystemFloat particleCollisionSystemFloatB =
                new ParticleCollisionSystemFloat( particlesFloatB, configFloat, true);

        particleCollisionSystemFloatB.run();

        totalTimeFloatB += particleCollisionSystemFloatB.getFinalTime();
        Ovito.writeListToFIle(particleCollisionSystemFloatB.getEventTimes(), file_path_floatB, true);
        Ovito.writeToFIle(totalTimeFloatB, total_time_path_floatB);

    }

}
