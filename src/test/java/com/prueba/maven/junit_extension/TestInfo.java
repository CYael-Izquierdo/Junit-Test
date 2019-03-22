package com.prueba.maven.junit_extension;

import java.util.ArrayList;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtensionContext;

public class TestInfo {

	private String name;
	private ArrayList<String> tags;
	private ExtensionContext testContext;

	public TestInfo(ExtensionContext testContext) {
		super();
		this.name = testContext.getDisplayName();
		this.tags = getTestCustomTags(testContext.getTags());
		this.testContext = testContext;
	}

	public ExtensionContext getTestContext(){
		return testContext;
	}
	
	public String getName() {
		return name;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void addTag(String tag) {
		this.tags.add(tag);
	}

	private ArrayList<String> getTestCustomTags(Set<String> testTags) {
		ArrayList<String> customsTags = TagsTo.run();
		customsTags.addAll(TagsTo.skip());
		
		ArrayList<String> testCustomTags = new ArrayList<String>();
		
		for (String tag : testTags) {
			if (customsTags.contains(tag)) {
				testCustomTags.add(tag);
			}
		}
		
		return testCustomTags;		
	}

}
