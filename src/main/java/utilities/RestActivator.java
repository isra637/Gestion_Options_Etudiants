package utilities;

import resssources.EtudiantRessource;
import resssources.Optionressource;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RestActivator extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(Optionressource.class);
        classes.add(EtudiantRessource.class);
        return classes;
    }
}

