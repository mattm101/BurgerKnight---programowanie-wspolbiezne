package com.malecmateusz.burger_knight.utils;
import java.util.Random;

public class Randomizer {
	private static String[] character = {
			"umatiego/sprites/fey.png",
			"umatiego/sprites/chinese_m1.png",
			"umatiego/sprites/kenyan_m1.png",
			"umatiego/sprites/devil.png",
			"umatiego/sprites/moderngirl01.png",
			"umatiego/sprites/captainamerica.png",
			"umatiego/sprites/ironman.png",
			"umatiego/sprites/darthvader.png",
			"umatiego/sprites/bridesmaid05.png",
			"umatiego/sprites/weddingguy04.png",
			"umatiego/sprites/nightelf_male3.png",
			"umatiego/sprites/nightelf_male2.png",
			"umatiego/sprites/nightelf_female2.png",
			"umatiego/sprites/nightelf_female1.png",
			"umatiego/sprites/pirategirl.png",
			"umatiego/sprites/pirate_f2.png",
			"umatiego/sprites/pirate_f1.png",
			"umatiego/sprites/pirate_m3.png",
			"umatiego/sprites/pirate_m2.png",
			"umatiego/sprites/chinese_f1.png",
			"umatiego/sprites/honeybee3.png",
			"umatiego/sprites/protocoldroid1.png",
			"umatiego/sprites/tibet.png",
			"umatiego/sprites/yoda.png",
			"umatiego/sprites/sadako.png"
	};
	
	public static String randomizeClient(){
		Random rand = new Random();
		return character[rand.nextInt(character.length)];
	}
}
