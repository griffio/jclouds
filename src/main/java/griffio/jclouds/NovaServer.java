package griffio.jclouds;

import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import java.util.List;

public class NovaServer {

  private final ServerApi serverApi;
  private final ImageApi imageApi;
  private final FlavorApi flavorApi;

  NovaServer(ServerApi serverApi, ImageApi imageApi, FlavorApi flavorApi) {
    this.serverApi = serverApi;
    this.imageApi = imageApi;
    this.flavorApi = flavorApi;
  }

  public Image imageById(String id) {
    return imageApi.get(id);
  }

  public List<Image> images() {
    return imageApi.listInDetail().concat().toList();
  }

  public Flavor flavorById(String id) {
    return flavorApi.get(id);
  }

  public List<Flavor> flavors() {
    return flavorApi.listInDetail().concat().toList();

  }

  public ServerCreated create(String description, Image image, Flavor flavor) {
    return serverApi.create(description, image.getId(), flavor.getId());
  }

}
