package misterx.bypassADC.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class DirectConnectSpoofScreen extends Screen {

    Runnable callback;
    long waitingTime;

    public DirectConnectSpoofScreen(int waitingTime, Runnable callback) {
        super(Text.literal("Direct connect spoof screen"));
        this.callback = callback;
        this.waitingTime = System.currentTimeMillis() + waitingTime;
    }

    @Override
    public void tick() {
        if (waitingTime < System.currentTimeMillis()) {
            callback.run();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, "Bypassing AntiDirectConnect...", this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
