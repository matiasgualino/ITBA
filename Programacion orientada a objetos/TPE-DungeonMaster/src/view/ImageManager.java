package view;

import java.awt.Color;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import model.board.Cell;
import model.board.Content;
import model.element.Blood;
import model.element.Bonus;
import model.element.HealthBonus;
import model.element.HealthPotion;
import model.element.AttackBonus;
import model.element.Shield;
import model.element.Sword;
import model.element.Wall;
import model.fighter.Fighter;
import model.fighter.FighterHero;
import model.fighter.Goblin;
import model.fighter.Golem;
import model.fighter.Hero;
import model.fighter.Serpent;
import model.fighter.ShieldHero;
import model.fighter.SwordHero;

public class ImageManager {
	
	private Map<String, Image> images = new HashMap<String, Image>();
	
	public ImageManager() {
		initImages();
	}

	public void initImages() {
		try{
			
			// Images for Hero
			images.put(Hero.class.getName(), ImageUtils.loadImage("resources/hero.png"));
			images.put(Hero.class.getName() + "down", ImageUtils.loadImage("resources/heroDown.png"));
			images.put(Hero.class.getName() + "up", ImageUtils.loadImage("resources/heroUp.png"));
			images.put(Hero.class.getName() + "left", ImageUtils.loadImage("resources/heroLeft.png"));
			images.put(Hero.class.getName() + "right", ImageUtils.loadImage("resources/heroRight.png"));

			// Images for SwordHero
			images.put(SwordHero.class.getName(), ImageUtils.loadImage("resources/swordHero.png"));
			images.put(SwordHero.class.getName() + "down", ImageUtils.loadImage("resources/swordHeroDown.png"));
			images.put(SwordHero.class.getName() + "up", ImageUtils.loadImage("resources/swordHeroUp.png"));
			images.put(SwordHero.class.getName() + "left", ImageUtils.loadImage("resources/swordHeroLeft.png"));
			images.put(SwordHero.class.getName() + "right", ImageUtils.loadImage("resources/swordHeroRight.png"));
			
			// Images for ShieldHero
			images.put(ShieldHero.class.getName(), ImageUtils.loadImage("resources/shieldHero.png"));
			images.put(ShieldHero.class.getName() + "down", ImageUtils.loadImage("resources/shieldHeroDown.png"));
			images.put(ShieldHero.class.getName() + "up", ImageUtils.loadImage("resources/shieldHeroUp.png"));
			images.put(ShieldHero.class.getName() + "left", ImageUtils.loadImage("resources/shieldHeroLeft.png"));
			images.put(ShieldHero.class.getName() + "right", ImageUtils.loadImage("resources/shieldHeroRight.png"));
			
			// Images for SuperHero
			/*images.put(SuperHero.class.getName(), ImageUtils.loadImage("resources/superHero.png"));
			images.put(SuperHero.class.getName() + "down", ImageUtils.loadImage("resources/superHeroDown.png"));
			images.put(SuperHero.class.getName() + "up", ImageUtils.loadImage("resources/superHeroUp.png"));
			images.put(SuperHero.class.getName() + "left", ImageUtils.loadImage("resources/superHeroLeft.png"));
			images.put(SuperHero.class.getName() + "right", ImageUtils.loadImage("resources/superHeroRight.png"));*/
			
			// Images for enemies
			images.put(Goblin.class.getName(), ImageUtils.loadImage("resources/goblin.png"));
			images.put(Golem.class.getName(), ImageUtils.loadImage("resources/golem.png"));
			images.put(Serpent.class.getName(), ImageUtils.loadImage("resources/serpent.png"));
			
			// Images for game elements
			images.put(Cell.class.getName(), ImageUtils.loadImage("resources/floor.png"));
			images.put(Wall.class.getName(), ImageUtils.loadImage("resources/wall.png"));
			images.put(Blood.class.getName(), ImageUtils.loadImage("resources/blood.png"));
			
			// Images for bonus
			images.put(HealthBonus.class.getName(), ImageUtils.loadImage("resources/healthbonus.png"));
			images.put(HealthPotion.class.getName(), ImageUtils.loadImage("resources/healthpotion.png"));
			images.put(AttackBonus.class.getName(), ImageUtils.loadImage("resources/attackbonus.png"));
			images.put(Sword.class.getName(), ImageUtils.loadImage("resources/sword.png"));
			images.put(Shield.class.getName(), ImageUtils.loadImage("resources/shield.png"));

			images.put("FOG", ImageUtils.loadImage("resources/fog.png"));
			
			images.put("ICON", ImageUtils.loadImage("resources/icon.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public Image get(Cell cell) {
		if (cell.hasContent()) {
			Content content = cell.getContent();
			if(content instanceof FighterHero){
				FighterHero fighter = (FighterHero) content;
				Image image = ImageUtils.overlap(images.get(cell.getClass().getName()), images.get(fighter.getClass().getName() + fighter.getMovementState()));
				return ImageUtils.drawString(image, String.valueOf(fighter.getLevel().getValue()), Color.YELLOW);
			}
			else if (content instanceof Fighter) {
				Fighter fighter = (Fighter) content;
				Image image = ImageUtils.overlap(images.get(cell.getClass().getName()), images.get(fighter.getClass().getName()));
				return ImageUtils.drawString(image, String.valueOf(fighter.getLevel().getValue()), Color.YELLOW);
			} 
			else if(content instanceof Bonus){
				Bonus bonus = (Bonus) content;
				Image image = ImageUtils.overlap(images.get(cell.getClass().getName()), images.get(bonus.getClass().getName()));
				return ImageUtils.drawString(image, String.valueOf(bonus.getValue()), Color.YELLOW);
			}
			else {
				return ImageUtils.overlap(images.get(cell.getClass().getName()), images.get(cell.getContent().getClass().getName()));
			}
		} else {
			return images.get(cell.getClass().getName());
		}
		
	}
	
	public Image get(String key) {
		return images.get(key);
	}
}
