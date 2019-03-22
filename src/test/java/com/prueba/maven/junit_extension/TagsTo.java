package com.prueba.maven.junit_extension;

import java.util.ArrayList;

public abstract class TagsTo {

    private static String[] run = new String[] {"Working"};
    private static String[] skip = new String[] {"NotImplementedYet", "Broken"};

    public static ArrayList<String> run (){
    	ArrayList<String> runArray = stringArrayToArrayList(run);
    	return runArray;
    }
    
    public static ArrayList<String> skip (){
    	ArrayList<String> skipArray = stringArrayToArrayList(skip);
    	return skipArray;
    }
    
    private static ArrayList<String> stringArrayToArrayList(String[] array) {
		ArrayList<String> arrayList = new ArrayList<String>();
    	for (String string : array) {
			arrayList.add(string);
		}
    	return arrayList;
    }
}
