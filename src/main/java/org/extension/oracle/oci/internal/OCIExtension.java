package org.extension.oracle.oci.internal;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "oci")
@Extension(name = "OCI")
@ErrorTypes(OCIErrorType.class)
@Configurations(OCIConfiguration.class)
public class OCIExtension {

}
