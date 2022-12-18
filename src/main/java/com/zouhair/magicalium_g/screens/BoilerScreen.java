package com.zouhair.magicalium_g.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.zouhair.magicalium_g.Magicalium_g;
import com.zouhair.magicalium_g.screens.renderer.FluidTankRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.Optional;

import static com.zouhair.magicalium_g.blocks.entity.custom.BoilerBlockEntity.*;

public class BoilerScreen extends AbstractContainerScreen<BoilerMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(Magicalium_g.MOD_ID, "textures/gui/boiler_block_gui.png");

    private FluidTankRenderer waterTankRenderer;
    private FluidTankRenderer steamTankRenderer;

    public BoilerScreen(BoilerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        waterTankRenderer = new FluidTankRenderer(TANK_CAPACITY, true, 16, 51);
        steamTankRenderer = new FluidTankRenderer(TANK_CAPACITY, true, 16, 51);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidToolTipsInArea(pPoseStack, pMouseX, pMouseY, x, y);
    }

    private void renderFluidToolTipsInArea(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        // Tooltip for water tank
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 62, 18, 16, 51)) {
            renderTooltip(pPoseStack, waterTankRenderer.getTooltip(menu.getFluidStackInTank(WATER_TANK), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x,
                    pMouseY - y);
        }
        // Tooltip for steam tank
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 98, 18, 16, 51)) {
            renderTooltip(pPoseStack, steamTankRenderer.getTooltip(menu.getFluidStackInTank(STEAM_TANK), TooltipFlag.Default.NORMAL),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        if (pMouseX >= x + offsetX && pMouseX <= x + offsetX + width)
            return pMouseY >= y + offsetY && pMouseY <= y + offsetY + height;
        return false;
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        // add animation for when isBoiling();

        waterTankRenderer.render(pPoseStack, x + 62, y + 18, menu.getFluidStackInTank(WATER_TANK));
        steamTankRenderer.render(pPoseStack, x + 98, y + 18, menu.getFluidStackInTank(STEAM_TANK));
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }
}
