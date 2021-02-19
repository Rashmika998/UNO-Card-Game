
package gameProjectUNO;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class PlayGame {
    boolean direction; //direction of the game
    private String[] playerNames; //get the name of the players who are playing
    private int player; //represents the current player
    private DeckUNO playingDeck; //playing deck
    private ArrayList<ArrayList<CardPropertiesUNO>> hands; //all of the players hands
    private ArrayList<CardPropertiesUNO> stockpile; //stockpile
    private  CardPropertiesUNO.cardColours validCardColour; //valid card colour throught the game
    private CardPropertiesUNO.cardValues validCardValue; //valid card value throught the game
    private int [] score=new int[10];
    String path;//path of the sound-clip
    MultiPlayerGameStage gameStage;
    public boolean uno=false;
    public boolean said=false;
    
    //default constructor
    public PlayGame(){}

    //passes the names of the players at the begining
    public PlayGame(String[] players,int score[],MultiPlayerGameStage gameStage)
    {
        this.gameStage=gameStage;
        this.score=score;
        playerNames=players;
        player=0;
        playingDeck= new DeckUNO();
        try {
                playMusicStarts("sounds/shuffling_cards.wav");
        } catch (IOException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        playingDeck.shuffleCards();
        stockpile=new ArrayList<CardPropertiesUNO>();
        direction=false; //standard game direction (clockwise)
        hands= new ArrayList<ArrayList<CardPropertiesUNO>>();
        for(int i=0;i<players.length;i++)
        {
            //create the hand by distributing 7 cards for each player
            ArrayList<CardPropertiesUNO> hand= new ArrayList<CardPropertiesUNO>(Arrays.asList(playingDeck.getCard(7)));
            hands.add(hand); //adding the 7 cards to hands variable(all of every player hand)
        }
        
    } 

    
    //start the game once all done
    public void start(PlayGame game)
    {
        CardPropertiesUNO card=playingDeck.getCard();
        validCardColour= card.getCardColour();
        validCardValue= card.getCardValue();
        
        //if the first card gets from the deck is a draw two or wild or wild four, restart the game 
        if(card.getCardValue() == CardPropertiesUNO.cardValues.DRAWTWO || card.getCardValue() == CardPropertiesUNO.cardValues.WILD || card.getCardValue() == CardPropertiesUNO.cardValues.WILDFOUR)
            start(game);
        
        //if the first card gets from the deck is skip
        if (card.getCardValue() == CardPropertiesUNO.cardValues.SKIP)
        {
            JLabel msg = new JLabel("Whoops! " + playerNames[player]+ " was skipped");
            msg.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg);
            
            if (direction == false)
                player= (player + 1 )%playerNames.length;
            
            else if (direction == true)
            {
                player = (player - 1)%playerNames.length;
                if(player<0)
                    player = playerNames.length-1;
            }   
        }
        
        //if the first card gets from the deck is reverse
        if (card.getCardValue()==CardPropertiesUNO.cardValues.REVERSE)
        {
            JLabel msg = new JLabel("Whoops! game direction was reversed");
            msg.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg);
            if(direction==true)
                direction=false;
            
            else if(direction==false)
                direction =true;
            player = player - 1;
            if(player<0)
                    player = playerNames.length-1;
            
        }
        stockpile.add(card);
          
    } 
    
    //method which returns the top card colour and value
    public CardPropertiesUNO topCard(){
        return new CardPropertiesUNO(validCardColour, validCardValue);
    }
    
    //method which returns the image of the card
    public ImageIcon topCardImage(){
        return new ImageIcon(validCardColour+"_"+validCardValue+".png");
    }
    
    //method to check whether the game is over
    public boolean gameOver(){
        for(String playName:this.playerNames)
        {
            if(handIsEmpty(playName))
                return true;
        }
        return false;
    }   
    
    //method to check whether a player's hand is empty
    public boolean handIsEmpty(String playid){
        return playerHand(playid).isEmpty();   
    }
    
    //method to get the current player
    public String currentPlayer(){
        return this.playerNames[this.player];
    }

    //method to get the previous player
    public String previousPlayer(int h){
        if(this.player-h < 0) //if the current player is 0th one
            this.player=playerNames.length-1;
        
        return this.playerNames[this.player-h];
    }
    
    //method to return the names of the players
    public String[] allPlayers(){
        return playerNames;
    }

    //method which returns the hand of a corresponding player
    public ArrayList<CardPropertiesUNO> playerHand(String playid){    
        int n=Arrays.asList(playerNames).indexOf(playid);
        return hands.get(n);
    }

    //method to gets audio when playing the game
    public void playMusicStarts(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException{
        this.path=path;
        AudioInputStream audioInput=AudioSystem.getAudioInputStream(getClass().getResource(this.path));
        Clip clip=AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
       
    }
    
    public void soundLoop(String path) throws UnsupportedAudioFileException, LineUnavailableException, IOException{
        this.path=path;
        AudioInputStream audioInput=AudioSystem.getAudioInputStream(getClass().getResource(this.path));
        Clip clip=AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    //method to get the number of cards in the player
    public int cardsInPlayer(String playid){
        return playerHand(playid).size();
    }

    //method to get the cards in the player
    public CardPropertiesUNO getPlayerCard (String playid,int i){    
        ArrayList<CardPropertiesUNO> hand = playerHand(playid);
        return hand.get(i);
    }
    
    //method to check whether the user entered card is valid
    public boolean cardValid(CardPropertiesUNO card){
        return card.getCardColour() == validCardColour || card.getCardValue()== validCardValue;
    }
    
    //method to get the player's turn without any error
    public void playerTurn(String playid) throws InvalidPlayerTurnException{
        if(this.playerNames[this.player] != playid)
            throw new InvalidPlayerTurnException("Sorry it's not "+" 's turn",playid);
    }

    //Draw a card from the deck without any error
    public void submit(String playid) throws InvalidPlayerTurnException{
        playerTurn(playid);
        if (playingDeck.noCardsLeft())
        {
            playingDeck.deckReplace(stockpile);
            playingDeck.shuffleCards();
        }
        playerHand(playid).add(playingDeck.getCard());
        if(direction == false)
                player=(player+1)%playerNames.length;
        
            else if(direction == true){
                player=(player-1)%playerNames.length;
                if(player<0)
                    player=playerNames.length-1;
            }
        
        
        
    }
    
    public void setCardColour(CardPropertiesUNO.cardColours col){
        validCardColour=col;
    }    
  
    //submit  the card according to the rules of the game
    public void submitPlayCard(String playid,CardPropertiesUNO card,CardPropertiesUNO.cardColours colourDeclared) throws InvalidCardColourSubmissionException,InvalidCardValueSubmissionException,InvalidPlayerTurnException{
        playerTurn(playid);
        ArrayList<CardPropertiesUNO> playerHand = playerHand(playid);
        if(!cardValid(card))
        {
            if(card.getCardColour() == CardPropertiesUNO.cardColours.OTHER){
                validCardColour=card.getCardColour();
                validCardValue=card.getCardValue();
            }
            if(card.getCardColour() != validCardColour)
            {
                try{
                    playMusicStarts("sounds/error.wav");
                } catch (IOException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                JLabel msg = new JLabel("Sorry you must enter " + validCardColour+ " card");
                msg.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg);
                throw new InvalidCardColourSubmissionException("Sorry you must enter " + validCardColour+ " card",card.getCardColour(),validCardColour); 
            }
            
            else if (card.getCardValue() != validCardValue) {
                try{
                    playMusicStarts("sounds/error.wav");
                } catch (IOException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                JLabel msg1 = new JLabel("Sorry you must enter " + validCardValue+ " card");
                msg1.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg1);
                throw new InvalidCardValueSubmissionException("Sorry you must enter " + validCardValue+ " card",card.getCardValue(),validCardValue);
            }
        }  
        
        //after submitting the card to the topcard remove one card from the player
        playerHand.remove(card);
        if(cardsInPlayer(playid)==1){
            if(said==false){
                if(uno==false)
                {
                    
                    try {
                        playMusicStarts("sounds/error.wav");
                    } catch (IOException ex) {
                        Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JLabel msg5= new JLabel("Whoops! You didn't say UNO!!!");
                    msg5.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg5);
                    
                    JLabel msg6= new JLabel("You will get extra two cards");
                    msg6.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg6);
                    for(int i=0;i<2;i++)
                        playerHand(playid).add(playingDeck.getCard());
                }
            
                else if(uno==true){
                    uno=false;
                }
            }
            
            else if(said==true)
                said=false;
                
        }
        //if a hand is empty player wins that round and automatically 2nd round is called
        if (handIsEmpty(this.playerNames[player]))
        {
            //play music after winning a round 
            try {
                playMusicStarts("sounds/round_won.wav");
            } catch (IOException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/won/round_won.jpg"));
            JLabel msg2 = new JLabel("Congratulations! "+this.playerNames[player]+" You have won this round!!!");
            msg2.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg2,"Won",JOptionPane.PLAIN_MESSAGE,icon);
                //getting the score of the winning player
                for(int z=0;z<playerNames.length;z++){
                    int s=cardsInPlayer(playerNames[z]);
                    for(int u=0;u<s;u++){
                        if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.ONE)
                            score[player]+=1;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.TWO)
                            score[player]+=2;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.THREE)
                            score[player]+=3;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.FOUR)
                            score[player]+=4;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.FIVE)
                            score[player]+=5;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SIX)
                            score[player]+=6;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SEVEN)
                            score[player]+=7;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.EIGHT)
                            score[player]+=8;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.NINE)
                            score[player]+=9;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.DRAWTWO||playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.REVERSE||playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SKIP)
                            score[player]+=20;
                        else if(playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.WILD||playerHand(playerNames[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.WILDFOUR)
                            score[player]+=50;
                    }
                }
                
                //if the score of the player is greater than 500 he/she wins the game 
                if(score[player]>=500){
                     JLabel msg3 = new JLabel("Congratulations! "+this.playerNames[player]+" You have won the game!!!");
                     msg3.setFont(new Font("Arial",Font.BOLD,42));
                     JOptionPane.showMessageDialog(null, msg3);
                     gameStage.dispose();
                     new GameWon().setVisible(true);
                }
                
                //if not go to the next round after viewing the score of the winner in the prevoius round
                else if(score[player]<500){
                    JLabel msg4=new JLabel("Points in the round of the player "+playerNames[player]+" is "+score[player]);
                    msg4.setFont(new Font("Arial",Font.BOLD,42));
                     JOptionPane.showMessageDialog(null, msg4);
                     JLabel msg5= new JLabel("Welcome to the next round of UNO!!!");
                     msg5.setFont(new Font("Arial",Font.BOLD,42));
                     JOptionPane.showMessageDialog(null, msg5);
                     gameStage.dispose();
                     //go to next round
                     new MultiPlayerGameStage(new ArrayList<String>(Arrays.asList(playerNames)),score).setVisible(true);
                }
         
        }
        validCardColour=card.getCardColour();
        validCardValue=card.getCardValue();
        //add the played card to the stockpile
        stockpile.add(card);
        
        //if the game direction changes
        if(direction == false)
            player=(player+1)%playerNames.length;
        
        else if(direction == true){
            player=(player-1)%playerNames.length;
            if(player<0)
               player=playerNames.length-1;
        }
        
        //if the card is a wild or wild four card player can decide the colour
        if(card.getCardColour()== CardPropertiesUNO.cardColours.OTHER){
            validCardColour=colourDeclared;
        }
        
        //if the card is a draw two next player will draw 2 cards form the deck after displaying a message 
        if(card.getCardValue() == CardPropertiesUNO.cardValues.DRAWTWO){
            playid=playerNames[player];
            for(int i=0;i<2;i++)
                playerHand(playid).add(playingDeck.getCard());
            JLabel msg3 = new JLabel(playid+" draw 2 cards");     
        }
        
        //if the card is a wild four, next player must draw 4 cards from the deck after displaying a message 
         if(card.getCardValue() == CardPropertiesUNO.cardValues.WILDFOUR){
            playid=playerNames[player];
            for(int i=0;i<4;i++)
                playerHand(playid).add(playingDeck.getCard());
            
            if (direction==false)
                 player=(player+1)%playerNames.length;
             else if(direction==true){
                 player=(player-1)%playerNames.length;
                 if(player<0)
                     player=playerNames.length-1;
             }
        }
         
         //if the card is skip next player will be skipped after displaying a message 
         if(card.getCardValue() == CardPropertiesUNO.cardValues.SKIP){
             JLabel msg3 = new JLabel(playerNames[player]+" was skipped");
             msg3.setFont(new Font("Arial",Font.BOLD,42));
             JOptionPane.showMessageDialog(null, msg3);
             if (direction==false)
                 player=(player+1)%playerNames.length;
             else if(direction==true){
                 player=(player-1)%playerNames.length;
                 if(player<0)
                     player=playerNames.length-1;
             }
         }
         
        //if the card is reverse,change the playing direction after displaying a message 
        if(card.getCardValue() == CardPropertiesUNO.cardValues.REVERSE){
            JLabel msg3 = new JLabel(playid+" changed the game direction");
             msg3.setFont(new Font("Arial",Font.BOLD,42));
             JOptionPane.showMessageDialog(null, msg3);
             direction ^= true;
             if(direction== true){
                 if(playerNames.length==2){
                     player=player-1;
                     if(player==-1)
                         player=playerNames.length-1;
                 }
                 else{
                 player=(player-2)%playerNames.length;
                 if(player== -1)
                     player=playerNames.length-1;
                 if(player== -2)
                     player=playerNames.length-2;
                 }
             }
             else if (direction==false)
                 if(playerNames.length==2)
                     player=(player+1)%playerNames.length;
                 else
                 player=(player + 2)%playerNames.length;
        } 
    }    
    
}

//exception handling for the inavlid card colour
class InvalidCardColourSubmissionException extends Exception{
    
    private CardPropertiesUNO.cardColours expected;
    private CardPropertiesUNO.cardColours actual;
    
    public InvalidCardColourSubmissionException(String msg,CardPropertiesUNO.cardColours actual,CardPropertiesUNO.cardColours expected){
        this.actual=actual;
        this.expected=expected;
    }    
}

//exception handling for the inavlid submission
class InvalidCardValueSubmissionException extends Exception{
    private CardPropertiesUNO.cardValues expected;
    private CardPropertiesUNO.cardValues actual;
    
    public InvalidCardValueSubmissionException(String msg,CardPropertiesUNO.cardValues actual,CardPropertiesUNO.cardValues expected){
        this.actual=actual;
        this.expected=expected;
    }  
}

//exception handling for the inavlid player turn
class InvalidPlayerTurnException extends Exception{
    String playid;
    public InvalidPlayerTurnException(String msg,String pid){
        super(msg);
        playid=pid;
    } 
    public String getPlayid(){
        
        return playid;
    }    
}
