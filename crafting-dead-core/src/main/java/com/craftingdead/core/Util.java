/*
 * Crafting Dead
 * Copyright (C) 2021  NexusNode LTD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.craftingdead.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import com.mojang.serialization.Codec;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Util {

  public static <T> Optional<T> optional(@Nullable Supplier<T> supplier) {
    return Optional.ofNullable(supplier).map(Supplier::get);
  }

  public static <T> Supplier<T> supply(T value) {
    return () -> value;
  }

  public static <T> Codec<Set<T>> setOf(Codec<T> codec) {
    return codec.listOf().xmap(HashSet::new, ArrayList::new);
  }

  public static <T> RegistryKey<T> createKey(
      RegistryKey<? extends Registry<T>> registryKey, String name) {
    return RegistryKey.create(registryKey, new ResourceLocation(CraftingDead.ID, name));
  }

  public static <T> RegistryKey<Registry<T>> createRegistryKey(String name) {
    return RegistryKey.createRegistryKey(new ResourceLocation(CraftingDead.ID, name));
  }
}
