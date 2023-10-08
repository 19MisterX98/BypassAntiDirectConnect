package misterx.bypassADC.mixin;

import misterx.bypassADC.mixininterface.IServerInfo;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerServerListPinger.class)
public class MultiplayerServerListPingerMixin {

    @Inject(method = "add", at = @At("HEAD"))
    public void onAdd(ServerInfo entry, Runnable saver, CallbackInfo ci) {
        IServerInfo serverInfo = ((IServerInfo) entry);
        serverInfo.setLastPing(System.currentTimeMillis());
    }
}

