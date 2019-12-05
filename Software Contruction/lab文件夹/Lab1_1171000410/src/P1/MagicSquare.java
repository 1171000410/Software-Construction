package P1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MagicSquare {
	public boolean isLegalMagicSquare(String fileName) {
		
		try {
			File file = new File(fileName);
			BufferedReader reader = null;
			int row = 0;
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			line = reader.readLine(); // 读文件的第一行，第一次行单独处理是为了确定矩阵参数大小
			String[] element = line.split("\t");
			int length = element.length;
			int[][] matrix = new int[length][length];

			int sumR[] = new int[length];
			int sumC[] = new int[length];
			int sumD[] = new int[2];

			for (int i = 0; i < length; i++) {
				if (line.split(".").length != 0) {
					System.out.println("数据存在浮点数");
					return false;
				}

				if (line.split("-").length > 1) {
					System.out.println("数据存在负数");
					return false;
				} else {
					matrix[row][i] = Integer.valueOf(element[i]);
					sumR[row] += matrix[row][i];
				}
			}
			while ((line = reader.readLine()) != null) {// 依次读取文件的接下来部分
				row++;
				element = line.split("\t");
				if (element.length != length) {// 如果这个条件不满足则不是一个矩阵
					System.out.println("不是矩阵！");
					return false;
				}
				for (int i = 0; i < length; i++) {
					if (line.split(".").length != 0) {
						System.out.println("数据存在浮点数");
						return false;
					}

					if (line.split("-").length > 1) {
						System.out.println("数据存在负数");
						return false;
					} else {
						matrix[row][i] = Integer.valueOf(element[i]);
						sumR[row] += matrix[row][i];
					}
				}
			}
			reader.close();
			if (row != length - 1) { // 如果行数和列数不等则不可能是magic square
				System.out.println("行列数不相等！");
				return false;
			}
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					sumC[i] += matrix[j][i]; // 列和
					if (i == j) {
						sumD[0] += matrix[i][j]; // 对角线和
					}

					if (i + j == length - 1) {
						sumD[1] += matrix[i][j]; // 对角线和
					}
				}
			}
			if (sumD[0] != sumD[1]) {
				System.out.println("对角线和不等");
				return false;
			}

			int sum = sumD[0];

			for (int i = 0; i < length; i++) {
				if (sumR[i] != sum || sumC[i] != sum) {
					System.out.println("行列和不相等！");

					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	public static boolean generateMagicSquare(int n) {
		File file = new File("src/P1/txt/" + 6 + ".txt");
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n; //最小的数放在第一行正中
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)  
				row++;
			else {
				if (row == 0)   //最上到最下
					row = n - 1;
				else
					row--;
				if (col == (n - 1))  //最右到最左
					col = 0;
				else
					col++;   //每个数放在上个数的右上一格
			}
		}
		try {
		
		FileWriter writer = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(writer);
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++) {
				out.write(magic[i][j] + "\t");
				System.out.print(magic[i][j] + "\t");
			}
			String data = "\n";
			out.write(data);
			System.out.println();
		}
		out.close();
		}catch (IOException e) {
            e.printStackTrace();
        }
		return true;
	}

	public static void main(String[] args) {
		System.out.println("请输入n的值:");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.close();
		if(n%2 !=0 && n>0) {
		generateMagicSquare(n);
		}
		MagicSquare magic = new MagicSquare();
		for (int i = 1; i <= 6; i++) {
			if(i==6 &&(n%2 ==0 || n<0)) {
				System.out.println("6:false");
				break;
			}
			if (magic.isLegalMagicSquare("src/P1/txt/" + i + ".txt"))
				System.out.println(i + ":true");
			else
				System.out.println(i + ":false");

		}

	}

}
