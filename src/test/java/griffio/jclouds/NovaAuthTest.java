package griffio.jclouds;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class NovaAuthTest {

  private String identity;
  private String accessKey;
  private String password;
  private NovaAuth novaAuth;

  @Before
  public void setUp() throws Exception {
    accessKey = System.getProperty("nova-key");
    identity = System.getProperty("nova-identity");
    password = System.getProperty("nova-password");
  }

  @After
  public void tearDown() throws Exception {
    novaAuth.close();
  }

  @Test
  public void accessKey() throws Exception {
    novaAuth = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    Collection<String> regions = novaAuth.availableRegions();
    Assert.assertTrue(regions.contains("IAD"));
  }

  @Test
  public void password() throws Exception {
    novaAuth = NovaAuth.UsePassword(identity, password, "rackspace-cloudservers-us");
    Collection<String> regions = novaAuth.availableRegions();
    Assert.assertTrue(regions.contains("SYD"));
  }

}