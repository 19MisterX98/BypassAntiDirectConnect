package misterx.bypassADC;

import misterx.bypassADC.modules.DirectConnectSpoof;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class Addon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    @Override
    public void onInitialize() {
        LOG.info("Initializing Bypass-AntiDirectConnect");

        // Modules
        Modules.get().add(new DirectConnectSpoof());
    }

    @Override
    public String getPackage() {
        return "misterx.bypassADC";
    }
}
