package FloatImpl.models;

public class CollisionFloat implements Comparable<CollisionFloat>{

    private final ParticleFloat A;
    private int collisionsA;
    private final ParticleFloat B;
    private int collisionsB;

    private final Float maxX;
    private final Float maxY;

    public CollisionFloat(ParticleFloat A, ParticleFloat B, Float maxX, Float maxY){

        if(A == null && B == null)
            throw new IllegalArgumentException("Both event particles are null.");

        this.A = A;
        if(A != null)
            this.collisionsA = A.getCollisionCount();

        this.B = B;
        if(B != null)
            this.collisionsB = B.getCollisionCount();

        this.maxX = maxX;
        this.maxY = maxY;

    }

    public Float getT() {
        if(B == null)
            return A.collidesX(maxX);
        else if(A == null)
            return B.collidesY(maxY);
        else return A.collides(B);
    }

    public ParticleFloat getA() {
        return A;
    }

    public ParticleFloat getB() {
        return B;
    }

    public boolean wasSuperveningEvent(){
        if(A == null)
            return this.collisionsB != B.getCollisionCount();
        if(B == null)
            return this.collisionsA != A.getCollisionCount();
        return this.collisionsA != A.getCollisionCount() || this.collisionsB != B.getCollisionCount();
    }

    public void execute(){
        if(A == null)
            B.bounceY();
        else if(B == null)
            A.bounceX();
        else A.bounce(B);
    }

    @Override
    public int compareTo(CollisionFloat collisionFloat) {
        return Double.compare(this.getT(), collisionFloat.getT());
    }
}
