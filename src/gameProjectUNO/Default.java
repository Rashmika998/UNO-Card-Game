/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Rashmika
 */
public class Default {
    boolean direction; //direction of the game
    private String[] playerName; //get the name of the player who is playing
    private int player; //represents the current player
    private DeckUNO playingDeck; //playing deck
    private ArrayList<ArrayList<CardPropertiesUNO>> hands; //all of the players hands
    private ArrayList<CardPropertiesUNO> stockpile; //stockpile
    private  CardPropertiesUNO.cardColours validCardColour; //valid card colour throught the game
    private CardPropertiesUNO.cardValues validCardValue; //valid card value throught the game
    ArrayList<CardPropertiesUNO> playerHand;
    String path; //path of the sound-clip
    ArrayList <JButton> cardButtons = new ArrayList<JButton>();
    String pc="Corona"; //name of the PC
    public boolean use=false; //boolean variable to check whether the game direction changes at the start
    public boolean used=false; //boolean variable to check whether PC hasn't draw a card from the pack
    int score[]=new int [10]; //array to store scores of the players during rounds
    public Default(){} //default constructor
    DefaultPlayer def;
    public boolean uno=false;
    public boolean said=false;

    /*This is almost same as the PlayGame class, so comments are used for additional functions*/
    
    public Default(String[] players,int score[],DefaultPlayer def)
    {
        this.def=def;
        this.score=score;
        playerName=players;
        player=0;
        playerName[player+1]=pc;
        playingDeck= new DeckUNO();
        //sound clip of shuffling cards
        try {
            playMusicStarts("sounds/shuffling_cards.wav");
        } catch (IOException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //shuffle the card pack
        playingDeck.shuffleCards();
        stockpile=new ArrayList<CardPropertiesUNO>();
        direction=false; //standard game direction
        hands= new ArrayList<ArrayList<CardPropertiesUNO>>();
        for(int i=0;i<playerName.length;i++)
        {
            //create the hand by distributing 7 cards for each player
            ArrayList<CardPropertiesUNO> hand= new ArrayList<CardPropertiesUNO>(Arrays.asList(playingDeck.getCard(7)));
            hands.add(hand); //adding the 7 cards to hands variable(all of every player hand)
        } 
    } 
    public void start(Default game)
    {
        CardPropertiesUNO card=playingDeck.getCard();
        validCardColour= card.getCardColour();
        validCardValue= card.getCardValue();
        
        if(card.getCardValue() == CardPropertiesUNO.cardValues.DRAWTWO || card.getCardValue() == CardPropertiesUNO.cardValues.WILD || card.getCardValue() == CardPropertiesUNO.cardValues.WILDFOUR)
            start(game);
        
        if (card.getCardValue() == CardPropertiesUNO.cardValues.SKIP)
        {
            JLabel msg = new JLabel("Whoops! " + playerName[player]+ " was skipped");
            msg.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg);
            
            JLabel msg2 = new JLabel("Corona's Turn!");
            msg2.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg2);
            
            player=1; 
            use=true; //give player turn to the PC
       
        }
        
        if (card.getCardValue()==CardPropertiesUNO.cardValues.REVERSE)
        {
            JLabel msg = new JLabel("Whoops! game direction was changed");
            msg.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg);
            
