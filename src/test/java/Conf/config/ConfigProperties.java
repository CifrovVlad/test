package Conf.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProperties {
Config config = getConfigg();

public static Config getConfigg() {
    return ConfigFactory.systemProperties().hasPath("testProfile") ?
            ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
            : ConfigFactory.load("application.conf");
}

String URL = getConfigg().getString("url");
String NAME  = getConfigg().getString("UserData.Users.name");
String PASSWORD  = getConfigg().getString("UserData.Users.password");
int i = 0;
}
