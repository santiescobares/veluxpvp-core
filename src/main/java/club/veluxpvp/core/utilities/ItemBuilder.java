package club.veluxpvp.core.utilities;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemBuilder {

	private Material material;
	private int amount;
	private byte dataValue;
	private String name;
	private String skullOwner;
	private List<PotionEffect> potionEffects;
	private List<String> lore;
	private Map<Enchantment, Integer> enchants;
	
	public ItemBuilder() {
		this.material = null;
		this.amount = 1;
		this.dataValue = 0;
		this.name = null;
		this.skullOwner = null;
		this.potionEffects = Lists.newArrayList();
		this.lore = Lists.newArrayList();
		this.enchants = Maps.newHashMap();
	}
	
	public ItemBuilder of(Material material) {
		this.material = material;
		return this;
	}
	
	public ItemBuilder amount(int amount) {
		this.amount = amount;
		return this;
	}
	
	public ItemBuilder dataValue(byte dataValue) {
		this.dataValue = dataValue;
		return this;
	}
	
	public ItemBuilder name(String name) {
		this.name = name;
		return this;
	}
	
	public ItemBuilder skull(String skullOwner) {
		this.skullOwner = skullOwner;
		return this;
	}
	
	public ItemBuilder effect(PotionEffect effect) {
		this.potionEffects.add(effect);
		return this;
	}
	
	public ItemBuilder lore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public ItemBuilder addEnchant(Enchantment enchant, int level) {
		if(enchant == null) return this;
		this.enchants.put(enchant, level);
		
		return this;
	}
	
	public ItemStack build() {
		ItemStack item = new ItemStack(this.material, this.amount, this.dataValue);
		
		if(this.skullOwner != null) {
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			
			meta.setOwner(this.skullOwner);
			
			if(this.name != null) {
				meta.setDisplayName(ChatUtil.TRANSLATE(this.name));
			}
			
			if(this.lore.size() > 0) {
				List<String> lore = Lists.newArrayList();
				
				for(String l : this.lore) {
					lore.add(ChatUtil.TRANSLATE(l));
				}
				
				meta.setLore(lore);
			}

			item.setItemMeta(meta);
		} else if(this.potionEffects.size() > 0) {
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			
			for(PotionEffect e : this.potionEffects) {
				meta.addCustomEffect(e, true);
			}
			
			if(this.name != null) {
				meta.setDisplayName(ChatUtil.TRANSLATE(this.name));
			}
			
			if(this.lore.size() > 0) {
				List<String> lore = Lists.newArrayList();
				
				for(String l : this.lore) {
					lore.add(ChatUtil.TRANSLATE(l));
				}
				
				meta.setLore(lore);
			}

			item.setItemMeta(meta);
		} else {
			ItemMeta meta = item.getItemMeta();
			
			if(this.name != null) {
				meta.setDisplayName(ChatUtil.TRANSLATE(this.name));
			}
			
			if(this.lore.size() > 0) {
				List<String> lore = Lists.newArrayList();
				
				for(String l : this.lore) {
					lore.add(ChatUtil.TRANSLATE(l));
				}
				
				meta.setLore(lore);
			}

			item.setItemMeta(meta);
		}
		
		for(Map.Entry<Enchantment, Integer> enchantsEntry : this.enchants.entrySet()) {
			item.addUnsafeEnchantment(enchantsEntry.getKey(), enchantsEntry.getValue());
		}

		return item;
	}
}
