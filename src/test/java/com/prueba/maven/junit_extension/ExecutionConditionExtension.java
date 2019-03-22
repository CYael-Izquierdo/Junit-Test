package com.prueba.maven.junit_extension;

import java.util.Collection;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class ExecutionConditionExtension implements ExecutionCondition {
	private static int countWorking, countBroken, countNotImplementedYet, countOutOfScope;
	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionConditionExtension.class);

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

		Collection<String> tags = context.getTags();
		String tagName = null;
		for (Object tag : tags) {
			tagName = tag.toString();
		}

		switch (tagName) {
		case "Working":
			countWorking += 1;
			break;
		case "Broken":
			countBroken += 1;
			break;
		case "NotImplementedYet":
			countNotImplementedYet += 1;
			break;
		case "OutOfScope":
			countOutOfScope += 1;
			break;
		}

		if (!tagName.contains("working")) {

			return ConditionEvaluationResult.disabled("Test not tagged as working, so skipping");
		}
		return ConditionEvaluationResult.enabled("Test tagged as working");
	}

}

