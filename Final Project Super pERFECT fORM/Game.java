import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;
/**
 * 
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<String> questions;
    private ArrayList<String> randomQuestions;
    private HashMap<String, String> answerBank;
    private Random rng;
    private int ingredients;
    private MusicOrganizer music;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        rng = new Random();
        parser = new Parser();
        int ingredients = 0;
        questions = new ArrayList<String>();
        randomQuestions = new ArrayList<String>();
        answerBank = new HashMap<String, String>();
        makeQuestions();
        makeAnswerBank();
        randomizeQuestions();
        createRooms();
        music = new MusicOrganizer();

    }

    private void makeQuestions()
    {
        questions.add("Leonardo DiCaprio won his first oscar in what movie?");
        questions.add("What album did Jimi Hendrix release in 1968?");
        questions.add("What company developed Java?");
        questions.add("In the movie Blade Runner, what did Rick Deckard dream about?");
        questions.add("What was the name of the black hole in the movie Interstellar?");
        questions.add("Another way to find the integral of a function is to find its");
    }

    private void randomizeQuestions()
    {
        for(int a = 6; a > 0; a--){
            int c = rng.nextInt(a);
            randomQuestions.add(questions.get(c));
            questions.remove(c);
        }
    }

    private void makeAnswerBank()
    {
        this.answerBank.put("TheRevenant", questions.get(0));
        this.answerBank.put("ElectricLadyland", questions.get(1));
        this.answerBank.put("SunMicrosystems", questions.get(2));
        this.answerBank.put("Unicorn",questions.get(3));
        this.answerBank.put("Gargantua", questions.get(4));
        this.answerBank.put("Antiderivative", questions.get(5));

    }

    public String getAnswer(String answer)
    {
        return answerBank.get(answer);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room Africa, Europe, NorthAmerica, CentralAmerica, kitchen, Asia, plane, Nigeria, France, China, Greece, Florida, DominicanRepublic;

        int b = 0;

        // create the rooms
        Africa = new Room("The Igbo people of Western Africa are famous yam farmers. Head to Nigeria to meet their leader, Okonkwo.", false);
        Europe = new Room("Ahhhh, Europe. Go to France where cheese practically grows on fields or Greece where the rivers flow with yogurt", false);
        NorthAmerica = new Room("Florida is often called the Orange State. Head there to obtain freshly squeezed orange juice.", false);
        CentralAmerica = new Room("Central America is a huge exporter of bananas. Head to the Dominican Republic to gather some bananas.", false);
        kitchen = new Room("You are in the kitchen. Once you've gathered all of the ingredients, head back here to make a breakfast worthy of Champions", false, "kitchen1");
        Asia = new Room("China is one of biggest egg producers in the world, I suggest we head there for the base of our omelette", false);
        plane = new Room("Where would you like to go?", false);
        Nigeria = new Room("You meet with Okonkwo and the only way to obtain their coveted yams is to solve this riddle: " + randomQuestions.get(b),randomQuestions.get(b), true);
        b=b+1;
        France = new Room("You go to France and you meet with a cheese master. In order to obtain cheese, you must asnwer this question: " + randomQuestions.get(b),randomQuestions.get(b), true);
        b=b+1;
        Greece = new Room("The yogurt God Chobani will let you have some of his delicious yogi, but you have to answer this trivia question right: " + randomQuestions.get(b), randomQuestions.get(b), true);
        b=b+1;
        China = new Room("The ghost of Confucious greets you with a dozen proverbs before offering you The Egg. He will only give The Egg if you answer this question correctly: " + randomQuestions.get(b),randomQuestions.get(b), true);
        b=b+1;
        DominicanRepublic = new Room("Adrian Beltre, the captain of the Texas Rangers, credits his athletic career to bananas. Solve this riddle and he will shares some of his bananas: " + randomQuestions.get(b),randomQuestions.get(b), true);
        b=b+1;
        Florida = new Room("You find out Tom petty has a huge orange plantation. He will give you some of his oranges if you solve this question: " + randomQuestions.get(b),randomQuestions.get(b), true);

        plane.setExit("NorthAmerica", NorthAmerica);
        plane.setExit("CentralAmerica", CentralAmerica);
        plane.setExit("Europe", Europe);
        plane.setExit("Africa", Africa);
        plane.setExit("Asia", Asia);
        plane.setExit("kitchen", kitchen);

        Asia.setExit("China", China);
        Asia.setExit("Plane", plane);

        China.setExit("Asia", Asia);

        Europe.setExit("Plane", plane);
        Europe.setExit("Greece", Greece);
        Europe.setExit("France", France);

        Greece.setExit("Europe", Europe);

        France.setExit("Europe", Europe);

        Africa.setExit("Plane", plane);
        Africa.setExit("Nigeria", Nigeria);

        Nigeria.setExit("Africa", Africa);

        CentralAmerica.setExit("Plane", plane);
        CentralAmerica.setExit("DominicanRepublic", DominicanRepublic);

        DominicanRepublic.setExit("CentralAmerica", CentralAmerica);

        NorthAmerica.setExit("Florida", Florida);
        NorthAmerica.setExit("Plane", plane);

        Florida.setExit("NorthAmerica", NorthAmerica);

        kitchen.setExit("Plane", plane);

        currentRoom = kitchen;  
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);

        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the Breakfast Starlight Excellent");
        System.out.println("Travel the world to find only the most exquisite ingredients and make breakfast");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        String commandWord = command.getCommandWord();
        for(int a = 0; a <= 5; a++){
            if(currentRoom.getQuestion().equals(randomQuestions.get(a)) && currentRoom.answeredQ() ){
                String answer = this.getAnswer(commandWord);
                if(currentRoom.getQuestion().equals(answer)){
                    currentRoom.hasQ();
                    System.out.println("Correct! You get the ingredient");
                    ingredients = ingredients + 1;
                }
                else{
                    System.out.println("Please try again");
                }
            }
        }

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if(commandWord.equals("CookBreakfast")){
            if("kitchen1".equals(currentRoom.getRoomName()) && ingredients == 6){
                makeBreakfast();
                wantToQuit = true;
                music.startPlaying(0);
            }
            else if(ingredients == 6){
                System.out.println("Head to the kitchen first before cooking breakfast");
            }
            else if("kitchen1".equals(currentRoom.getRoomName())){
                System.out.println("Gather all the ingredients first!");
            }
        }

        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    private void makeBreakfast()
    {
        System.out.println("Congratulations! After gathering all of the ingredients, you finally aseembled a breakfast worthy of champions");

    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Don't know how to navigate my poorly crafted game?");
        System.out.println("No worries, here are a list of command words you can use");
        System.out.println();
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
