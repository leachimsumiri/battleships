package sample;

import java.util.ArrayList;
import java.util.Random;

//In der Klasse sind alle Eigenschaften, die ein Player hat
public class Player
{
    Field area = new Field();

    private ArrayList<AttackPositions> attackpositions = new ArrayList<>();

    /*SaveAttack speichert alle Attacken in die Arraylist*/
    public void SaveAttack(int x, int y)
    {
        this.attackpositions.add(new AttackPositions(x, y));
    }

    /*Wir verhindern doppelten Angriff. Wir schauen, mit der foreach Schleife, ob die Übergebenen x,y von attackPossible
    schon in einer der gespeicherten Stellen in unserer ArrayList attackpositions enthalten ist.*/
    boolean attackPossible(int x, int y)
    {
        for (AttackPositions a : this.attackpositions)
        {
            if ((a.getX() == x) && (a.getY() == y))
            {
                return false;
            }
        }
        return true;
    }

    /*Reset überschreibt unsere Klassenarraylist, die wir oben erstellt haben, mit einer Leeren Arraylist --> Resetet es*/
    public void Reset()
    {
        this.attackpositions = new ArrayList<>();
    }

    private boolean isHuman;

    Player(boolean isHuman)
    {
        this.isHuman = isHuman;
    }

}
