package assignments;

import java.util.Arrays;
import java.util.*;
public class Methods {
	static char[] alpha = {'a','b','c','d','e','f','g','h','i','j','k','l','m',
			'n','o','p','q','r','s','t','u','v','w',
			'x','y','z'};
	public static List<Integer[]>list = new ArrayList<Integer[]>();
	public static void main(String[] args) {
		//hill("BGCIMOQN 9 2 1 15");
		int[] arr = {1,2,3,4,5,6,7,8};
		//addToList(arr);
		String[] ingredients = {"Mustard","Chicken breasts", "Apple cider vinegar", "Paprika", "Garlic", "Salt"};
		ArrayList<String>ing = new ArrayList<>(), recipe = new ArrayList<>();
		ing.add("paprika");
		ing.add("ranch");
		recipe = ing;
		System.out.println(recipe);
		
	}
	public static void hill(String s) {
		String[] sec1 = s.split("[0-9]|\\s+");
		String[] sec2 = s.split("[a-z]+|\\s+");
		int[] arr = new int[sec1[0].length()];
		for(int i = 0;i<alpha.length;i++) {
			for(int j = 0;j<sec1[0].length();j++) {
				if(sec1[0].charAt(j)==alpha[i]) {
					arr[j] = i;
				}
			}
		}
		System.out.printf("%s", Arrays.toString(arr));
	}
	public static void replace(String s) {
		
	}
	public static void substitute(String s) {
		
	}
	public static void summit(String s) {
		
	}
	public static int[] searchLettInd(String s) {
		int[] arr = new int[s.length()];
		for(int i = 0;i<s.length();i++) {
			for(int j = 0;j<alpha.length;j++) {
				if(s.charAt(i)==alpha[j]) {
					arr[i] = j;
				}
			}
		}
		return arr;
	}
	public static void addToList(int[] arr) {
		Integer[] a = new Integer[2];
		for(int i = 0;i<arr.length;i+=2) {
			a[0] = arr[i];
			a[1] = arr[i+1];
			list.add(a);
		}
		System.out.println(list.toString());
	}
}
