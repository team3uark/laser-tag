import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

class Player {
    private String name;
    private int score;
    private int equipmentID;

    private int playerID;

    public Player(String name, int playerID, int equipmentID, int score) {
        this.name = name;
        this.playerID = playerID;
        this.equipmentID = equipmentID;
        this.score = score;
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

    public void setEquipmentID(int equipmentID)
    {
        this.equipmentID = equipmentID;
    }

    @Override
    public String toString()
    {
        return "Username: " + this.getName() + " Score: " + this.getScore() + " PlayerID: " + this.getPlayerID() +
                " EquipmentID: " + this.getEquipmentID();
    }
}
