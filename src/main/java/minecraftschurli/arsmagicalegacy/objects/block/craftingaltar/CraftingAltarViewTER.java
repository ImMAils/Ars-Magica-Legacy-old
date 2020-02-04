package minecraftschurli.arsmagicalegacy.objects.block.craftingaltar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.RenderSystem;
import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import minecraftschurli.arsmagicalegacy.api.spell.crafting.*;
import net.minecraft.block.Blocks;
import net.minecraft.client.*;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author Minecraftschurli
 * @version 2019-12-20
 */
public class CraftingAltarViewTER extends TileEntityRenderer<CraftingAltarViewTileEntity> {
    public CraftingAltarViewTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void doRender(CraftingAltarTileEntity altar, CraftingAltarViewTileEntity view) {
        ItemStack book = altar.getBook();
        SpellIngredientList recipe = altar.getRecipe();
        if (book.isEmpty() || recipe == null)
            return;
        RenderSystem.pushMatrix();
        RenderSystem.scaled(0.55, 0.55, 0.55);
        if (!altar.hasEnoughPower()) {
            //drawNameplate(view, new TranslationTextComponent(ArsMagicaLegacy.MODID+".altar.lowpower").getFormattedText(), -0.5, -0.4, -0.5, 32);
        } else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            //drawNameplate(view, ingredient.getTooltip().getFormattedText(), -0.5, -0.4, -0.5, 32);
        }
        RenderSystem.popMatrix();
        RenderSystem.rotatef(view.itemRotation, 0.0F, 1.0F, 0.0F);
        //RenderSystem.rotatef(this.rendererDispatcher.renderInfo.getPitch(), 1.0F, 0.0F, 0.0F);
        ItemStack stack;
        if (!altar.hasEnoughPower()) {
            stack = new ItemStack(Blocks.BARRIER);
        } else {
            ISpellIngredient ingredient = altar.getCurrentIngredient();
            if (ingredient == null)
                return;
            if (ingredient instanceof ItemStackSpellIngredient) {
                stack = ((ItemStackSpellIngredient) ingredient).getStack();
            } else if (ingredient instanceof ItemTagSpellIngredient) {
                if (view.itemRotation % 36 == 0) {
                    view.itemCache = ((ItemTagSpellIngredient) ingredient).getTag().getRandomElement(altar.getWorld().rand);
                }
                stack = new ItemStack(view.itemCache);
            } else {
                ingredient.render();
                return;
            }
        }
        //noinspection deprecation
        //Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);
    }

    @Override
    public void render(CraftingAltarViewTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        tileEntityIn.itemRotation += 1;
        if (tileEntityIn.itemRotation == 360)
            tileEntityIn.itemRotation = 0;
        CraftingAltarTileEntity altar = tileEntityIn.getAltar();
        if (altar != null && altar.isMultiblockFormed()) {
            RenderSystem.pushMatrix();
            RenderSystem.translated(0.5, 0.5, 0.5);
            //setLightmapDisabled(true);

            doRender(altar, tileEntityIn);

            //setLightmapDisabled(false);
            RenderSystem.popMatrix();
        }
    }
}
