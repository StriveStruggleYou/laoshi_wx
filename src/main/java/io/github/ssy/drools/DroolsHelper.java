package io.github.ssy.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class DroolsHelper {

  KieServices ks = KieServices.Factory.get();

  // From the kie services, a container is created from the classpath
  KieContainer kc = ks.getKieClasspathContainer();


  public KieSession getKieSession() {
    KieSession ksession = kc.newKieSession("BindMobile");
    return ksession;
  }




}
