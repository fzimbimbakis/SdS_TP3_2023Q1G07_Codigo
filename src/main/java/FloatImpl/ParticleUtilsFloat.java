package FloatImpl;

import FloatImpl.models.ParticleFloat;

import java.util.ArrayList;
import java.util.List;

public class ParticleUtilsFloat {

    private static final boolean FIXED = true;

    private static Float randomEpsilon(Float min, Float max){
        return min + ((float)Math.random()) * (Math.abs(max - min));
    }

    public static List<ParticleFloat> generateFixedParticles(JsonConfigReaderFloat config){
        Float RADIUS = config.getRadius();
        Float MASS = config.getMass();


        List<ParticleFloat> list = new ArrayList<>();

        //// Fixed
        list.add(new ParticleFloat(0, 0, 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 101));
        list.add(new ParticleFloat(config.getMaxX() / 2, 0, 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 102));
        list.add(new ParticleFloat(config.getMaxX(), 0, 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 103));

        list.add(new ParticleFloat(0, config.getMaxY(), 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 104));
        list.add(new ParticleFloat(config.getMaxX() / 2, config.getMaxY(), 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 105));
        list.add(new ParticleFloat(config.getMaxX(), config.getMaxY(), 0, 0, 2*RADIUS, MASS, FIXED, ParticleFloat.Color.BLACK, 106));

        return list;
    }

    public static List<ParticleFloat> generateInitialParticles(JsonConfigReaderFloat config){

        Float RADIUS = config.getRadius();
        Float MASS = config.getMass();
        Float MAX_EPSILON = config.getMaxEpsilon();
        Float MIN_EPSILON = config.getMinEpsilon();

        List<ParticleFloat> list = new ArrayList<>();

        //// White
        list.add(new ParticleFloat(config.getWhiteX(), config.getWhiteY(), config.getWhiteV(), 0, RADIUS, MASS, !FIXED, ParticleFloat.Color.WHITE, -1));

        //// Default balls
        Float triangleX = config.getTriangleX();
        Float triangleY = config.getTriangleY();

        int n = 1;
        float deltaY = RADIUS * 2 + MAX_EPSILON;
        float deltaX = ((float)Math.cos(Math.PI / 6)) * (RADIUS * 2 + MAX_EPSILON);

        for (int i = 0; i < 5; i++) {
            list.add(new ParticleFloat(triangleX + randomEpsilon(MIN_EPSILON, MAX_EPSILON), triangleY + randomEpsilon(MIN_EPSILON, MAX_EPSILON), 0, 0, RADIUS, MASS, !FIXED, ParticleFloat.Color.RED, i * n / 2));

            for (int j = 1; j < n; j++) {
                list.add(new ParticleFloat(triangleX + randomEpsilon(MIN_EPSILON, MAX_EPSILON), triangleY - j * deltaY + randomEpsilon(MIN_EPSILON, MAX_EPSILON), 0, 0, RADIUS, MASS, !FIXED, ParticleFloat.Color.RED, i * n / 2 + j));
            }

            n++;
            triangleX += deltaX;
            triangleY += deltaY / 2;
        }

        return list;
    }

}
