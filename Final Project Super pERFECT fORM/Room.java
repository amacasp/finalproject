import java.util.Set;
import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Room 
{
    private String description;
    private String roomName;
    private HashMap<String, Room> exits;
    private boolean hasQuestion;
    private String question;
    // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, boolean hasQuestion) 
    {
        this.description = description;
        this.hasQuestion = hasQuestion;
        this.question = "No Question";
        exits = new HashMap<String, Room>();
    }

    public Room(String description, String triviaQ, boolean hasQuestion)
    {
        this.description = description;
        this.hasQuestion = hasQuestion;
        question = triviaQ;
        exits = new HashMap<String, Room>();
    }

    public Room(String description, boolean hasQuestion, String room)
    {
        this.description = description;
        this.hasQuestion = hasQuestion;
        this.question = "No Question";
        roomName = room;
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public void hasQ()
    {
        hasQuestion = false;
    }

    public String getQuestion()
    {
        return question;
    }

    public boolean answeredQ()
    {
        return hasQuestion; 
    }
    
    public String getRoomName()
    {
        return roomName;
    }
}

