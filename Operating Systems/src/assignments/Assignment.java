package assignments;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class Assignment extends Thread{
	static char[] alpha = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	static int count = 0, totalAstCount = 0;
	static String outPut = "";

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		Scanner in = new Scanner(System.in);
		String filePath = getFileName(in);
		// C:\Users\gabev\Downloads\Operating Systems\OSQuiz.txt
		String word = "", sub = "";
		in = new Scanner(new File(filePath));
		while (in.hasNextLine()) {

			Runnable substitute, hill, summit, replace;
			Thread thread1 = new Thread(), thread2 = new Thread(),
					thread3 = new Thread(), thread4 = new Thread();
			word = in.nextLine();
			System.out.printf("%n%s%n", word);
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == '*') {
					count++;
				} else {

					if (count == 1) {
						sub = word.substring(i);
						String[] words = sub.split("\\*+|\\*+\s|\s\\*+");
						// System.out.printf("Substitute thread input: %s%n", words[0].trim());
						substitute = new Substitute(words[0].trim());
						thread1 = new Thread(substitute);

					} else if (count == 2) {
						sub = word.substring(i);

						String[] words = sub.split("\\*+|\\*+\s|\s\\*+");
						// System.out.printf("Hill thread input: %s%n", words[0].trim());
						hill = new Hill(words[0].trim());
						thread2 = new Thread(hill);

					} else if (count == 3) {
						sub = word.substring(i);

						String[] words = sub.split("\\*+|\\*+\s|\s\\*+");
						// System.out.printf("Summit thread input: %s%n", words[0].trim());
						summit = new Summit(words[0].trim());
						thread3 = new Thread(summit);

					} else if (count == 4) {
						sub = word.substring(i);

						String[] words = sub.split("\\*+|\\*+\s|\s\\*+");
						// System.out.printf("Replace thread input: %s%n", words[0].trim());
						replace = new Replace(words[0].trim());
						thread4 = new Thread(replace);

					}
					totalAstCount += count;

					count = 0;
				}

				// System.out.println(count);
			}
			if (totalAstCount > 10) {
				totalAstCount = totalAstCount - 10;
			}
			// System.out.printf("Total asterisks: %d%n", totalAstCount);

			if (totalAstCount != 10) {

				System.out.printf("Invalid record: %s%n%n", word);
				totalAstCount = 0;
			} else {
				totalAstCount = 0;

				thread1.start();
				try {
					thread1.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				thread2.start();
				
				try {
					thread2.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				thread3.start();
				try {
					thread3.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				thread4.start();
				try {
					thread4.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println();
			}
		}
	}

	public static synchronized void hill(String s) {
		String[] sec1 = s.split("[0-9]|\\s+");
		// String[] sec2 = s.split("[a-z]+|\\s+");
		String[] sec2 = s.split("[a-z]+\\s+|[A-Z]+\\s+|\\s+");
		String word = sec1[0];
		if (word.length() % 2 == 0) {
			try {
				int[] wordInd = searchLettInd(word);
				int[] nums = convertToInt(sec2);
				int[][] wordMatrix = createMatrix2(wordInd);
				int[][] numMatrix = createMatrix(nums);
				// System.out.printf("%s%n", Arrays.toString(nums));
				 //printMatrix(wordMatrix);
				// System.out.println(wordMatrix.length);
				// printMatrix(numMatrix);
				int[][] x = multiMatrix(wordMatrix, numMatrix);
				// printMatrix(x);
				char[] lettArr = createAlphaArray(x);
				outPut = convertCharToString(lettArr);
				System.out.printf("%s%n", outPut);

			} catch (NumberFormatException e) {
				System.out.println("Input is invalid");
			}
		} else {
			System.out.println("Invalid word length");
		}
	}

	public static synchronized void replace(String s) {
		try {
			s = s.trim();
			String[] arr = s.split("\\s+");
			int[] nums = convertToInt2(arr);
			int sum = 0, num = 0;
			for (int i : nums) {
				sum += i;
			}
			if (sum < 0) {
				
				System.out.printf("Total sum of array values: %d%n"
						+ "Sum is negative, thread is finished", sum);
			} else {
				// System.out.println(sum);
				num = sum % 26;
				// System.out.println(num);
				for (int i = 0; i < nums.length; i++) {
					int j = num - nums[i];
					// nums[i] = num-nums[i];
					while (j < 0) {
						j = 26 + j;
					}
					if (j > 26) {
						j = j % 26;
					}
					nums[i] = j;
					// System.out.printf("%d ", nums[i]);
				}
				int temp1 = nums[0], temp2 = nums[1];
				for (int i = 2; i < nums.length; i++) {

					nums[i - 2] = nums[i];

				}
				nums[nums.length - 2] = temp1;
				nums[nums.length - 1] = temp2;

				char[] lett = convertToChar(nums);
				outPut = convertCharToString(lett);
				System.out.printf("%s", outPut);
			}
		} catch (ArrayIndexOutOfBoundsException e) {

			System.out.println("Negative numbers generated. Letter cannot be located");
		}
	}

	public static synchronized void substitute(String s) {
		try {
			s = s.toLowerCase();
			char lett = s.charAt(0);

			s = s.substring(1).trim();
			if (s.contains(" ")) {
				System.out.println("Invalid token");
			} else {

				String temp = "";
				int[] word = searchLettInd(s);
				int num = 0;
				for (int i = 0; i < alpha.length; i++) {
					if (lett == alpha[i]) {
						num = i;
						num = (i % 10) + 2;
					}
					
				}

				for (int i = 0; i < word.length; i++) {
					int j = word[i] - num, k = 0;
					if (j < 0) {
						k = num - word[i];
						k = 26 - k;
						temp += alpha[k];
					} else {
						temp += alpha[j];
					}
				}

				System.out.printf("%s%n", temp.toUpperCase());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Negative numbers generated, could not get index");
		}
	}

	public static synchronized void summit(String s) {
		String[] sec1 = s.split("[0-9]|\\s+");
		// String[] sec2 = s.split("[a-z]+|\\s+");
		String[] sec2 = s.split("[a-z]+\\s+|[A-Z]+\\s+|\\s+");
		String word = sec1[0].trim();

		if (word.length() % 3 == 0) {
			try {
				int[] wordInd = searchLettInd(word);
				int[] nums = convertToInt(sec2);
				int[][] wordMatrix = createMatrix3(wordInd);
				if (wordInd.length == 6) {
					wordMatrix = createMatrix4(wordInd);
				}
				int[][] numMatrix = createMatrix4(nums);
				// System.out.printf("%s%n", Arrays.toString(nums));
				// printMatrix(wordMatrix);
				// System.out.println(wordMatrix.length);
				 //printMatrix(numMatrix);
				// System.out.println(numMatrix.length);
				int[][] x = multiMatrix2(wordMatrix, numMatrix);

				 //printMatrix(x);
				char[] lettArr = createAlphaArray2(x);
				if (wordInd.length == 6) {
					lettArr = createAlphaArray3(x);
				}
				outPut = convertCharToString(lettArr);
				System.out.printf("%s%n", outPut);

			} catch (NumberFormatException e) {
				System.out.println("Input is invalid");
			}
		} else {
			System.out.println("Invalid word length");
		}
	}

	public static String getFileName(Scanner sc) {
		boolean validFile = false;
		String fileName = null;
		while (!validFile) {
			try {
				System.out.println("Enter the text file path:");
				fileName = sc.nextLine();
				Scanner in = new Scanner(new File(fileName));
				validFile = true;
			} catch (FileNotFoundException e) {
				System.out.println("File could not be found, try again");
				validFile = false;
			}

		}
		return fileName;
	}

	public static int[] searchLettInd(String s) {
		s = s.toLowerCase();
		int[] arr = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			for (int j = 0; j < alpha.length; j++) {
				if (s.charAt(i) == alpha[j]) {
					arr[i] = j;
				}
			}
		}
		return arr;
	}

	public static int[][] createMatrix(int[] arr) {
		int count = 0;
		int[][] a = new int[arr.length / 2][2];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (count == arr.length) {
					break;
				} else {

					a[i][j] = arr[count];

					count++;
				}
			}
		}
		return a;
	}

	// creates correct word 2xn matrix
	public static int[][] createMatrix2(int[] arr) {
		int count = 0;
		int[][] a = new int[arr.length / 2][2];
		for (int i = 0; i < a[0].length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (count == arr.length) {
					break;
				} else {

					a[j][i] = arr[count];

					count++;
				}
			}
		}
		return a;
	}

	// creates word 3xn matrix
	public static int[][] createMatrix3(int[] arr) {
		int count = 0;
		int[][] a = new int[arr.length / 3][3];
		for (int i = 0; i < a[0].length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (count == arr.length) {
					break;
				}
				a[j][i] = arr[count];
				count++;
			}
		}
		return a;
	}

	// creates int 3x3 matrix
	public static int[][] createMatrix4(int[] arr) {
		int count = 0;
		int[][] a = new int[arr.length / 3][3];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (count == arr.length) {
					break;
				}
				a[i][j] = arr[count];
				count++;
			}
		}
		return a;
	}

	public static int[] convertToInt(String[] s) {
		int[] nums = new int[s.length - 1];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Integer.parseInt(s[i + 1]);
		}
		return nums;
	}

	public static int[] convertToInt2(String[] s) {
		int[] nums = new int[s.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Integer.parseInt(s[i]);
		}
		return nums;
	}

	public static void printMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.printf("%d ", m[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	public static int[][] multiMatrix(int[][] x, int[][] y) {
		int i = 0, j = 0;
		int[][] arr = new int[x.length][y[0].length];
		while (i < x.length - 1 && j < x[0].length) {
			int[] a = new int[2];

			a[0] = x[i][j];
			a[1] = x[i + 1][j];
			arr[i][j] += (a[0] * y[0][0] + a[1] * y[0][1]);
			arr[i + 1][j] += (a[0] * y[1][0] + a[1] * y[1][1]);
			arr[i][j] = arr[i][j] % 26;
			arr[i + 1][j] = arr[i + 1][j] % 26;

			if (i + 2 == x.length - 1 && j < x[0].length - 1) {
				a[0] = x[i + 2][j];
				a[1] = x[i - i][j + 1];
				arr[i + 2][j] += (a[0] * y[0][0] + a[1] * y[0][1])%26;
				arr[i - i][j + 1] += (a[0] * y[1][0] + a[1] * y[1][1])%26;
				
			}
			
			i += 2;

			if (i >= x.length) {
				i = 0;
				j++;
			} else if (i == x.length - 1) {
				i = 1;
				j++;
			}

		}

		return arr;
	}

	public static int[][] multiMatrix2(int[][] x, int[][] y) {
		int i = -1, j = 0;

		int[][] arr = new int[x.length][y[0].length];
		int[] a = new int[3];
		while (i < x.length - 1 && j < x[0].length) {
			if (j == 0) {
				i = 0;
			}
			
			if (x.length <= 2) {

				while (i < x.length && j == 0) {
					a[0] = x[i][j];
					a[1] = x[i][j + 1];
					a[2] = x[i][j + 2];
					arr[i][j] += (a[0] * y[0][0] + a[1] * y[0][1] + a[2] * y[0][2]) % 26;
					arr[i][j + 1] += (a[0] * y[1][0] + a[1] * y[1][1] + a[2] * y[1][2]) % 26;
					arr[i][j + 2] += (a[0] * y[2][0] + a[1] * y[2][1] + a[2] * y[2][2]) % 26;
					i++;
				}
			}

			else {
				a[0] = x[i][j];
				a[1] = x[i + 1][j];
				a[2] = x[i + 2][j];
				arr[i][j] += (a[0] * y[0][0] + a[1] * y[0][1] + a[2] * y[0][2]) % 26;
				arr[i + 1][j] += (a[0] * y[1][0] + a[1] * y[1][1] + a[2] * y[1][2]) % 26;
				arr[i + 2][j] += (a[0] * y[2][0] + a[1] * y[2][1] + a[2] * y[2][2]) % 26;
			}
			
			i += 3;
			if (i >= x.length) {
				i = 0;
				j++;
			}

		}
		return arr;
	}

	public static char[] createAlphaArray(int[][] arr) {
		int count = 0;
		char[] a = new char[arr.length * 2];

		for (int i = 0; i < arr[0].length; i++) {
			for (int j = 0; j < arr.length; j++) {
				for (int k = 0; k < alpha.length; k++) {
					if (count == a.length) {
						break;
					}
					if (arr[j][i] == k) {
						a[count] = alpha[k];
						count++;
					}
				}
			}
		}
		return a;
	}

	public static char[] createAlphaArray2(int[][] arr) {
		int count = 0;
		char[] a = new char[arr.length * 3];

		for (int i = 0; i < arr[0].length; i++) {
			for (int j = 0; j < arr.length; j++) {
				for (int k = 0; k < alpha.length; k++) {
					if (count == a.length) {
						break;
					}
					if (arr[j][i] == k) {
						a[count] = alpha[k];
						count++;
					}
				}
			}
		}
		return a;
	}

	public static char[] createAlphaArray3(int[][] arr) {
		int count = 0;
		char[] a = new char[arr.length * 3];

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				for (int k = 0; k < alpha.length; k++) {
					if (count == a.length) {
						break;
					}
					if (arr[i][j] == k) {
						a[count] = alpha[k];
						count++;
					}
				}
			}
		}
		return a;
	}

	public static char[] convertToChar(int[] arr) {
		char[] a = new char[arr.length];
		for (int i = 0; i < a.length; i++) {
			a[i] = alpha[arr[i]];
		}
		return a;
	}
	public static String convertCharToString(char[] arr) {
		String temp = "";
		for (int i = 0; i < arr.length; i++) {
			temp += arr[i];
		}
		temp = temp.toUpperCase();
		return temp;
	}

	static class Substitute implements Runnable {
		private String word;

		public Substitute(String s) {
			word = s;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.printf("Substitute thread input: %s%n", word);
			System.out.print("Substitute thread output: ");
			substitute(word);
		}

	}

	static class Hill implements Runnable {
		private String word;

		public Hill(String s) {
			word = s;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.printf("Hill thread input: %s%n", word);
			System.out.print("Hill thread output: ");
			hill(word);

		}

	}

	static class Summit implements Runnable {
		private String word;

		public Summit(String s) {
			word = s;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.printf("Summit thread input: %s%n", word);
			System.out.print("Summit thread output: ");
			summit(word);
		}

	}

	static class Replace implements Runnable {
		private String word;

		public Replace(String s) {
			word = s;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.printf("Replace thread input: %s%n", word);
			System.out.print("Replace thread output: ");
			replace(word);
		}

	}

}

