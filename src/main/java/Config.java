import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {

    @Value("${sops.bin}")
    private String sopsBin;

    @Value("${sops.gpg.fingerPrint}")
    private String fingerPrint;

    @Bean
    public SopsWrapper sops(){
        return new SopsWrapper(sopsBin,fingerPrint);
    }
}
