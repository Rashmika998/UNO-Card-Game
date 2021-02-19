
package gameProjectUNO;

public class CardPropertiesUNO {
    enum cardValues
	{
		ZERO,ONE,TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,DRAWTWO,REVERSE,SKIP,WILD,WILDFOUR;
		
		static final cardValues[] CARDVALUES= cardValues.values(); //get all the elements in the cardValues enumeration and made it final to have it constant
		public static cardValues giveCardValue(int index)
		{
			return cardValues.CARDVALUES[index]; //returns the corresponding value in the card
		}
	}
	
    enum cardColours
    {
	BLUE,YELLOW,GREEN,RED,OTHER; //OTHER represents the wild and wild_four in the UNO pack
		
	static final cardColours[] COLOURS = cardColours.values(); //get all the elements in the cardColours enumeration and made it final to have it constant
	public static cardColours giveColour(int index)
	{
	    return cardColours.COLOURS[index]; //returns the corresponding colour
	}
    }
    
    private final cardValues VALUE_OF_CARD; //value must keep constant and only accessible in this class
    private final cardColours CARD_COLOUR; //colour must be constant and only accessible in this class
	
    public CardPropertiesUNO(cardColours CARD_COLOUR,cardValues VALUE_OF_CARD)
    {
	this.CARD_COLOUR=CARD_COLOUR;
	this.VALUE_OF_CARD =VALUE_OF_CARD;
    }
	
    public cardValues getCardValue()
    {
	return VALUE_OF_CARD; //return the card value(constant)
    }
	
    public cardColours getCardColour()
    {
	return CARD_COLOUR; //return the card colour(constant)
    }
	
    @Override
    public String toString()
    {
	return (CARD_COLOUR+"_"+VALUE_OF_CARD);
    }
    
}
