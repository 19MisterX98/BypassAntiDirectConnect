package misterx.bypassADC.mixin;

import meteordevelopment.meteorclient.systems.modules.Modules;
import misterx.bypassADC.mixininterface.IServerInfo;
import misterx.bypassADC.modules.DirectConnectSpoof;
import misterx.bypassADC.screens.DirectConnectSpoofScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.UnknownHostException;

@Mixin(ConnectScreen.class)
public class ConnectScreenMixin {
    @Inject(method = "connect(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;Z)V", at = @At("HEAD"), cancellable = true)
    private static void onConnect(Screen screen, MinecraftClient client, ServerAddress address, ServerInfo info, boolean quickPlay, CallbackInfo ci) {
        DirectConnectSpoof module = Modules.get().get(DirectConnectSpoof.class);
        if (!module.isActive()) return;

        long lastPingedTime = ((IServerInfo) info).getLastPing();
        long timeSinceLastPing = System.currentTimeMillis() - lastPingedTime;

        if ((lastPingedTime == 0 && module.onlyPingOnce.get()) || timeSinceLastPing > module.slpValidityDuration.get()*1000) {
            ci.cancel(); // cancel connect attempt and ping the server first
            //ping server
            new Thread(() -> {
                MultiplayerServerListPinger slp = new MultiplayerServerListPinger();
                try {
                    slp.add(info, () -> {});
                } catch (UnknownHostException ignored) {}
            }).start();

            //show info screen which will connect after some time
            int waitingTime = module.slpDelay.get();
            Runnable connectToServer = () -> ConnectScreen.connect(screen, client, address, info, quickPlay);
            client.setScreen(new DirectConnectSpoofScreen(waitingTime, connectToServer));
        }
    }
}
