package misterx.bypassADC.mixin;

import misterx.bypassADC.mixininterface.IServerInfo;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerInfo.class)
public class ServerInfoMixin implements IServerInfo {
    @Unique
    long lastPing = 0;

    public void setLastPing(long timeMillis) {
        lastPing = timeMillis;
    }

    public long getLastPing() {
        return lastPing;
    }

    @Inject(method = "fromNbt", at = @At("TAIL"))
    private static void onFromNBT(NbtCompound root, CallbackInfoReturnable<ServerInfo> cir) {
        if (root.contains("lastPing", NbtElement.LONG_TYPE)) {
            IServerInfo info = (IServerInfo) cir.getReturnValue();
            info.setLastPing(root.getLong("lastPing"));
        }
    }

    @Inject(method = "toNbt", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void onToNBT(CallbackInfoReturnable<NbtCompound> cir, NbtCompound nbtCompound) {
        nbtCompound.putLong("lastPing", this.lastPing);
    }
}
