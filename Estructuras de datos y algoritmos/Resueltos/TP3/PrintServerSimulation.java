package TP3;

import java.util.LinkedList;
import java.util.Queue;

public class PrintServerSimulation {
	public static void main(String[] args) {

		Queue<PrintOrder> list = new LinkedList<PrintOrder>();
		int simulationTime = 1000;
		double arrivalProbability = 0.3;
		int minServiceTime = 1;
		int maxServiceTime = 3;
		int ordersPrinted = 0;
		int printingTime =0;
		int averageQueueSize = 0;

		for (int i = 0; i < simulationTime; i++) {
			/* Agrego impresiones segun la probabilidad */
			if (Math.random() <= arrivalProbability) {
				PrintOrder aux = new PrintOrder(minServiceTime, maxServiceTime);
				list.add(aux);
				printingTime += aux.time;
			}
			averageQueueSize += list.size();
			/*Imprimo*/
			if (list.peek() != null) {
				if (list.peek().time == 1) {
					ordersPrinted++;
					list.poll();
				}else{
					list.peek().time--;
				}
			}
		}
		System.out.println("Total prints: " + ordersPrinted);
		System.out.println("Average waiting time: " + printingTime/ordersPrinted);
		System.out.println("Average queue size: " + averageQueueSize/simulationTime);

	}

	private static class PrintOrder {
		int time;

		public PrintOrder(int min, int max) {
			this.time = (int) Math.floor(Math.random() * (max - min + 1) + min);
		}
	}
}
