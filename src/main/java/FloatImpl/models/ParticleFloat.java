package FloatImpl.models;


public class ParticleFloat {

    public enum Color {
        BLACK,
        WHITE,
        RED,
        BLUE
    }

    private final boolean isFixed;
    private float x;
    private float y;
    private float Vx;
    private float Vy;
    private final float radius;
    private final float mass;
    private int collisionsCount;
    private final Color color;
    private final int number;

    public static ParticleFloat copy(ParticleFloat particle){
        return new ParticleFloat(particle.x, particle.y, particle.Vx, particle.Vy, particle.radius, particle.mass, particle.isFixed, particle.color, particle.number);
    }

    public ParticleFloat(float x, float y, float vx, float vy, float radius, float mass, boolean isFixed, Color color, int number) {
        this.x = x;
        this.y = y;
        Vx = vx;
        Vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.isFixed = isFixed;
        this.color = color;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Float collidesX(float xMax){
        if(Vx == 0)
            return Float.MAX_VALUE;
        if( Vx > 0)
             return (xMax - radius - x)/Vx;
        return (radius - x)/Vx;
    }

    public Float collidesY(float yMax){
        if(Vy == 0)
            return Float.MAX_VALUE;
        if( Vy > 0)
            return (yMax - radius - y)/Vy;
        return (radius - y)/Vy;
    }

    public Float collides(ParticleFloat b) {
        float deltaX = b.x - x;
        float deltaY = b.y - y;
        float deltaVx = b.Vx - Vx;
        float deltaVy = b.Vy - Vy;

        float productDeltaVR = deltaX * deltaVx + deltaY * deltaVy;

        if (productDeltaVR >= 0)
            return Float.MAX_VALUE;
        float v2 = ((float)Math.pow(deltaVx, 2)) + ((float)Math.pow(deltaVy, 2));

        float r2 = ((float)Math.pow(deltaX, 2)) + ((float)Math.pow(deltaY, 2));

        float d = ((float)Math.pow(productDeltaVR, 2)) - v2 * (r2 - ((float)Math.pow(radius + b.radius, 2)));

        if (d < 0)
            return Float.MAX_VALUE;

        return -(productDeltaVR + ((float)Math.sqrt(d))) / v2;
    }

    public void bounceX(){
        collisionsCount++;
        Vx = -Vx;
    }

    public void bounceY(){
        collisionsCount++;
        Vy = -Vy;
    }

    public void bounce(ParticleFloat b) {
        collisionsCount++;

        if (b.isFixed)
            return;
        else
            b.collisionsCount++;

        float deltaX = b.x - x;
        float deltaY = b.y - y;
        float deltaVx = b.Vx - Vx;
        float deltaVy = b.Vy - Vy;

        float J = (2 * mass * b.mass * (deltaX * deltaVx + deltaY * deltaVy)) / ((radius + b.radius) * (mass + b.mass));

        float Jx = (J * deltaX) / (radius + b.radius);
        float Jy = (J * deltaY) / (radius + b.radius);

        Vx = Vx + Jx / mass;
        Vy = Vy + Jy / mass;

        b.Vx = b.Vx - Jx / b.mass;
        b.Vy = b.Vy - Jy/b.mass;

    }

    public void move(float time, float maxX, float maxY) {
        x += Vx * time;
        if (x > maxX)
            throw new IllegalStateException("Ball " + this.number + " out of bounds: x = " + x);
        y += Vy * time;
        if (y > maxY)
            throw new IllegalStateException("Ball " + this.number + " out of bounds: y = " + y);
    }

    public int getCollisionCount() {
        return collisionsCount;
    }

    public boolean isFixed() {
        return isFixed;
    }

    @Override
    public String toString() {
        switch (color){
            case RED:
                return x + " " + y + " " + Vx + " " + Vy + " " + radius + " 255 0 0";
            case BLACK:
                return x + " " + y + " " + Vx + " " + Vy + " " + radius + " 0 0 0";
            case WHITE:
                return x + " " + y + " " + Vx + " " + Vy + " " + radius + " 255 255 255";
            case BLUE:
                return x + " " + y + " " + Vx + " " + Vy + " " + radius + " 0 0 255";
        }

        return x + " " + y + " " + Vx + " " + Vy + " " + radius;
    }

    public float getVx() {
        return Vx;
    }

    public float getVy() {
        return Vy;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
