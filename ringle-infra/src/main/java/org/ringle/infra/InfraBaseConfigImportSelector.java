package org.ringle.infra;

import java.util.Arrays;
import java.util.Map;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

class InfraBaseConfigImportSelector implements DeferredImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata metadata) {
		return Arrays.stream(getValues(metadata))
			.map(group -> group.getConfigClass().getName())
			.toArray(String[]::new);
	}

	private InfraBaseConfigGroup[] getValues(AnnotationMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableInfraBaseConfig.class.getName());
		if (attributes == null) {
			return new InfraBaseConfigGroup[0];
		}
		return (InfraBaseConfigGroup[])attributes.getOrDefault("value", new InfraBaseConfigGroup[0]);
	}
}
