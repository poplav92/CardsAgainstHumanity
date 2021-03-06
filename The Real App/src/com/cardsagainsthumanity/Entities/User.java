package com.cardsagainsthumanity.Entities;

import java.util.*;

public class User 
{
	private List<User> friends;
	public String name;//Assume each person has a unique name
	public int gameRound;
	public List<String> otherUsers;
	public List<Integer> otherUsersScore;
	public String currentCzar;
	public boolean isCzar;
	public List<String> whiteCardsList;
	public List<Integer> whiteCardsID;
	public String blackCard;
	public int userScore;
	public boolean submitted;
	
	User()
	{
		submitted = false;
		otherUsers = new ArrayList<String>();
		otherUsersScore = new ArrayList<Integer>();
		whiteCardsList = new ArrayList<String>();
		whiteCardsID = new ArrayList<Integer>();
	}
	
	private String password;
	
	private int wins;
	private int losses;
	
	private int points;//points in the current game and we are assuming you are only playing one
	//game at a time
	
	private Card cardCzarCard;
	private List<Card> userHand;
	
	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Card getCardCzarCard() {
		return cardCzarCard;
	}

	public void setCardCzarCard(Card cardCzarCard) {
		this.cardCzarCard = cardCzarCard;
	}

	public List<Card> getUserHand() {
		return userHand;
	}

	public void setUserHand(List<Card> userHand) {
		this.userHand = userHand;
	}

	User(String name)
	{
		this.name = name;
	}
	
	public boolean isCardCzar()
	{
		if(isCzar == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void setPassWord(String passWord)
	{
		this.password = passWord;
	}
	public boolean removeFriend(User user)
	{
		for(int i=0; i<friends.size(); i++)
		{
			if(friends.get(i).name.equals(user.name))
			{
				friends.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean hasFriend(User user)
	{
		for(int i=0; i<friends.size(); i++)
		{
			if(friends.get(i).name.equals(user.name))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean addFriend(User user)
	{
		if(hasFriend(user))
			return false;
		else
		{
			friends.add(user);
			return true;	
		}
	}

	/*
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	*/
}
