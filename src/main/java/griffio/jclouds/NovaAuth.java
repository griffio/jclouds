package griffio.jclouds;

import com.google.common.io.Closeables;
import org.jclouds.ContextBuilder;
import org.jclouds.openstack.keystone.v2_0.config.CredentialTypes;
import org.jclouds.openstack.keystone.v2_0.config.KeystoneProperties;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class NovaAuth implements Closeable {

  private final NovaApi nova;

  private NovaAuth(String identity, String credential, String provider, Properties overrides) {
    nova = ContextBuilder
        .newBuilder(provider)
        .credentials(identity, credential)
        .overrides(overrides)
        .buildApi(NovaApi.class);
  }

  public Collection<String> availableRegions() {
    return nova.getConfiguredRegions();
  }

  public NovaServer server(String region) {
    return new NovaServer(nova.getServerApi(region), nova.getImageApi(region), nova.getFlavorApi(region));
  }

  public void close() throws IOException {
    Closeables.close(nova, true);
  }

  public static NovaAuth UseAccessKey(String identity, String accessKey, String provider) {
    return new NovaAuth(identity, accessKey, provider, new Properties());
  }

  public static NovaAuth UsePassword(String identity, String password, String provider) {
    Properties overrides = new Properties();
    overrides.put(KeystoneProperties.CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
    return new NovaAuth(identity, password, provider, overrides);
  }

}