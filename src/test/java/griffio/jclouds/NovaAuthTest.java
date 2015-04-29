package griffio.jclouds;

import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class NovaAuthTest {

  private String identity;
  private String accessKey;
  private String password;
  private NovaAuth auth1;
  private NovaAuth auth2;

  @Before
  public void setUp() throws Exception {
    accessKey = System.getProperty("nova-key");
    identity = System.getProperty("nova-identity");
    password = System.getProperty("nova-password");
    auth1 = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    auth2 = NovaAuth.UsePassword(identity, password, "rackspace-cloudservers-us");
  }

  @After
  public void tearDown() throws Exception {
    auth1.close();
    auth2.close();
  }

  @Test
  public void accessKey() throws Exception {
    auth1 = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    Collection<String> regions = auth1.availableRegions();
    Assert.assertTrue(regions.contains("IAD"));
  }

  @Test
  public void password() throws Exception {
    auth2 = NovaAuth.UsePassword(identity, password, "rackspace-cloudservers-us");
    Collection<String> regions = auth2.availableRegions();
    Assert.assertTrue(regions.contains("SYD"));
  }

  @Test
  public void flavours() {
    auth1 = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    NovaServer serverIAD = auth1.server("IAD");
    Flavor flavor = serverIAD.flavorById("2");
    Assert.assertNotNull(flavor);
  }

  @Test
  public void images() {
    auth1 = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    NovaServer serverIAD = auth2.server("IAD");
    Image image = serverIAD.imageById("1cc2475a-785f-4aa8-8ff0-ab37d5e3602a");
    Assert.assertNotNull(image);
  }

}