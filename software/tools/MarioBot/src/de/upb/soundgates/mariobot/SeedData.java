package de.upb.soundgates.mariobot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeedData {
	private List<MusicalEvent> events = new ArrayList<MusicalEvent>();
	
	
	public void add(MusicalEvent event){
		events.add(event);
	}
	
	private void printAsTable(HashMap<MusicalEvent, HashMap<MusicalEvent, Double>> toPrint){
		for(MusicalEvent key : toPrint.keySet()){
			System.out.print(key + " ->");
			for(MusicalEvent key2 : toPrint.get(key).keySet()){
				System.out.print(" [" + key2 + "," + toPrint.get(key).get(key2) + "]");
			}
			System.out.println();
		}
		
	}
	
	public HashMap<MusicalEvent, HashMap<MusicalEvent, Double>> getProbabilities(){
		HashMap<MusicalEvent, HashMap<MusicalEvent, Double>> count = new HashMap<MusicalEvent, HashMap<MusicalEvent, Double>>();
		for (int i = 1; i< events.size(); i++){
			if (!count.containsKey(events.get(i-1))){
				count.put(events.get(i-1), new HashMap<MusicalEvent, Double> ());
			} else {
				if (!count.get(events.get(i-1)).containsKey(events.get(i))){
					count.get(events.get(i-1)).put(events.get(i), (double) 1);
				} else {
					count.get(events.get(i-1)).put(events.get(i), count.get(events.get(i-1)).get(events.get(i)) + 1);
				}
			}
		}
		printAsTable(count);
		for(MusicalEvent key : count.keySet()){
			for(MusicalEvent key2 : count.get(key).keySet()){
//				count.get(key).put(key2, count.get(key).get(key2) / events.size());
				int counter = 0;
				for (MusicalEvent key3 : count.get(key).keySet()){
					counter += count.get(key).get(key3);
				}
				count.get(key).put(key2, count.get(key).get(key2) / counter);
			}
			System.out.println();
		}

		printAsTable(count);
		return count;
	}
	
}
