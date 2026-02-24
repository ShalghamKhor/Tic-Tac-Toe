package tic.tac.toe;

import java.io.InputStream;
import java.util.Properties;

public final class AppInfo {
    private static final String DEFAULT_VERSION = "dev";
    private static String cachedVersion;

    private AppInfo() {
    }

    public static String getVersion() {
        if (cachedVersion != null) {
            return cachedVersion;
        }

        try (InputStream stream = AppInfo.class.getResourceAsStream("/version.properties")) {
            if (stream != null) {
                Properties properties = new Properties();
                properties.load(stream);
                String version = properties.getProperty("app.version");
                if (version != null && !version.isBlank()) {
                    cachedVersion = version.trim();
                    return cachedVersion;
                }
            }
        } catch (Exception ignored) {
            // fall through to manifest/default values
        }

        String manifestVersion = AppInfo.class.getPackage().getImplementationVersion();
        if (manifestVersion != null && !manifestVersion.isBlank()) {
            cachedVersion = manifestVersion.trim();
            return cachedVersion;
        }

        cachedVersion = DEFAULT_VERSION;
        return cachedVersion;
    }
}
