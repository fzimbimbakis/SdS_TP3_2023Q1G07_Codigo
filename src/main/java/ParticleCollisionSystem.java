import models.Collision;
import models.Particle;
import utils.JsonConfigReader;
import utils.Ovito;
import utils.ParticleUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ParticleCollisionSystem {

    private static final String JSON_CONFIG_PATH = "./src/main/java/config.json";
    private static final String OUTPUT_PATH = "./src/main/resources/output.xyz";

    private static final int BALL_COUNT = 15;
    private int ballsIn = 0;
    private static final Double TIME_STEP = 0.001;

    PriorityQueue<Collision> queue = new PriorityQueue<>();

    JsonConfigReader config = new JsonConfigReader(JSON_CONFIG_PATH);
    List<Particle> particles = ParticleUtils.generateInitialParticles(config);
    List<Particle> fixedParticles = ParticleUtils.generateFixedParticles(config);

    public void run(){
        this.fillQueue();
        double time = 0.0;
        Collision collision;

        Ovito.createFile(OUTPUT_PATH);
        Ovito.writeParticlesToFileXyz(OUTPUT_PATH, time, particles, fixedParticles);
        while (ballsIn != BALL_COUNT){

            //// Search next event
            do {
                collision = queue.poll();
                if(collision == null)
                    throw new IllegalStateException("Null collision");
            }while (collision.wasSuperveningEvent());

            //// Move particles
            time += collision.getT();
            for (Particle p : particles) {
                p.move(collision.getT());
            }
            Ovito.writeParticlesToFileXyz(OUTPUT_PATH, time, particles, fixedParticles);

            //// Execute collision
            collision.execute();

            //// If one is fixed -> Remove not fixed ball
            if(collision.getB() != null && collision.getB().isFixed()) {
                particles.remove(collision.getA());
                ballsIn++;
            }else
                updateQueue(collision.getA(), collision.getB());


        }
    }

    private void updateQueue(Particle A, Particle B){
        //// Update queue for particle A and B
        if(A != null) {
            for (Particle p : particles) {
                if (!p.equals(A))
                    queue.add(new Collision(A, p, config.getMaxX(), config.getMaxY()));
            }
            addDefaultCollisions(A);
        }
        if(B != null) {
            for (Particle p : particles) {
                if (!p.equals(B) && !p.equals(A))
                    queue.add(new Collision(B, p, config.getMaxX(), config.getMaxY()));
            }
            addDefaultCollisions(B);
        }
    }

    private void fillQueue(){
        List<Particle> done = new ArrayList<>();
        particles.forEach(
                p1 -> {
                    particles.forEach(p2 -> {
                        if(!done.contains(p2) && !p1.equals(p2))
                            queue.add(new Collision(p1, p2, config.getMaxX(), config.getMaxY()));
                    });
                    done.add(p1);

                    addDefaultCollisions(p1);
                }
        );
    }

    private void addDefaultCollisions(Particle p){
        queue.add(new Collision(p, null, config.getMaxX(), config.getMaxY()));
        queue.add(new Collision(null, p, config.getMaxX(), config.getMaxY()));

        fixedParticles.forEach(f -> {
            queue.add(new Collision(p, f, config.getMaxX(), config.getMaxY()));
        });
    }


}