import java.util.Random;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Phuong Thanh Nguyen
 *
 */
public class UnluckyRobot {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int totalScore = 300;
		int itrCount = 0;
		int x = 0;
		int y = 0;
		int exceedPunish = -2000;
		do {
			displayInfo(x, y, itrCount, totalScore);
			itrCount++;
			char direction = inputDirection();
			x = movementX(x, direction);
			y = movementY(y, direction);
			int reward = reward();	// the returned value of int reward() function
			if (!(doesExceed(x, y, direction))) {
				int punish = punishOrMercy(direction, reward); 
				totalScore = totalScore + punish + directionPayment(direction);
			}
			else {
				punishOrMercy(direction, reward); 
				System.out.printf("Exceed boundary, -2000 damage applied\n");
				totalScore = totalScore + reward + directionPayment(direction) + exceedPunish;
			}	
			System.out.println();
		} while (!(isGameOver(x, y, totalScore, itrCount)));
			evaluation(totalScore);
	}
	
	/**
	 * To calculate the x coordinate of the robot after moving
	 * @param x stores the x coordinate of the robot
	 * @param y stores the y coordinate of the robot
	 * @param direction stores the direction of the user input
	 * @return the x coordinate of the robot after moving
	 */
	public static int movementX(int x, char direction) {
		if (direction == 'r')   
			x--;
		else if (direction == 'l') 
			x++;
		return x;
	}
	
	/**
	 * To calculate the y coordinate of the robot after moving
	 * @param x stores the x coordinate of the robot
	 * @param y stores the y coordinate of the robot
	 * @param direction stores the direction of the user input
	 * @return the y coordinate of the robot after moving
	 */
	public static int movementY(int y, char direction) {
		if (direction == 'd')
			y--;
		else if (direction == 'u')
			y++;
		return y;
	}
	
	/**
	 * To calculate the payment of the robot after each move
	 * @param direction stores the direction of the user input
	 * @return the payment the robot must pay after each move
	 */
	public static int directionPayment(char direction) {
		int payment = 0;
		switch(direction) {
			case 'r':
			case 'l':
			case 'd':
				return payment = - 50;
			case 'u':
				return payment = - 10;
		}
		return payment;
	}
	
	/**
	 * To print a message in the console reporting the current x and y coordinate of the robot, 
	 * the total score, and the number of iterations made so far
	 * @param x stores the x coordinate of the robot
	 * @param y stores the y coordinate of the robot
	 * @param itrCount keeps track of the number of iterations
	 * @param totalScore keeps track of the total number of points obtained
	 */
	public static void displayInfo(int x, int y, int itrCount, int totalScore) {
		System.out.printf("For point" + "(X=" + x + ", Y=" + y + ") at iterations: " + itrCount 
				+ " the total score is: " + totalScore + "\n");		
	}
	
	/**
	 * check if the robot exceed the grid limits after taking a step towards or not
	 * @param x stores the x coordinate of the robot
	 * @param y stores the y coordinate of the robot
	 * @param direction stores the direction of the user input
	 * @return true if the robot would exceed the grid limits after taking a step towards, false otherwise
	 */
	public static boolean doesExceed(int x, int y, char direction) {
		if (x > 4)
			return true;
		else if (y < 0)
			return true;
		else if (x < 0)
			return true;
		else if (y > 4)
			return true;
		else 
			return false;
	}
	
	/**
	 * this function will be called when the robot makes and ends up on a cell, It returns a number
	 * as the reward or the punishment of entering that cell. Once called, the function will roll a dice
	 * and return a number based on the following rules:
	 * 1. if 1 is displayed it will return -100
	 * 2. if 2 is displayed it will return -200
	 * 3.If 3 is displayed it will return -300.
	 * 4. If 4 is displayed it will return 300.
	 * @return a number based on the rules, the returned value will be stored in the variable reward in the main function
	 */
	public static int reward() {
		Random rand = new Random();
		int reward = 0;
		int dice = rand.nextInt(6) + 1;
		switch (dice) {
			case 1: 
				System.out.printf("Dice: 1, reward: -100\n");
				return reward = -100;
			case 2: 
				System.out.printf("Dice: 2, reward: -200\n");
				return reward = -200;
			case 3:
				System.out.printf("Dice: 3, reward: -300\n");
				return reward = -300;
			case 4:
				System.out.printf("Dice: 4, reward: 300\n");
				return reward = 300;
			case 5:
				System.out.printf("Dice: 5, reward: 400\n");
				return reward = 400;
			case 6:
				System.out.printf("Dice: 6, reward: 600\n");
				return reward = 600;
		}
		return reward;
	}
	
	/**
	 * To help the robot in case it faced a lot of damage in the current step
	 * However, this function will only be called only if the robot's reward was negative 
	 * and the direction that the user inputed was up.
	 * Then, it will flip the coin and for zero (tail), it will return zero
	 * and for one (head) it will return the initial reward (the same reward that the function received as an input).
	 * @param direction stores the direction the user inputs
	 * @param reward the returned value of int reward() function
	 * @return int which will be the value of zero or the initial reward
	 */
	public static int punishOrMercy(char direction, int reward) {
		Random rand = new Random();
		int flip = rand.nextInt(2);
		int newReward = 0;
		if (reward < 0 && direction == 'u') {
			if (flip == 0) {
				reward = 0;
				System.out.printf("Coin: tail | Mercy, the negative reward is removed.\n");
			}
			else {
				newReward = reward;
				System.out.printf("Coin: head | No mercy, the negative rewarded is applied.\n");
			}
		}
		return newReward;
	}
	
	/**
	 * This function will bring a string to title case.
	 * Assume the input string only contains two words, separated by a space.
	 * @param str the string inputed
	 * @return the string in title case
	 */
	public static String toTitleCase(String str) {
		int spaceIdx = str.indexOf(' ');	
		char c = str.charAt(0);					
		char c1 = str.charAt(spaceIdx + 1);
		c = Character.toTitleCase(c);	// the first letter of the first word in title case
		c1 = Character.toTitleCase(c1);	// the first letter of the second word in title case
		String str1 = str.substring(1, spaceIdx);	// the rest of the first word
		String str2 = str.substring(spaceIdx + 2);	// the rest of the second word
		
		return c + str1 + " " + c1 + str2;
	}
	
	/**
	 * This function will be called at the end of the game
	 * prints a statement based on the value of the total score
	 * @param totalScore keeps track of the total number of points obtained
	 */
	public static void evaluation(int totalScore) {
		Scanner console = new Scanner(System.in);
		System.out.print("Enter your name (only two words): ");
		String str = console.nextLine();
		str = str.toLowerCase();
		String name = toTitleCase(str);
		if (totalScore >= 2000)
			System.out.printf("Victory, " + name + ", your score is " + totalScore);
		else
			System.out.printf( "Mission falied, " + name + ", your score is " + totalScore);
	}

	/**
	 * This function will be called if the user inputed anything except 'u', 'd', 'l', 'r'
	 * Then it will be kept asked to enter a new char until the entered char matched one of them
	 * @param direction stores the direction the user inputs
	 * @return the correct inputed char
	 */
	public static char inputDirection() {
		Scanner input = new Scanner(System.in);
		char direction = 0;  
		do {
			System.out.print("Please input a valid direction: ");
			direction = input.next().charAt(0);  
		} while (direction != 'r' && direction != 'l' && direction != 'u' && direction != 'd');
		return direction;
	}
	
	/**
	 * To check if the game is over, the game will be terminated if any of the following condition meets:
	 * 1. The number of the steps/iterations exceeds 20
	 * 2. Your total score falls under -1,000
	 * 3. Your total score exceeds 2,000
	 * 4. You reach one of the end states
	 * @param x stores the x coordinate of the robot
	 * @param y stores the y coordinate of the robot
	 * @param totalScore keeps track of the total number of points obtained
	 * @param itrCount keeps track of the number of iterations.
	 * @return true if the game is over, otherwise false
	 */
	public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
		if (itrCount == 20) 
			return true;
		else if (totalScore < -1000)
			return true;
		else if (totalScore >= 2000)
			return true;
		else if (x == 4 && y == 4)
			return true;
		else if (x == 4 && y == 0)
			return true;
		else
			return false;
	}
}
 