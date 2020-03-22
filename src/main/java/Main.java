import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


public class Main {
    public static void main(String[] args) throws  Exception {
        AbstractApplicationContext appContext = new AnnotationConfigApplicationContext(Config.class);
        SopsWrapper sops = appContext.getBean(SopsWrapper.class);

        sops.encryptToFile("/Users/iob/projects/no-sops-for-you/text.yaml", "/Users/iob/projects/no-sops-for-you/text.enc.yaml");
        sops.decryptToFile("/Users/iob/projects/no-sops-for-you/text.enc.yaml","/Users/iob/projects/no-sops-for-you/text.dec.yaml");
    }
}
