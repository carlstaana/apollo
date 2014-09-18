package com.apollo.training.games.cardgames.freecell;

import com.apollo.training.games.cardgames.Card;
import com.apollo.training.games.cardgames.Player;

public class FreeCellPlayer extends Player {
	
	private Card thrownCard = null;
	
	private boolean originOfBaseCard = false;;
	
	private boolean win = false;
	
	
	
	public FreeCellPlayer() {
		super();
	}
	
	public FreeCellPlayer(String name) {
		super(name);
	}
	
	
	
	public Card getThrownCard() {
		return thrownCard;
	}

	public void setThrownCard(Card thrownCard) {
		this.thrownCard = thrownCard;
	}

	public boolean isOriginOfBaseCard() {
		return originOfBaseCard;
	}

	public void setOriginOfBaseCard(boolean originOfBaseCard) {
		this.originOfBaseCard = originOfBaseCard;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}
}
