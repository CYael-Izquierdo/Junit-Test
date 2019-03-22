package com.prueba.maven.junit_extension;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.engine.descriptor.ClassExtensionContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.hierarchical.Node.SkipResult;
import org.mockito.internal.junit.TestFinishedEvent;
import org.springframework.test.context.TestContextBootstrapper;


public class RegisterTagExtension implements AfterAllCallback,  ExecutionCondition{

	private HashMap<String, ArrayList<TestInfo>> taggedTestCounters = new HashMap<String, ArrayList<TestInfo>>();
	private ArrayList<TestInfo> WrongTaggedTests = new ArrayList<TestInfo>();
	private ArrayList<TestInfo> UntaggedTests = new ArrayList<TestInfo>();

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
			addTaggedTest(test);
			if (TagsTo.skip().contains(test.getTags().get(0))) {
				return ConditionEvaluationResult.disabled("Skip: Test tagged with a skip custom tag");
			}
			return ConditionEvaluationResult.enabled("Test Tagged with a run custom tag");
		}
	}

	private void addUntaggedTest(TestInfo test) {
		UntaggedTests.add(test);
	}

	private void addWrongTaggedTest(TestInfo test) {
		WrongTaggedTests.add(test);
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
		String report;
		
		String testsExecutedReport = "Tests Executed: \n";
		for(String tag: TagsTo.run()){
			testsExecutedReport.concat("\t" + tag + "\n");
			for(TestInfo test: taggedTestCounters.get(tag)){
				testsExecutedReport.concat("\t\t" + test.getName() + "\n");
			}
		}
		
	}
	
}
