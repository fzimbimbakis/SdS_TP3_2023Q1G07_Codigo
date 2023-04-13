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
    private static final String OUTPUT_NAME = "output";

    private static final int BALL_COUNT = 16;
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

        String path = Ovito.createFile(OUTPUT_NAME);
        Ovito.writeParticlesToFileXyz(path, time, particles, fixedParticles, null, null);
        while (ballsIn != BALL_COUNT){

            //// Search next event
            queue.removeIf(Collision::wasSuperveningEvent);
            collision = queue.remove();

            //// Move particles
            time = collision.getT();
            moveAllParticlesPrinting(time, collision, path);

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

    private void moveAllParticles(double time, Collision collision, String path){
        for (Particle p : particles) {
            p.move(time, config.getMaxX(), config.getMaxY());
        }
        Ovito.writeParticlesToFileXyz(path, time, particles, fixedParticles, collision.getA(), collision.getB());
    }

    private void moveAllParticlesPrinting(double time, Collision collision, String path){
        double step = TIME_STEP;
        int steps = (int) (time/step);
        for (int i = 0; i < steps; i++) {
            for (Particle p : particles) {
                p.move(step, config.getMaxX(), config.getMaxY());
            }
            Ovito.writeParticlesToFileXyz(path, time, particles, fixedParticles, collision.getA(), collision.getB());
        }
        for (Particle p : particles) {
            p.move(time - ((double) steps) * step, config.getMaxX(), config.getMaxY());
        }
        Ovito.writeParticlesToFileXyz(path, time, particles, fixedParticles, collision.getA(), collision.getB());
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
                if (!p.equals(B))
                    queue.add(new Collision(B, p, config.getMaxX(), config.getMaxY()));
            }
            addDefaultCollisions(B);
        }
    }

    private void fillQueue(){
        particles.forEach(
                p1 -> {
                    particles.forEach(p2 -> {
                        if(!p1.equals(p2))
                            queue.add(new Collision(p1, p2, config.getMaxX(), config.getMaxY()));
                    });

                    addDefaultCollisions(p1);
                }
        );
    }

    private void addDefaultCollisions(Particle p){
        if(p.getVx() != 0)
            queue.add(new Collision(p, null, config.getMaxX(), config.getMaxY()));
        if(p.getVy() != 0)
            queue.add(new Collision(null, p, config.getMaxX(), config.getMaxY()));

        fixedParticles.forEach(f -> {
            queue.add(new Collision(p, f, config.getMaxX(), config.getMaxY()));
        });
    }


}
