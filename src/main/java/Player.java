import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int score;
    private int equipmentID;

    private String hitBaseIndicator;
    private boolean hitBase;

    private int playerID;

    public Player(String name, int playerID, int equipmentID, int score) {
        this.name = name;
        this.playerID = playerID;
        this.equipmentID = equipmentID;
        this.score = score;
        this.hitBase = false;
        this.hitBaseIndicator = "";
    }

    public String getName() {
        return name;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score += 10;
    }

    public void incrementScoreBaseHit(){ score += 100; }

    public void setEquipmentID(int equipmentID)
    {
        this.equipmentID = equipmentID;
    }

    public void setHitBase(int equipmentID)
    {
        if(hitBase == false)
        {
            hitBase = true;
            hitBaseIndicator = "ℬ";
        }
    }

    public String getHitBaseIndicator()
    {
        return this.hitBaseIndicator;
    }

    public boolean getHitBase()
    {
        return this.hitBase;
    }

    @Override
    public String toString()
    {
        return "Username: " + this.getName() + " Score: " + this.getScore() + " PlayerID: " + this.getPlayerID() +
                " EquipmentID: " + this.getEquipmentID();
    }
}
