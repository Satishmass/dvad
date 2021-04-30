package org.extension.oracle.oci.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;

import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import com.oracle.bmc.model.BmcException;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.GetNamespaceRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetNamespaceResponse;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;

import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Config;



/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class OCIOperations {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OCIOperations.class);
	
	
  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @MediaType(value = ANY, strict = false)
  @Throws(OCIErrorProvider.class)
  public String createObject(@Config OCIConfiguration configuration, String bucket, String contents, String objectName, String region) {
	  ObjectStorage client = null;
	  URL u = null;
	  try {
		  LOGGER.info("Create Object Invoked [" + configuration);
		  LOGGER.info("[Bucket: " + bucket + ", Object Name: " + objectName + ", Contents: " + contents);
		  u =  this.getClass().getClassLoader().getResource(configuration.getPrivateKeyPath());
		  if (u == null) {
			  throw new Exception("Could not find private key: " + configuration.getPrivateKeyPath());
		  }
	      Supplier<InputStream> privateKeySupplier 
	      = new SimplePrivateKeySupplier(u.getPath());
	      AuthenticationDetailsProvider provider 
	      = SimpleAuthenticationDetailsProvider.builder()
	          .tenantId(configuration.getTenantId())
	          .userId(configuration.getUserId())
	          .fingerprint(configuration.getFingerprint())
	          .privateKeySupplier(privateKeySupplier)
	          .build();
	      LOGGER.info("Authenticated: " + provider);
	      client = new ObjectStorageClient(provider);
	      client.setRegion(this.getRegion(region));
	      GetNamespaceResponse namespaceResponse =
	              client.getNamespace(GetNamespaceRequest.builder().build());
	      String namespaceName = namespaceResponse.getValue();

	      LOGGER.info("Namespace: " + namespaceName);

	      PutObjectRequest request =
	              PutObjectRequest.builder()
	                      .bucketName(bucket)
	                      .namespaceName(namespaceName)
	                      .objectName(objectName)
	                      .putObjectBody(new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8)))
	                      .build();
	      PutObjectResponse res =  client.putObject(request);
	      return res.getOpcRequestId();
	} catch(Exception e) {
		LOGGER.info("In Message");
	      if (e instanceof BmcException) {
	    	  BmcException bmc = (BmcException)e;
	          String body = bmc.getMessage();
	          if (body != null && body.contains("BucketNotFound")) {
	            throw new ModuleException(OCIErrorType.BUCKET_NOT_FOUND, e);
	          } else if (body != null && body.contains("NotAuthenticated")) {
	        	  throw new ModuleException(OCIErrorType.NOT_AUTHENTICATED, e);
	          } else {
	        	  throw new ModuleException(OCIErrorType.OCI_ERROR, e);
	          }
	        } else {
	        	throw new ModuleException(OCIErrorType.OCI_ERROR, e);
	        }
	        
		
		
	}finally {
		if (client != null) {
			try {
				client.close();
			} catch (Exception e) {
				LOGGER.debug(e.getMessage());
			}
		}
	}
	  
  }
  
 
  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @MediaType(value = ANY, strict = false)
  public String createBucket(@Config OCIConfiguration configuration){
    //return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId() + "]";
	  return "";
  }
  
  private Region getRegion(String r) {
		Region ociRegion = null;
		switch (r) {
		case "us-ashburn-1":
			ociRegion = Region.US_ASHBURN_1;
			break;
		case "us-phoenix-1":
			ociRegion = Region.US_PHOENIX_1;
			break;
		case "uk-london-1":
			ociRegion = Region.UK_LONDON_1;
			break;
		case "ca-toronto-1":
			ociRegion = Region.CA_TORONTO_1;
			break;
		default:
			ociRegion = Region.US_ASHBURN_1;
			break;
		}
		LOGGER.info("Region: "+ ociRegion.toString());
		return ociRegion;
	}

 
}
