package unitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BowlingGameTest {
	Game bg = null;
	private void rollMany(int n,int pins) {
		for(int i=0;i <n; i++) {
			bg.roll(pins);
		}
	}
	@BeforeEach
	public void before() {
		bg = new Game();
	}
	@AfterEach
	public void after() {
		bg = null;
	}
	@Test
	public void testGutter() {
		rollMany(20,0);
		assertEquals(0,bg.score());
	}
	@Test
	public void testAllOne() {
		rollMany(20,1);
		assertEquals(20,bg.score());
	}
	@Test
	public void testOneSpace() {
		rollSpare();
		bg.roll(3);
		rollMany(17,0);
		assertEquals(16,bg.score());
	}
	@Test
	public void testOneStrike() {
		rollStrike();
		bg.roll(3);
		bg.roll(4);
		rollMany(16,0);
		assertEquals(24,bg.score());
	}
	@Test
	public void testPerfectGame() {
		rollMany(12,10);
		assertEquals(300,bg.score());
	}
	private void rollSpare() {
		bg.roll(5);
		bg.roll(5);
	}
	private void rollStrike() {
		bg.roll(10);
	}
}
class Game{
	//private int score = 0;
	private int rolls[] = new int [21];
	private int currentRoll = 0;
	public  void roll(int pins) {
		rolls[currentRoll++]=pins;
	}
	public int score() {
		int score = 0;
		int frameIndex = 0;  //當下局數的第一顆球
		for(int frame = 0; frame <10;frame++) {
			if(isStrike(frameIndex)) {
				score += 10+ strikeBonus(frameIndex); //rolls[frameIndex+1]+ rolls[frameIndex+2];
				frameIndex+=1;
			}else if(isSpare(frameIndex)) {
				score += 10+spareBonus(frameIndex);//rolls[frameIndex+2];
				frameIndex+=2;   //跳至下一局 
			}else {
				score += sumOfBallsIndexFrame(frameIndex);//rolls[frameIndex]+rolls[frameIndex+1];
				frameIndex+=2;   //跳至下一局
			}
		}
		return score;
	}
	private int sumOfBallsIndexFrame(int frameIndex) {
		return rolls[frameIndex]+rolls[frameIndex+1];
	}
	private int spareBonus(int frameIndex) {
		return rolls[frameIndex+2];
	}
	private int strikeBonus(int frameIndex) {
		return rolls[frameIndex+1]+rolls[frameIndex+2];
	}
	private boolean isSpare(int frameIndex) {
		return rolls[frameIndex] + rolls[frameIndex+1] == 10;
	}
	private boolean isStrike(int frameIndex) {
		return rolls[frameIndex] == 10;
	}
}