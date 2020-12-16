package sample;

public class AIsave
{
    private int x,y;
    private Direction direction;
    private boolean water;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Direction getDirection()
    {
        return direction;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public AIsave(int x, int y, boolean water)
    {
        this.x = x;
        this.y = y;
        this.water = water;
        direction=null;
    }

    public AIsave(int x, int y, Direction direction, boolean water)
    {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.water = water;
    }
}
