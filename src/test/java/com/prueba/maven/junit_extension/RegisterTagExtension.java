package com.prueba.maven.junit_extension;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.engine.descriptor.ClassExtensionContext;

public class RegisterTagExtension implements AfterAllCallback,  ExecutionCondition{

	private HashMap<String, ArrayList<TestInfo>> taggedTestCounters = new HashMap<String, ArrayList<TestInfo>>();
	private ArrayList<TestInfo> wrongTaggedTests = new ArrayList<TestInfo>();
	private ArrayList<TestInfo> untaggedTests = new ArrayList<TestInfo>();

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
		if(context.getClass().getName() == ClassExtensionContext.class.getName()){
			return ConditionEvaluationResult.enabled("It's a test class");
		}
		System.out.println(context.getClass().getName());
		System.out.println(context.getDisplayName() + "  " + context.getTags().toString());
		TestInfo test = new TestInfo(context);
		return registerTest(test);
	}

	@Override
	public void afterAll(ExtensionContext context) throws Exception {
		showReport();	
	}

	// UTILS

	private ConditionEvaluationResult registerTest(TestInfo test) {
		if (test.getTags().size() == 0) {
			addUntaggedTest(test);
			return ConditionEvaluationResult.disabled("Skip: Test no tagged with custom tag");
		} else if (test.getTags().size() >= 2) {
			addWrongTaggedTest(test);
			return ConditionEvaluationResult.disabled("Skip: Test tagged with multiple custom tag");
		} else {
			if(!test.getTestContext().getParent().get().getTestMethod().isPresent()){
				addTaggedTest(test);
				if (TagsTo.skip().contains(test.getTags().get(0))) {
					return ConditionEvaluationResult.disabled("Skip: Test tagged with a skip custom tag");
				}
				return ConditionEvaluationResult.enabled("Test Tagged with a run custom tag");
			} else {
				return ConditionEvaluationResult.enabled("Test Tagged with a run custom tag");
			}
		}
	}

	private void addUntaggedTest(TestInfo test) {
		untaggedTests.add(test);
	}

	private void addWrongTaggedTest(TestInfo test) {
		wrongTaggedTests.add(test);
	}
	
	private void addTaggedTest(TestInfo test) {
		ArrayList<TestInfo> testTag = taggedTestCounters.get(test.getTags().get(0));
		if (testTag == null) {
			ArrayList<TestInfo> auxArray = new ArrayList<TestInfo>();
			auxArray.add(test);
			taggedTestCounters.put(test.getTags().get(0), auxArray);
		} else {
			testTag.add(test);
			taggedTestCounters.put(test.getTags().get(0), testTag);
		}
	}
	
	private void showReport(){
		
		System.out.println("================================================================================================================");
		
		String testsExecutedReport = "Tests Executed: \n";
		for(String tag: TagsTo.run()){
			testsExecutedReport = testsExecutedReport + "\t" + tag + ":\n";
			if(!taggedTestCounters.containsKey(tag)){
				break;
			}
			for(TestInfo test: taggedTestCounters.get(tag)){

				testsExecutedReport = testsExecutedReport + "\t\t- " + test.getName() + "\n";
			}
		}
		System.out.println(testsExecutedReport);
		
		String testsSkippedReport = "Tests Skipped: \n";
		for(String tag: TagsTo.skip()){
			testsSkippedReport = testsSkippedReport + "\t" + tag + ":\n";
			if(!taggedTestCounters.containsKey(tag)){
				break;
			}
			for(TestInfo test: taggedTestCounters.get(tag)){
				testsSkippedReport = testsSkippedReport + "\t\t- " + test.getName() + "\n";
			}
		}
		System.out.println(testsSkippedReport);		
		
		String wrongTaggedTestsReport = "Wrongly tagged tests: \n";
		for(TestInfo test: wrongTaggedTests){
			String wTTReportHelp = "\t-" + test.getName() + ":\n";
			for(String tag: test.getTags()){
				wTTReportHelp = wTTReportHelp + "\t\t-" + tag + "\n";
			}
			wrongTaggedTestsReport = wrongTaggedTestsReport + wTTReportHelp;
		}
		System.out.println(wrongTaggedTestsReport);
		
		String untaggedTestsReport = "Untagged tests: \n";
		for(TestInfo test: untaggedTests){
			untaggedTestsReport = untaggedTestsReport + "\t-" + test.getName();
		}
		System.out.println(untaggedTestsReport);
		
		System.out.println("================================================================================================================");
		
	}
	
}
