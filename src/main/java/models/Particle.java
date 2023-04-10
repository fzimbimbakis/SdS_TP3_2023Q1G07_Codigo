package models;


public class Particle{

    public static enum Color{
        BLACK,
        WHITE,
        RED
    }

    private final boolean isFixed;
    private double x;
    private double y;
    private double Vx;
    private double Vy;
    private final double radius;
    private final double mass;
    private int collisionsCount;
    private final Color color;

    public Particle(double x, double y, double vx, double vy, double radius, double mass, boolean isFixed, Color color) {
        this.x = x;
        this.y = y;
        Vx = vx;
        Vy = vy;
        this.radius = radius;
        this.mass = mass;
        this.isFixed = isFixed;
        this.color = color;
    }


    public double collidesX(double xMax){
        if(Vx == 0)
            return Double.MAX_VALUE;
        if( Vx > 0)
             return (xMax - radius - x)/Vx;
        return (radius - x)/Vx;
    }

    public double collidesY(double yMax){
        if(Vy == 0)
            return Double.MAX_VALUE;
        if( Vy > 0)
            return (yMax - radius - y)/Vy;
        return (radius - y)/Vy;
    }

    public double collides(Particle b){
        double deltaX = x - b.x;
        double deltaY = y - b.y;
        double deltaVx = Vx - b.Vx;
        double deltaVy = Vy - b.Vy;


        if (deltaX*deltaVx +  deltaY*deltaVy >= 0)
            return Double.MAX_VALUE;
        double v2 = Math.pow(deltaVx, 2) + Math.pow(deltaVy, 2);

        double d = Math.pow(deltaX*deltaVx +  deltaY*deltaVy, 2) - v2 * (Math.pow(deltaX,2) + Math.pow(deltaY,2) - Math.pow(radius + b.radius, 2));
        if(d < 0){
            return Double.MAX_VALUE;
        }

        return - ((deltaX*deltaVx +  deltaY*deltaVy) + Math.sqrt(d)) / v2;

    }

    public void bounceX(){
        collisionsCount++;
        Vx = -Vx;
    }

    public void bounceY(){
        collisionsCount++;
        Vy = -Vy;
    }

    public void bounce(Particle b){
        collisionsCount++;

        if(b.isFixed)
            return;
        else
            b.collisionsCount++;

        double deltaX = x - b.x;
        double deltaY = y - b.y;
        double deltaVx = Vx - b.Vx;
        double deltaVy = Vy - b.Vy;

        double J = (2 * mass * b.mass * (deltaX*deltaVx +  deltaY*deltaVy)) / ((radius + b.radius) * (mass + b.mass));

        double Jx = (J * deltaX) / (radius + b.radius);
        double Jy = (J * deltaY) / (radius + b.radius);

        Vx = Vx + Jx/mass;
        Vy = Vy + Jy/mass;

        b.Vx = b.Vx - Jx/b.mass;
        b.Vy = b.Vy - Jy/b.mass;

    }

    public void move (double time){
        x += Vx * time;
        y += Vy * time;
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

        }

        return x + " " + y + " " + Vx + " " + Vy + " " + radius;
    }
}
