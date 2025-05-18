/*
 * Copyright (C) 2023-2025 Jyguy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.reknown.fastercrystals.listeners.bukkit;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldUnloadEvent;
import xyz.reknown.fastercrystals.FasterCrystals;

public class WorldUnloadListener implements Listener {
    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        FasterCrystals.getInstance().getCrystalIds().entrySet().removeIf(entry -> entry.getValue().getWorld().equals(event.getWorld()));
    }
}
