package org.extension.oracle.oci.internal;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public class OCIErrorProvider implements ErrorTypeProvider {

	@SuppressWarnings("rawtypes")
	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		HashSet<ErrorTypeDefinition> errors = new HashSet<ErrorTypeDefinition>();
        errors.add(OCIErrorType.BUCKET_NOT_FOUND);
        errors.add(OCIErrorType.NOT_AUTHENTICATED);
        errors.add(OCIErrorType.OCI_ERROR);
        return errors;
	}

}
