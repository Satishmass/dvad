package org.extension.oracle.oci.internal;


import org.mule.runtime.extension.api.annotation.Operations;

import org.mule.runtime.extension.api.annotation.param.Parameter;

import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Example;

import org.springframework.core.annotation.Order;


/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(OCIOperations.class)
public class OCIConfiguration {
	

  
  @Parameter
  @DisplayName(value = "User ID")
  @Order(value = 1)
  @Example(value = "ocid1.user.oc1..bbbbb36d62b2hesdfdsdw2hbhgy5tb6iybprvlghadziqdsdfdd7q")
  private String userId;
  
  @Parameter
  @DisplayName(value = "Tenant ID")
  @Order(value = 2)
  @Example(value = "ocid1.tenancy.oc1..bbbbbbblcra7vr7rjm9ybvn3leer76dfdf4vdcpl4qv37wo2zp0fpcrxxyz")
  private String tenantId;
  
  
  @Parameter
  @DisplayName(value = "FingerPrint")
  @Order(value = 3)
  @Example(value = "b1:a9:36:5e:77:25:80:79:3z:ea:82:52:52:39:af:8a")
  private String fingerprint;
  
  
  @Parameter
  @DisplayName(value = "Private Key Path")
  @Order(value = 4)
  @Example(value = "oci_api_key.pem")
  private String privateKeyPath;
  

public String getTenantId() {
	return tenantId;
}

public String getUserId() {
	return userId;
}

public String getFingerprint() {
	return fingerprint;
}

public String getPrivateKeyPath() {
	return privateKeyPath;
}

@Override
public String toString() {
	return "OCIConfiguration [tenantId=" + tenantId + ", userId=" + userId + ", fingerprint=" + fingerprint
			+ ", privateKeyPath=" + privateKeyPath + "]";
}


  
}
