package griffio;

import griffio.jclouds.NovaAuth;
import griffio.jclouds.NovaServer;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;

import java.util.List;

public class Main {

  static ServerCreated serverCreated;

  public static void main(String[] args) {

    String accessKey = System.getProperty("nova-key");
    String identity = System.getProperty("nova-identity");
    NovaAuth nova = NovaAuth.UseAccessKey(identity, accessKey, "rackspace-cloudservers-us");
    NovaServer iad = nova.server("IAD");

    List<Image> images = iad.images();

    for (Image image : images) {
      System.out.println("image = " + image);
    }

    List<Flavor> flavors = iad.flavors();
    for (Flavor flavor : flavors) {
      System.out.println("flavor = " + flavor);
    }
    // PVHVM Image -> Flavor must match ( or 400 bad request)
    serverCreated = iad.create("Test Server", iad.imageById("6b025595-a83b-436b-b37d-d5b212b5ddfc"), iad.flavorById("general1-1"));
    System.out.println("serverCreated = " + serverCreated);

  }

}
