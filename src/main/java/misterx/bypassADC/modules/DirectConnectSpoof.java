package misterx.bypassADC.modules;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public class DirectConnectSpoof extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Integer> slpDelay = sgGeneral.add(new IntSetting.Builder()
        .name("delay-after-ping")
        .description("How long to wait after sending server list ping in milliseconds")
        .defaultValue(800)
        .range(200, 3000)
        .sliderRange(200,3000)
        .build()
    );

    public final Setting<Boolean> onlyPingOnce = sgGeneral.add(new BoolSetting.Builder()
        .name("ping-only-once")
        .description("Whether or not the server list ping expires")
        .defaultValue(false)
        .build()
    );

    public final Setting<Integer> slpValidityDuration = sgGeneral.add(new IntSetting.Builder()
        .name("time-until-expiration")
        .description("How long does the server list ping stay valid for in seconds")
        .defaultValue(60)
        .range(20, 100000)
        .noSlider()
        .visible(() -> !onlyPingOnce.get())
        .build()
    );

    public DirectConnectSpoof() {
        super(Categories.Misc, "direct-connect-spoof", "Bypass the AntiDirectConnect addon on servers.");
    }
}
