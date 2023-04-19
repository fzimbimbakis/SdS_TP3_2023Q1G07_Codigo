package FloatImpl;

import FloatImpl.models.CollisionFloat;
import FloatImpl.models.ParticleFloat;
import FloatImpl.utils.OvitoFloat;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ParticleCollisionSystemFloat {
    private static final String OUTPUT_NAME = "output";
    private static final String OUTPUT_EXTENSION = "xyz";
    private static final int BALL_COUNT = 16;
    JsonConfigReaderFloat config;
    private int ballsIn = 0;
    PriorityQueue<CollisionFloat> queue = new PriorityQueue<>();
    List<ParticleFloat> particleFloats;
    List<ParticleFloat> fixedParticleFloats;
    private final List<Float> eventTimes = new ArrayList<>();
    private Float finalTime = (float)0.0;
    private final boolean generateXYZ;

    public ParticleCollisionSystemFloat(JsonConfigReaderFloat config) {
        this.config = config;
        this.particleFloats = ParticleUtilsFloat.generateInitialParticles(config);
        this.fixedParticleFloats = ParticleUtilsFloat.generateFixedParticles(config);
        this.generateXYZ = false;
    }

    public ParticleCollisionSystemFloat(JsonConfigReaderFloat config, boolean generateXYZ) {
        this.config = config;
        this.particleFloats = ParticleUtilsFloat.generateInitialParticles(config);
        this.fixedParticleFloats = ParticleUtilsFloat.generateFixedParticles(config);
        this.generateXYZ = generateXYZ;
    }

    public ParticleCollisionSystemFloat(List<ParticleFloat> particles, JsonConfigReaderFloat config, boolean generateXYZ) {
        this.config = config;
        this.particleFloats = particles;
        this.fixedParticleFloats = ParticleUtilsFloat.generateFixedParticles(config);
        this.generateXYZ = generateXYZ;
    }

    public void run() {
        this.fillQueue();
        Float time = (float)0.0;
        CollisionFloat collisionFloat;
        String path = null;

        if (this.generateXYZ) {
            path = OvitoFloat.createFile(OUTPUT_NAME, OUTPUT_EXTENSION);
            OvitoFloat.writeParticlesToFileXyz(path, time, particleFloats, fixedParticleFloats, null, null);
        }
        while (ballsIn != BALL_COUNT) {

            //// Search next event
            queue.removeIf(CollisionFloat::wasSuperveningEvent);
            collisionFloat = queue.remove();

            //// Move particles
            time = collisionFloat.getT();
            moveAllParticles(time);
            if (this.generateXYZ)
                OvitoFloat.writeParticlesToFileXyz(path, time, particleFloats, fixedParticleFloats, collisionFloat.getA(), collisionFloat.getB());

            //// Execute collision
            eventTimes.add(time);
            this.finalTime += time;
            collisionFloat.execute();

            //// If one is fixed -> Remove not fixed ball
            if (collisionFloat.getB() != null && collisionFloat.getB().isFixed()) {
                particleFloats.remove(collisionFloat.getA());
                ballsIn++;
            } else
                updateQueue(collisionFloat.getA(), collisionFloat.getB());


        }
    }

    private void moveAllParticles(float time) {
        for (ParticleFloat p : particleFloats) {
            p.move(time, config.getMaxX(), config.getMaxY());
        }
    }

    private void updateQueue(ParticleFloat A, ParticleFloat B){
        //// Update queue for particle A and B
        if(A != null) {
            for (ParticleFloat p : particleFloats) {
                if (!p.equals(A))
                    queue.add(new CollisionFloat(A, p, config.getMaxX(), config.getMaxY()));
            }
            addDefaultCollisions(A);
        }
        if(B != null) {
            for (ParticleFloat p : particleFloats) {
                if (!p.equals(B))
                    queue.add(new CollisionFloat(B, p, config.getMaxX(), config.getMaxY()));
            }
            addDefaultCollisions(B);
        }
    }

    private void fillQueue(){
        particleFloats.forEach(
                p1 -> {
                    particleFloats.forEach(p2 -> {
                        if(!p1.equals(p2))
                            queue.add(new CollisionFloat(p1, p2, config.getMaxX(), config.getMaxY()));
                    });

                    addDefaultCollisions(p1);
                }
        );
    }

    private void addDefaultCollisions(ParticleFloat p){
        if (p.getVx() != 0)
            queue.add(new CollisionFloat(p, null, config.getMaxX(), config.getMaxY()));
        if (p.getVy() != 0)
            queue.add(new CollisionFloat(null, p, config.getMaxX(), config.getMaxY()));

        fixedParticleFloats.forEach(f -> {
            queue.add(new CollisionFloat(p, f, config.getMaxX(), config.getMaxY()));
        });
    }

    public List<Float> getEventTimes() {
        return eventTimes;
    }

    public Float getFinalTime() {
        return finalTime;
    }
}
