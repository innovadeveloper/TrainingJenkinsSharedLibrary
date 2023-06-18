package sharedlibrary

import io.kubernetes.client.ApiClient
import io.kubernetes.client.ApiException
import io.kubernetes.client.Configuration
import io.kubernetes.client.apis.CoreV1Api
import io.kubernetes.client.models.V1Pod
import io.kubernetes.client.models.V1PodList
import io.kubernetes.client.util.Config
import io.kubernetes.client.util.KubeConfig
import io.kubernetes.client.util.authenticators.GCPAuthenticator

public class Main {
  public static void main(String[] args) throws Exception {
    runElements()


    KubeConfig.registerAuthenticator(new GCPAuthenticator());
    ApiClient apiClient = Config.defaultClient();
//    apiClient.setUsername("admin");
    apiClient.setVerifyingSsl(false)
    apiClient.setBasePath("https://192.168.64.3:16443")
    apiClient.setApiKey("cnpFNFVlUTRESjNleTg4M0ZwYlFOSlQwczUrSVJXK1ZBejl6NWRhZXRaST0K")
    Configuration.setDefaultApiClient(apiClient);

    CoreV1Api api = new CoreV1Api();
    try {
      V1PodList list = api.listPodForAllNamespaces(null, null, null, null, null, null, null,
              null, null);
      for (V1Pod item : list.getItems()) {
        System.out.println(item.getMetadata().getName());
      }
    } catch (ApiException e) {
      System.out.println(e);
      System.out.println(e.getCode());
      System.out.println(e.getResponseBody());
    }
  }

  private static void runElements(){
    def square = {it * it}
    def foundValud = square(4)
    ["kenny", "lesly", "ines"].each{ name ->
      println("the name is $name")
    }
  }
}

//static void main(String[] args) {
//  println "Hello world!"
//}