            JLabel msg2 = new JLabel("Corona's Turn!");
            msg2.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg2);
            
            if(direction==true)
                direction=false;
            
            else if(direction==false)
                direction =true;
            player=1;
            use=true; //give player turn to the PC
           
        }
        stockpile.add(card);
          
    }
    
    public ArrayList<CardPropertiesUNO> playerHand(String playid){    
        int n=Arrays.asList(playerName).indexOf(playid);
        return hands.get(n);
    }
    public int cardsInPlayer(String playid){
        return playerHand(playid).size();
    }

    public CardPropertiesUNO getPlayerCard (String playid,int i){    
        ArrayList<CardPropertiesUNO> hand = playerHand(playid);
        try{
            if(hand.size()==0){
                JLabel msg2 = new JLabel("Corona used a "+validCardColour+" "+validCardValue+" card and won the round!");
                msg2.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg2);
                playerHand(pc).remove(hand);//remove the card from PC
                won();
                
            }
            return hand.get(i);
        }
        catch(IndexOutOfBoundsException e){
             return hand.get(i-1);
        }
        
       
    }
    
     public CardPropertiesUNO topCard(){
        return new CardPropertiesUNO(validCardColour, validCardValue);
    }
    
    //method to get the current player value(0 or 1)
    public int givePlayer(){ 
        return player;
    }
     
     public boolean handIsEmpty(String playid){
        return playerHand(playid).isEmpty();   
    }
     
     public void playerTurn(String playid) throws InvalidPlayerTurnException{
        if(this.playerName[this.player] != playid)
            throw new InvalidPlayerTurnException("Sorry it's not "+" 's turn",playid);
    }
     
    public void submit(String playid) throws InvalidPlayerTurnException{
        playerTurn(playid);
        if (playingDeck.noCardsLeft())
        {
            playingDeck.deckReplace(stockpile);
            playingDeck.shuffleCards();
        }
        playerHand(playid).add(playingDeck.getCard());
        
       
            if(direction == false)
                player=(player+1)%2;
        
            else if(direction == true){
                player=(player-1)%2;
                if(player<0)
                    player=1;
            }
        
    }

      
    public void setCardColour(CardPropertiesUNO.cardColours col){
        validCardColour=col;
    } 
  
    public ImageIcon topCardImage(){
        return new ImageIcon(validCardColour+"_"+validCardValue+".png");
    }
    
     public String currentPlayer(){
        return this.playerName[this.player];
    }
     
    public boolean cardValid(CardPropertiesUNO card){
        return card.getCardColour() == validCardColour || card.getCardValue()== validCardValue;
    }
    
    //method to return the valid card value
    public CardPropertiesUNO.cardValues giveValidCard(){
        return validCardValue;
    }
    
    public CardPropertiesUNO.cardColours giveValidCardCol(){
        return validCardColour;
    }
    
    //method to gets audio when playing the game
    public void playMusicStarts(String path) throws IOException, LineUnavailableException, UnsupportedAudioFileException{
        this.path=path;
        AudioInputStream audioInput=AudioSystem.getAudioInputStream(getClass().getResource(this.path));
        Clip clip=AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    }
    
    //round and game winning conditions are checked in here
    public void won(){
            if(player==1){
                try {
                    playMusicStarts("sounds/round_lost.wav");
                } catch (IOException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/lost/round_lost.jpg"));
                JLabel msg2 = new JLabel("Whoops!!! You lost the round");
                msg2.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg2,"Lost",JOptionPane.PLAIN_MESSAGE,icon);
            }
            else if(player==0){
                try {
                    playMusicStarts("sounds/round_won.wav");
                } catch (IOException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                }
                ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/won/round_won.jpg"));
                JLabel msg2 = new JLabel("Congratulations "+this.playerName[player]+" You have won this round!!!");
                msg2.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg2,"Won",JOptionPane.PLAIN_MESSAGE,icon);
            }
            
                for(int z=0;z<playerName.length;z++){
                    int s=cardsInPlayer(playerName[z]);
                    for(int u=0;u<s;u++){
                        if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.ONE)
                            score[player]+=1;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.TWO)
                            score[player]+=2;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.THREE)
                            score[player]+=3;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.FOUR)
                            score[player]+=4;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.FIVE)
                            score[player]+=5;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SIX)
                            score[player]+=6;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SEVEN)
                            score[player]+=7;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.EIGHT)
                            score[player]+=8;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.NINE)
                            score[player]+=9;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.DRAWTWO||playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.REVERSE||playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.SKIP)
                            score[player]+=20;
                        else if(playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.WILD||playerHand(playerName[z]).get(u).getCardValue()==CardPropertiesUNO.cardValues.WILDFOUR)
                            score[player]+=50;
                    }
                }
                
                if(score[player]>=500){
                    if(player==1){
                        JLabel msg3 = new JLabel("Whoops!!! Corona won the game! Better luck next time!");
                        msg3.setFont(new Font("Arial",Font.BOLD,42));
                        JOptionPane.showMessageDialog(null, msg3);
                        def.dispose();
                        new GameLost().setVisible(true);
                    }
                    else if(player==0){
                        JLabel msg3 = new JLabel("Congratulations! "+this.playerName[player]+" You have won the game!!!");
                        msg3.setFont(new Font("Arial",Font.BOLD,42));
                        JOptionPane.showMessageDialog(null, msg3);
                        def.dispose();
                        new GameWon().setVisible(true);
                    }
                     
                }
                else if(score[player]<500){
                    JLabel msg4=new JLabel("Points so far of the winner "+playerName[player]+" is "+score[player]);
                    msg4.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg4);
                    JLabel msg5= new JLabel("Welcome to the next round of UNO!!!");
                    msg5.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg5);
                     
                    //proceed to the next round while closing the previusly played window
                    def.dispose();
                    new DefaultPlayer(new ArrayList<String>(Arrays.asList(playerName)),score).setVisible(true);
                }
         
        
    }
    
    public boolean pcWon(){
        if(cardsInPlayer(pc)==0)
            return true;
        else
            return false;
    }
    
    public void useDefaultCard(){
        use=false; //use must assign with false as it will result to the col method in the DefaultPlayer JFrame (this was used to check whether direction changes in the start)
        used=false; //assign used with false then it wll check whether PC has drawn a card from deck
        int i;
        for(i=0;i<playerHand(pc).size();i++){ //go through all the cards in PC's hand
            if(getPlayerCard(pc, i).getCardValue()==CardPropertiesUNO.cardValues.WILD){
                used=true; //if the condition is true PC won't draw a card
                
                validCardColour=getPlayerCard(pc, i).getCardColour(); //assign the valid colour
                validCardValue=getPlayerCard(pc, i).getCardValue(); //assigne the valid value
                playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
  
                if(direction == false)
                    player=0;
        
                else if(direction == true)
                    player=0;
                
                break;
            }
                  
            else if(getPlayerCard(pc, i).getCardValue()==CardPropertiesUNO.cardValues.WILDFOUR){
                used=true; //if the condition is true PC won't draw a card
                try {
                    playMusicStarts("sounds/action_cards.wav");
                } catch (IOException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                validCardColour=getPlayerCard(pc, i).getCardColour(); 
                validCardValue=getPlayerCard(pc, i).getCardValue(); 
                playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                for(int j=0;j<4;j++)
                    playerHand(playerName[0]).add(playingDeck.getCard());
                     
      
                //if PC used a WILD FOUR assign player=1 as player will forfeit his/her turn
                if(direction == false)
                    player=1;
        
                else if(direction == true)
                    player=1;
                
                break;
            }
                
            else if(validCardColour==getPlayerCard(pc, i).getCardColour()){
                if(getPlayerCard(pc, i).getCardValue()==CardPropertiesUNO.cardValues.DRAWTWO){
                    used=true; //if the condition is true PC won't draw a card
                    validCardColour=getPlayerCard(pc, i).getCardColour();
                    validCardValue=getPlayerCard(pc, i).getCardValue();
                    playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                    stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                    for(int j=0;j<2;j++)
                        playerHand(playerName[0]).add(playingDeck.getCard());
                    
                    try {
                        playMusicStarts("sounds/action_cards.wav");
                    } catch (IOException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    JLabel msg3 = new JLabel("Corona used a DRAW TWO card and "+playerName[0]+" will get extra two cards");
                    msg3.setFont(new Font("Arial",Font.BOLD,36));
                    JOptionPane.showMessageDialog(null,msg3);
                   
                   
                    if(direction == false)
                        player=0;
        
                    else if(direction == true)
                        player=0;
                     
                    break;
                }
            
                else if(getPlayerCard(pc, i).getCardValue()==CardPropertiesUNO.cardValues.SKIP){
                    try {
                        playMusicStarts("sounds/action_cards.wav");
                    } catch (IOException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    used=true; //if the condition is true PC won't draw a card
                    validCardColour=getPlayerCard(pc, i).getCardColour();
                    validCardValue=getPlayerCard(pc, i).getCardValue();
                    JLabel msg3 = new JLabel("Corona used a SKIP card and "+playerName[0]+" was skipped");
                    msg3.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg3);
                    playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                    stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                    
                    //if PC used a SKIP assign player=1 as player will forfeit his/her turn
                    if(direction == false)
                        player=1;
        
                    else if(direction == true)
                        player=1;
                    break;
                }
            
                else if(getPlayerCard(pc, i).getCardValue()==CardPropertiesUNO.cardValues.REVERSE){
                    try {
                        playMusicStarts("sounds/action_cards.wav");
                    } catch (IOException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    used=true; //if the condition is true PC won't draw a card
                    validCardColour=getPlayerCard(pc, i).getCardColour();
                    validCardValue=getPlayerCard(pc, i).getCardValue();
                    JLabel msg3 = new JLabel("Corona used a REVERSE card and changed the game direction");
                    msg3.setFont(new Font("Arial",Font.BOLD,42));
                    JOptionPane.showMessageDialog(null, msg3);
                    playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                    stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                    direction ^= true;
                    
                    //if PC used a SKIP assign player=1 as player will forfeit his/her turn
                    if(direction == false)
                        player=1;
        
                    else if(direction == true)
                        player=1;
                    
                    break;   
                }
            
                //if the colour and the value matches
                else if(getPlayerCard(pc, i).getCardValue()==validCardValue){ 
                    used=true; //if the condition is true PC won't draw a card
                    validCardColour=getPlayerCard(pc, i).getCardColour();
                    validCardValue=getPlayerCard(pc, i).getCardValue();
                    playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                    stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                    if(direction == false)
                        player=0;
        
                    else if(direction == true)
                        player=0;
                    break;
                }
            
                //if the only the colour matches
                else{
                    used=true; //if the condition is true PC won't draw a card
                    validCardColour=getPlayerCard(pc, i).getCardColour();
                    validCardValue=getPlayerCard(pc, i).getCardValue();
                    playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                    stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                   
                    if(direction == false)
                        player=0;
        
                    else if(direction == true)
                        player=0;
                    break;
                }
                     
            }
        
            //if the value matches
            else if(getPlayerCard(pc, i).getCardValue()==validCardValue){
                used=true;
                validCardColour=getPlayerCard(pc, i).getCardColour();
                validCardValue=getPlayerCard(pc, i).getCardValue();
                playerHand(pc).remove(getPlayerCard(pc, i)); //remove the card from PC
                stockpile.add(getPlayerCard(pc, i)); //add the card to the stockpile
                 
                if(direction == false)
                    player=0;
        
                else if(direction == true)
                     player=0;
                break;
            }
             
        }
        
        //if there are no matching cards PC will draw a card from the deck as "used=false" in thw begining of the function
        if(used==false){
            try {
                submit(pc);
            }   
            catch(InvalidPlayerTurnException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            }
            JLabel msg = new JLabel("Corona drew a card from the deck");
            msg.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null,msg);

        }
        
        if(cardsInPlayer(pc)==1)
        {
        try {
            playMusicStarts("sounds/uno.wav");
        } catch (IOException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(PlayGame.class.getName()).log(Level.SEVERE, null, ex);
        }
            ImageIcon icon=new ImageIcon(PlayGame.class.getResource("UNOimages/uno!/UNO!.png"));
            JLabel msg1 = new JLabel(pc);
            msg1.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg1,"UNO!",JOptionPane.PLAIN_MESSAGE,icon);
        }
        
    }
    
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
                JLabel msg = new JLabel("Sorry you must enter atleast " +validCardColour+ " card");
                msg.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg);
                throw new InvalidCardColourSubmissionException("Sorry you must enter atleast " + validCardColour+ " card",card.getCardColour(),validCardColour); 
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
                JLabel msg1 = new JLabel("Sorry you must enter atleast " +validCardValue+ " card");
                msg1.setFont(new Font("Arial",Font.BOLD,42));
                JOptionPane.showMessageDialog(null, msg1,"Error",JOptionPane.ERROR_MESSAGE);
                throw new InvalidCardValueSubmissionException("Sorry you must enter atleast " + validCardValue+ " card",card.getCardValue(),validCardValue);
            }
        }  
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
                    
                    JLabel msg6= new JLabel("You will get extra two cards as a penalty");
                    msg6.setFont(new Font("Arial",Font.BOLD,40));
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
        
        if (handIsEmpty(this.playerName[player])){
            won();
        }
        
        //won();
        validCardColour=card.getCardColour();
        validCardValue=card.getCardValue();
        stockpile.add(card);
        
        if(direction == false)
            player=1;
        
        else if(direction == true)
            player=1;
        
        if(card.getCardColour()== CardPropertiesUNO.cardColours.OTHER){
            try {
                playMusicStarts("sounds/action_cards.wav");
            } catch (IOException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            }
            validCardColour=colourDeclared;
            if(direction == false)
                player=1;
        
            else if(direction == true)
                player=1;
        }
        
        if(card.getCardValue() == CardPropertiesUNO.cardValues.DRAWTWO){
            try {
                playMusicStarts("sounds/action_cards.wav");
            } catch (IOException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            playid=pc;
            for(int i=0;i<2;i++)
                playerHand(playid).add(playingDeck.getCard());
            
            JLabel msg3 = new JLabel(playerName[0]+" used a DRAW TWO card and "+pc+" will get extra 2 cards"); 
            msg3.setFont(new Font("Arial",Font.BOLD,36));
            JOptionPane.showMessageDialog(null, msg3);
        }
        
        if(card.getCardValue() == CardPropertiesUNO.cardValues.WILDFOUR){
            
            playid=pc;
            for(int i=0;i<4;i++)
                playerHand(playid).add(playingDeck.getCard());
            
            if (direction==false)
                player=0;
            else if(direction==true){
                player=0;
            }
        }
         
        if(card.getCardValue() == CardPropertiesUNO.cardValues.SKIP){
            try {
                playMusicStarts("sounds/action_cards.wav");
            } catch (IOException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            }
            JLabel msg3 = new JLabel(pc+" was skipped");
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3);
            if (direction==false)
                player=0;
            else if(direction==true){
                player=0;
            }
        }
         
        if(card.getCardValue() == CardPropertiesUNO.cardValues.REVERSE){
            try {
                playMusicStarts("sounds/action_cards.wav");
            } catch (IOException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Default.class.getName()).log(Level.SEVERE, null, ex);
            }
            JLabel msg3 = new JLabel(playid+" changed the game direction");
            msg3.setFont(new Font("Arial",Font.BOLD,42));
            JOptionPane.showMessageDialog(null, msg3);
            direction ^= true;
            if(direction== true){
                  player=0;
            }
            else{
                player=0;
            }
        }
        
     
    }       
}
   

