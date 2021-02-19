
package gameProjectUNO;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;

public class DeckUNO {
    private int noOfCardsInDeck;
    private CardPropertiesUNO[] cardPack;
    public DeckUNO()
    {
	cardPack=new CardPropertiesUNO [108]; //no of cards in UNO pack
	deckReset();
    }
	
    public void deckReset()
    {
	CardPropertiesUNO.cardColours[] colours=CardPropertiesUNO.cardColours.values();
	noOfCardsInDeck=0;
	for(int colourIndex=0;colourIndex<colours.length-1;colourIndex++) //showing the colours except the wild
	{
	    CardPropertiesUNO.cardColours colour=colours[colourIndex]; //card colour gets the value in the array starting with blue
	    cardPack[noOfCardsInDeck++]=new CardPropertiesUNO(colour,CardPropertiesUNO.cardValues.giveCardValue(0)); //add the zero value(1 zero in each colour) to the deck
			
	    for(int valueIndex=1;valueIndex<10;valueIndex++)
	    {
		for(int i=0;i<2;i++) //add 2 cards in each colour (there are 2 cards in 1-9 values)
		cardPack[noOfCardsInDeck++]=new CardPropertiesUNO(colour,CardPropertiesUNO.cardValues.giveCardValue(valueIndex));	
	    }
			
            //creating an array with the remaining values in the card pack in each colour(drawTwo,reverse,skip)
	    CardPropertiesUNO.cardValues[] values=new CardPropertiesUNO.cardValues[] {CardPropertiesUNO.cardValues.DRAWTWO,CardPropertiesUNO.cardValues.REVERSE,CardPropertiesUNO.cardValues.SKIP};
            for (CardPropertiesUNO.cardValues value : values)
            {
                for (int i = 0; i<2; i++)
                {
                    cardPack[noOfCardsInDeck++] = new CardPropertiesUNO(colour, value); //add 2 cards in each colour (there are 2 cards for these values)
                }
            }
		
        }
	//creating an array with wild and wildFour cards to add it to the pack
	CardPropertiesUNO.cardValues[] values =new CardPropertiesUNO.cardValues[] {CardPropertiesUNO.cardValues.WILD,CardPropertiesUNO.cardValues.WILDFOUR};
        for (CardPropertiesUNO.cardValues value : values)
        {
            for (int x = 0; x<4; x++)
            {
                //there are 4 wild and wildFour cards in the pack
                cardPack[noOfCardsInDeck++] = new CardPropertiesUNO(CardPropertiesUNO.cardColours.OTHER, value);
            }
        }
    }
    //shuffle the cards
    public void shuffleCards()
    {
        int num=cardPack.length;
        Random ran=new Random();
        for(int i=0;i<cardPack.length;i++)
        {
            int randVal=i+ran.nextInt(num-i); //generate a random index from the remaining cards to shuffle(swap)
            CardPropertiesUNO randCard=cardPack[randVal];
            cardPack[randVal]=cardPack[i];
            cardPack[i]=randCard;
        }
    }
    
    //this method returns true when there are no cards in the deck
    public boolean noCardsLeft()
    {
        return noOfCardsInDeck==0;
    }
        
    //replaces the deck with the stockpile
    public void deckReplace(ArrayList<CardPropertiesUNO> cards)
    {
        cardPack=cards.toArray(new CardPropertiesUNO[cards.size()]);
        noOfCardsInDeck=cardPack.length;
    } 
    
    //this method used when draw cards from the deck
    public CardPropertiesUNO getCard() throws IllegalArgumentException
    {
        if(noCardsLeft())
            throw new IllegalArgumentException("Sorry! You cannot get a card because the card deck is empty");
        
        return cardPack[--noOfCardsInDeck];
    }   
    
    //when get multiple cards(wildFour)
    public CardPropertiesUNO[] getCard(int n)
    {
        if(n<0)
            throw new IllegalArgumentException("Error! Entered value is invalid");
        
        if(n>noOfCardsInDeck)
            throw new IllegalArgumentException("Sorry! Not enough cards left in the deck");
        
        CardPropertiesUNO[] giveCards=new CardPropertiesUNO[n];
        for(int y=0;y<n;y++)
        {
            noOfCardsInDeck--;
            giveCards[y]=cardPack[noOfCardsInDeck];
        }
        return giveCards;
    }   
    
    public ImageIcon giveCardImage() throws IllegalArgumentException //get every card with images
    {
        if(noCardsLeft())
            throw new IllegalArgumentException("Sorry! You cannot get a card because the card deck is empty");
        
        return new ImageIcon(cardPack[--noOfCardsInDeck].toString()+".png"); //return card with the colour and the number
    }    
}
