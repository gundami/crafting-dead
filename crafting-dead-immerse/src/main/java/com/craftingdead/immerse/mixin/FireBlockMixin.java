/*
 * Crafting Dead Copyright (C) 2021 NexusNode LTD
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */

package com.craftingdead.immerse.mixin;

import java.util.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.craftingdead.immerse.CraftingDeadImmerse;
import com.craftingdead.immerse.game.GameServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraftforge.fml.LogicalSide;

@Mixin(FireBlock.class)
public class FireBlockMixin {

  /**
   * Adds hook for {@link GameServer#disableBlockBurning()}.
   */
  @Inject(method = "tryCatchFire", at = @At(value = "HEAD"), cancellable = true, remap = false)
  private void tryCatchFire(Level level, BlockPos pos, int chance, Random random, int age,
      Direction face, CallbackInfo callbackInfo) {
    if (this.blockBurningDisabled(level)) {
      callbackInfo.cancel();
    }
  }

  /**
   * Adds hook for {@link GameServer#disableBlockBurning()}.
   */
  @Inject(method = "canCatchFire", at = @At(value = "HEAD"), cancellable = true, remap = false)
  private void canCatchFire(BlockGetter blockGetter, BlockPos pos, Direction face,
      CallbackInfoReturnable<Boolean> callbackInfo) {
    if (blockGetter instanceof Level && this.blockBurningDisabled((Level) blockGetter)) {
      callbackInfo.setReturnValue(false);
    }
  }

  private boolean blockBurningDisabled(Level level) {
    var game = CraftingDeadImmerse.getInstance()
        .getGame(level.isClientSide() ? LogicalSide.CLIENT : LogicalSide.SERVER);
    return game != null && game.disableBlockBurning();
  }
}
