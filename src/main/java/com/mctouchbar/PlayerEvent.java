package com.mctouchbar;


import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArrowItem;

import com.thizzer.jtouchbar.common.Color;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Objects;


public class PlayerEvent {
	
	private Item oldItem = null;
	
	private int oldArrowsAmount = -1;
	
	private String oldCoords = "";
	private int oldHealth = 0;
	
	private String oldHealthEffectName = "";
	private String oldMovementEffectName = "";
	
	private String healthEffectName = "";
	private String movementEffectName = "";

	


	@SubscribeEvent
	public void onClientTick(TickEvent.PlayerTickEvent event) {
			final Player player = Minecraft.getInstance().player;
			
			if (event.phase == TickEvent.Phase.START && event.side.isClient()) {
				
				
				// Health indicator

                assert player != null;
                int health = (int) player.getHealth();
				if (health != oldHealth) {
					MCTouchBar.touchBarButtonLifeImg.setTitle(""+health);
				}
				oldHealth = health;

				healthEffectName = "";
				movementEffectName = "";
				player.getActiveEffects().forEach((effect) -> {
					String name = effect.getEffect().toString(); //FIXME
					if (Objects.equals(name, "effect.absorption") || Objects.equals(name, "effect.poison") || Objects.equals(name, "effect.wither") || Objects.equals(name, "effect.regeneration") || Objects.equals(name, "effect.healthBoost") || Objects.equals(healthEffectName, "effect.resistance")) {
						healthEffectName = name;
					} else if (Objects.equals(name, "effect.moveSpeed") || Objects.equals(name, "effect.moveSlowdown") || Objects.equals(name, "effect.levitation") || Objects.equals(name, "effect.jump") || Objects.equals(name, "effect.invisibility")) {
						movementEffectName = name;
					}
					
				});
				
				if (!Objects.equals(healthEffectName, oldHealthEffectName)) {
                    switch (healthEffectName) {
                        case "effect.absorption" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.ABSO_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.YELLOW);
                        }
                        case "effect.poison" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.POISON_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.GREEN);
                        }
                        case "effect.wither" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.WITHER_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.PURPLE);
                        }
                        case "effect.regeneration" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.HEALTH_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.MAGENTA);
                        }
                        case "effect.healthBoost" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.BOOST_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.ORANGE);
                        }
                        case "effect.resistance" -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.RESISTANCE_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.GRAY);
                        }
                        case null, default -> {
                            MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.HEALTH_IMAGE);
                            MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.RED);
                        }
                    }
				}
				oldHealthEffectName = healthEffectName;

				
				
				// Arrows counter
				
				int count = 0;
				for (int slot = 0; slot < Inventory.INVENTORY_SIZE; slot++) {
					ItemStack stack = player.getInventory().getItem(slot);

					if (stack.getItem() instanceof ArrowItem) {
							count = count + stack.getCount();
					}
				}
				if (count != oldArrowsAmount) {
					MCTouchBar.touchBarButtonArrowsImg.setTitle(""+count);
				}

				
				
				
				
				// Coordinates
				
				
				int x = (int) player.position().get(Direction.Axis.X);
				int y = (int) player.position().get(Direction.Axis.Y);
				int z = (int) player.position().get(Direction.Axis.Z);
				String coords = x + " " + y + " " + z;
				if (!coords.equals(oldCoords)) {
					MCTouchBar.touchBarCoordsButton.setTitle(coords);
				}
				

				if (!Objects.equals(oldMovementEffectName, movementEffectName)) {
                    switch (movementEffectName) {
                        case "effect.moveSpeed" -> {
                            MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.SPEED_IMAGE);
                            MCTouchBar.touchBarCoordsButton.setImagePosition(2);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.CYAN);
                        }
                        case "effect.moveSlowdown" -> {
                            MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.SLOW_IMAGE);
                            MCTouchBar.touchBarCoordsButton.setImagePosition(2);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.BLUE);
                        }
                        case "effect.jump" -> {
                            MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.JUMP_IMAGE);
                            MCTouchBar.touchBarCoordsButton.setImagePosition(2);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.GREEN);
                        }
                        case "effect.levitation" -> {
                            MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.LEVI_IMAGE);
                            MCTouchBar.touchBarCoordsButton.setImagePosition(2);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.MAGENTA);
                        }
                        case "effect.invisibility" -> {
                            MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.INVIS_IMAGE);
                            MCTouchBar.touchBarCoordsButton.setImagePosition(2);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.CYAN);
                        }
                        case null, default -> {
                            MCTouchBar.touchBarCoordsButton.setImagePosition(0);
                            MCTouchBar.touchBarCoordsButton.setBezelColor(Color.BLACK);
                        }
                    }
				}
				oldMovementEffectName = movementEffectName;


			}			
		
	}

}