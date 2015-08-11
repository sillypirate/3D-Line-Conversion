import java.util.Random;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.*;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;
public class Primary {

	private static double[][] cube = {
				{-1,  1, -1, 1},
				{ 1,  1, -1, 1},
				{ 1, -1, -1, 1},
				{-1, -1, -1, 1},
				{-1,  1,  1, 1},
				{ 1,  1,  1, 1},
				{ 1, -1,  1, 1},
				{-1, -1,  1, 1}
	};
	private static double[][] pcube = cube;
	
	private static double Vx = 6, Vy = 8, Vz = 7.5, D = 60, S = 15;
	
	private static double[][] T1 = {
				{  1,  0,  0,  0},
				{  0,  1,  0,  0},
				{  0,  0,  1,  0},
				{-Vx,-Vy,-Vz,  1}};
	
	private static double[][] T2 = {
				{  1,  0,  0,  0},
				{  0,  0, -1,  0},
				{  0,  1,  0,  0},
				{  0,  0,  0,  1}};
	
	private static double[][] T3 = {
				{-.8,  0, .6,  0},
				{  0,  1,  0,  0},
				{-.6,  0,-.8,  0},
				{  0,  0,  0,  1}};
	
	private static double[][] T4 = {
				{  1,  0,  0,  0},
				{  0, .8, .6,  0},
				{  0,-.6, .8,  0},
				{  0,  0,  0,  1}};
	
	private static double[][] T5 = {
				{  1,  0,  0,  0},
				{  0,  1,  0,  0},
				{  0,  0, -1,  0},
				{  0,  0,  0,  1}};
	
