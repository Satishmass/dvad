package org.extension.oracle.oci.internal;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

public enum OCIErrorType implements ErrorTypeDefinition<OCIErrorType> {
	BUCKET_NOT_FOUND,
	NOT_AUTHENTICATED,
	OCI_ERROR
	
}
