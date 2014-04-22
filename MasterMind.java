import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import java.util.Random;

public class mastermind extends JApplet
{
	public final static int startX = 20;
	public final static int startY = 20;
	public final static int totalWidth = 380;
	public final static int totalHeight = 610;
	private final static char[] mapIntToChar = {'R','G','B','Y','O','W'};
	private static char[] computer = new char[4];
	private static char[][] user = new char[1][];
	private static String input = "";
	private static int line = 0;
	private static int offset = 0;
	private static boolean win = false;
	
	public void init(){
		Random ranNum=new Random();
		for(int i=0;i<computer.length;i++){
			computer[i] = mapIntToChar[ranNum.nextInt(6)];
		}
		popDialog();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		this.setSize(totalWidth+2*startX, totalHeight+2*startY);
		this.setBounds(0, 0, totalWidth+2*startX, totalHeight+2*startY);
		g.drawString("MasterMind", startX+totalWidth/2-25, startY-5);
		while(!win){
			g.setColor(Color.WHITE);
			g.fillRect(startX, startY, totalWidth, totalHeight);
			for(int i=0+offset;i<line;i++){
				for(int j=0;j<user[i].length;j++){
					addCircle(g,user[i][j],j,i-offset);
				}
			}
			popDialog();
		}
		g.setColor(Color.WHITE);
		g.fillRect(startX, startY, totalWidth, totalHeight);
		for(int i=0+offset;i<line;i++){
			for(int j=0;j<user[i].length;j++){
				addCircle(g,user[i][j],j,i-offset);
			}
		}
	}
	
	private void popDialog(){
		do{
			input = "";
			input = JOptionPane.showInputDialog("Make a guess!").toUpperCase();
		}while(!checkStr(input));
		if(line>0){
			char[][] temp = user;
			user = new char[line+1][];
			for(int i=0;i<line;i++) user[i] = temp[i];
		}
		user[line++] = addResult(input).toCharArray();
		if(line>12) offset++;
	}
	
	private String addResult(String input){
		char[] inputChar = input.toCharArray();
		int R = 0, W = 0;
		boolean[] filter = {true,true,true,true};
		for(int i=0;i<4;i++){
			if(inputChar[i]==computer[i]){
				R++;
				filter[i] = false;
			}
		}
		boolean[] filter2 = filter;
		for(int i=0;i<4;i++){
			if(filter[i]){
				for(int j=0;j<4;j++){
					if(filter2[j]&&(inputChar[i]==computer[j])){
						W++;
						filter2[j] = false;
						break;
					}
				}
			}
		}
		for(int i=0;i<R;i++) input+="R";
		for(int i=0;i<W;i++) input+="W";
		System.out.println(input);
		if(R==4) win = true;
		return input;
	}
	
	public boolean checkStr(String input){
		if(input.length()==4){
			char[] inputChar = input.toCharArray();
			for(int i=0;i<4;i++){
				boolean valid = false;
				for(int j=0;j<6;j++){
					if(inputChar[i]==mapIntToChar[j]){
						valid = true;
						break;
					}
				}
				if(!valid) return false;
			}
			return true;
		}
		else return false;
	}
	
	private static void addCircle(Graphics g,char colorCode,int Nx,int Ny){
		switch(colorCode){
		case 'R':
			g.setColor(Color.RED);
			break;
		case 'B':
			g.setColor(Color.BLUE);
			break;
		case 'Y':
			g.setColor(Color.YELLOW);
			break;
		case 'G':
			g.setColor(Color.GREEN);
			break;
		case 'W':
			g.setColor(Color.WHITE);
			break;
		case 'O':
			g.setColor(Color.ORANGE);
			break;
		default:
			return;
		}
		if(Nx<4){
			int r = 40;
			g.fillOval(startX+(Nx+1)*r*5/4-r, startY+(Ny+1)*r*5/4-r, r, r);
			g.setColor(Color.BLACK);
			g.drawOval(startX+(Nx+1)*r*5/4-r, startY+(Ny+1)*r*5/4-r, r, r);
			g.drawString( String.valueOf(colorCode), startX+(Nx+1)*50-r/2-3, startY+(Ny+1)*50-r/2+3);
		}
		else{
			int r = 20;
			g.fillOval(startX+200+(Nx-3)*r*2-r, startY+(Ny+1)*r*5/2-r*3/2, r, r);
			g.setColor(Color.BLACK);
			g.drawOval(startX+200+(Nx-3)*r*2-r, startY+(Ny+1)*r*5/2-r*3/2, r, r);
			g.drawString( String.valueOf(colorCode), startX+200+(Nx-3)*40-r/2-5, startY+(Ny+1)*50-r+5);
		}
	}
}