	private static double[][] N = {
				{D/S,  0,  0,  0},
				{  0,D/S,  0,  0},
				{  0,  0,  1,  0},
				{  0,  0,  0,  1}};
	
	
	static double[][] BasicTranslate(float Tx, float Ty, float Tz){
		double[][] T = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {Tx, Ty, Tz, 1}};
		return T;
	}
	
	
	static double[][] BasicScale(float Sx, float Sy, float Sz){
		double[][] S = {{Sx, 0, 0, 0},{0, Sy, 0, 0}, {0, 0, Sz, 0}, {0, 0, 0, 1}};
		return S;
	}
	
	static double[][] BasicXRotate(float Rx){
		double[][] A = {
				{1, 0, 0, 0},
				{0, Math.cos(Math.toRadians(Rx)), -Math.sin(Math.toRadians(Rx)), 0},
				{0, Math.sin(Math.toRadians(Rx)), Math.cos(Math.toRadians(Rx)), 0},
				{0, 0, 0, 1}
		};
		return A;
	}
	
	static double[][] BasicYRotate(float Ry){
		double[][] A = {
				{Math.cos(Math.toRadians(Ry)), 0, Math.sin(Math.toRadians(Ry)), 0},
				{0, 1, 0, 0},
				{-Math.sin(Math.toRadians(Ry)), 0, Math.cos(Math.toRadians(Ry)), 0},
				{0, 0, 0, 1}
		};
		return A;
	}
	
	static double[][] BasicZRotate(float Rz){
		double[][] A = {
				{Math.cos(Math.toRadians(Rz)), -Math.sin(Math.toRadians(Rz)), 0, 0},
				{Math.sin(Math.toRadians(Rz)), Math.cos(Math.toRadians(Rz)), 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
		return A;
	}
	
	static double[][] BasicRotate(float X, float Y, float Z){
		double[][]A;
		A = Concatenate(BasicXRotate(X), BasicYRotate(Y));
		A = Concatenate(A, BasicZRotate(Z));
		return A;
	}
	
	static double[][] Concatenate(double[][] matrix1, double[][] matrix2){
		double[][] Matrix = new double[4][4];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				for (int k = 0; k < 4; k++){
					Matrix[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		matrix1 = Matrix;
		return matrix1;
	}
	
	static double[][] ApplyTransformation(double[][] matrix, double[][] datalines){
		double[][] temp = new double[8][4];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 4; j++){
				for(int k = 0; k < 4; k++){
					
						temp[i][j] += (datalines[i][k]*matrix[k][j]);
					
				}
				
				
				
			}
			
		}
		
		return temp;
	}
	
	static void display(double[][] pts){
		
			 
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			 
			 glBegin(GL_LINES);
			 

			
			 glVertex3d(pts[4][0], pts[4][1], pts[4][2]);
			 glVertex3d(pts[5][0], pts[5][1], pts[5][2]);
			 
			 glVertex3d(pts[5][0], pts[5][1], pts[5][2]);
			 glVertex3d(pts[6][0], pts[6][1], pts[6][2]);
			 
			 glVertex3d(pts[6][0], pts[6][1], pts[6][2]);
			 glVertex3d(pts[7][0], pts[7][1], pts[7][2]);
			 
			 glVertex3d(pts[7][0], pts[7][1], pts[7][2]);
			 glVertex3d(pts[4][0], pts[4][1], pts[4][2]);
			 
			 
			 glVertex3d(pts[0][0], pts[0][1], pts[0][2]);
			 glVertex3d(pts[4][0], pts[4][1], pts[4][2]);
			 
			 glVertex3d(pts[1][0], pts[1][1], pts[1][2]);
			 glVertex3d(pts[5][0], pts[5][1], pts[5][2]);
			 
			 glVertex3d(pts[2][0], pts[2][1], pts[2][2]);
			 glVertex3d(pts[6][0], pts[6][1], pts[6][2]);
			 
			 glVertex3d(pts[3][0], pts[3][1], pts[3][2]);
			 glVertex3d(pts[7][0], pts[7][1], pts[7][2]);
			 
			 
			 glVertex3d(pts[0][0], pts[0][1], pts[0][2]);
			 glVertex3d(pts[1][0], pts[1][1], pts[1][2]);
			 
			 glVertex3d(pts[1][0], pts[1][1], pts[1][2]);
			 glVertex3d(pts[2][0], pts[2][1], pts[2][2]);
			 
			 glVertex3d(pts[2][0], pts[2][1], pts[2][2]);
			 glVertex3d(pts[3][0], pts[3][1], pts[3][2]);
			 
			 glVertex3d(pts[3][0], pts[3][1], pts[3][2]);
			 glVertex3d(pts[0][0], pts[0][1], pts[0][2]);
			 
			 
			glEnd();
			
			Display.sync(60);
			 Display.update();
			
			 
			
	}
	
	
	static double[][] transform(){
		double[][] v = Concatenate(T1, T2);
		v = Concatenate(v, T3);
		v = Concatenate(v, T4);
		v = Concatenate(v, T5);
		v = Concatenate(v, N);
		v = ApplyTransformation(v, cube);
		return v;
	}
	
	
	static void projection(double[][] pts){
		
		for(int i = 0; i < 8; i++){
			pcube[i][0] = (pts[i][0]/pts[i][2])*200+200;
			pcube[i][1] = (pts[i][1]/pts[i][2])*200+200;
		}
	}

	
	public static void main(String[] args) {
		
		int input = 0;
		float x, y, z;
		Scanner keyboard = new Scanner(System.in);
		
		try{
			Display.setDisplayMode(new DisplayMode(400, 400));
			Display.setTitle("Display Pixels");
			Display.create();
		}
		catch(LWJGLException e){
			e.printStackTrace();
		}
		 glMatrixMode(GL_PROJECTION);
		 glLoadIdentity();
		 
		 glOrtho(0, 1000, 0, 1000, -10, 10);
		// GLU.gluPerspective(field_of_vision, Display.getWidth()/Display.getHeight(), zNear, zFar);
		 //GL11.glViewport(-6, -8, 6, 8);
		 
		 
		 glMatrixMode(GL_MODELVIEW);
		 glLoadIdentity();
		 
		 cube = transform();
		 while(!Display.isCloseRequested()){
			 
			 projection(cube);
				
			 display(pcube);
			 
			
			input = keyboard.nextInt();
			
			if(input == 1){
				x = keyboard.nextInt();
				y = keyboard.nextInt();
				z = keyboard.nextInt();
				cube = ApplyTransformation(BasicTranslate(x, y, z), cube);
				 input = 0;
			 }
			 if(input == 2){
				 x = keyboard.nextInt();
					y = keyboard.nextInt();
					z = keyboard.nextInt();
					cube = ApplyTransformation(BasicScale(x, y, z), cube);
					 
				 input = 0;
			 }
			 if(input == 3){
				 x = keyboard.nextInt();
					y = keyboard.nextInt();
					z = keyboard.nextInt();
					cube = ApplyTransformation(BasicRotate(x, y, z), cube);
					 
				 input = 0;
			 }
		
			
		//cube = ApplyTransformation(BasicRotate(-10,10,10), cube);
		//cube = ApplyTransformation(BasicScale(2, 2, 1), cube);
		
			 
		
		 
	 }
	 Display.destroy();
	}

}
