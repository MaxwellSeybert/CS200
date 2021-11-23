import java.util.ArrayList;
import java.util.Random;

public class CoalSimulator {
	/*
	 * Step 1: Check to see if any trains arrived at this hour. If so, how many
	 * trains are in the system, and is one of them the high-capacity train? If the
	 * high-capacity train arrives: Go to Step 7.
	 *
	 * � Step 2: If three standard trains are in the system, at least two are going
	 * to have to wait on hold and incur demurrage costs. Add $30,000 to cost for
	 * this hour�s waiting. Go to Step 5.
	 * 
	 * Step 3: If two trains are in the system, at least one will have to wait and
	 * incur demurrage costs. Add $15,000 to costs for this hour�s waiting. Go to
	 * Step 5.
	 * 
	 * � Step 4: If no trains are currently in the system, then let the system be
	 * idle, except check to see if the tipple is not at full capacity. If it is not
	 * at full capacity, then begin filling it. In 1 hr, two crews can fill one-half
	 * trainload into the tipple. Add $11,000 to cost for working crews if they fill
	 * the tipple during this hour. Go back and check for next hour to see if trains
	 * arrive. Proceed to next hour. Go to Step 1.
	 * 
	 * � Step 5: By now, we must see if we can actually fill the train that is
	 * on-line to be filled. If so, then take one-third of a trainload away from the
	 * tipple and put it into the train. If the train is full, send it on its way
	 * and reset to 0 the load value of the current train being loaded. Otherwise,
	 * keep track of the size of the load in the current train being loaded. Proceed
	 * to the next hour. Go to Step 1.
	 * 
	 * 
	 * � Step 6: If the tipple needs filling, we should fill it with two crews, add
	 * a half trainload to the tipple, add $1,000 demurrage cost for this train to
	 * sit on the tracks waiting for the tipple to load, and add $21,000 to the
	 * labor costs for the crews at work. Proceed to next hour. Go to Step 1.
	 * 
	 * 
	 * � Step 7: Now that a high-capacity train with five engines has arrived, to
	 * prevent exorbitant demurrage costs for making this train wait in lieu of
	 * another, we will automatically switch to service the high-capacity train. If
	 * another train is currently being serviced, then we will put it on hold for
	 * now.
	 * 
	 * 
	 * � Step 8: If three standard trains are now waiting, all three are going to
	 * have to wait for the high-capacity train and incur demurrage costs. Add
	 * $45,000 to costs for this hour�s waiting.
	 * 
	 * � Step 9: If two standard trains are now waiting for the high-capacity train
	 * to be serviced, both will have to wait a qnd incur demurrage costs. Add
	 * $30,000 to costs for this hour�s waiting.
	 * 
	 * 
	 * � Step 10: If one standard train is now waiting for the high-capacity train
	 * to be serviced, then the standard train will have to incur demurrage costs
	 * for this hour. Add $15,000 to costs for this hour of waiting.
	 * 
	 * 
	 * � Step 11: By now, we must see if we can actually fill the high-capacity
	 * train that is on-line to be filled. If so, then no demurrage costs will be
	 * incurred for this one train for this hour. If the tipple needs filling, we
	 * should fill it with two crews, add a half trainload to the tipple, add to
	 * costs $25,000 for the high-capacity train to sit on the tracks waiting and
	 * $21,000 for the crews at work. Proceed to the next hour and go back to Step 1
	 * now if the tipple was filled and the high-capacity train had to wait.
	 * 
	 * 
	 * � Step 12: If we did not need to fill the tipple, then take one-third of a
	 * trainload away from the tipple and put it into the train. If the highcapacity
	 * train is full, send it on its way and let the load value of the current train
	 * being worked on go to 0 since it has now left. Otherwise, keep track of the
	 * size of the load in the current train being serviced. Proceed to next hour
	 * and go back to Step 1.
	 * 
	 */

	public static void main(String[] args) throws InterruptedException {
		// built in for randomizing arrive and depart attribute
		Math.random();
		Random rand = new Random();

		double hours = 00.00;
		if (hours == 0) {
		}
		// instance of tipple
		Tipple tipple = new Tipple();
		// array list of trains type Train
		ArrayList<Train> trainList = new ArrayList<Train>();
		while (true) {
			
			if (hours == 0) {
				trainList = new ArrayList<Train>();
				// semi random instances of trains
				for (int i = 0; i < 3; i++) {
					double arrive = (double) rand.nextInt(14);
					double depart = arrive + 8;
					Train trainOne = new Train(3, false, false, 0.0, arrive + 5, depart);

					trainList.add(trainOne);

				}
				for (int j = 0; j < 1; j++) {
					double arriveHC = (double) rand.nextInt(2);
					double departHC = arriveHC + 16;

					Train trainFourHC = new Train(5, false, false, 0.0, arriveHC + 11, departHC);
					trainList.add(trainFourHC);

				}
			}
			// used in fill train(),instantiate variables to pass from tipple
			int demurrageFees = 0;
			int crewFees = 0;
			Train arrivedTrain = null;

			for (Train train : trainList) {
				// step 1 check trains in train list for high capacity and move to front of list
				if (train.getArrivalTime() == hours) {
					arrivedTrain = train;
				}
				//when a train arrives and the tipple is empty
				if (train.getArrivalTime() == hours && tipple.getCurrentTrain() == null) {
					
					// prioritize high capacity
					if (train.getNumberOfEngines() == 5) {
						tipple.setCurrentTrain(train);
						train.setDepartureTime(hours + 6.0);
					}
					else   {
						tipple.setCurrentTrain(train);
						train.setDepartureTime(hours + 3.0);
					}
					// adds trains in trainlist to que
				} else if (train.getArrivalTime() == hours && tipple.getCurrentTrain() != null) {
					tipple.addToTippleQue(train);

				}

			}

			// step 4 check tipple at zero and rounds to eliminate the decimals

			if (Math.round(tipple.getTippleFillLevel() * 100) / 100 <= 0 || tipple.getCurrentTrain() == null) {
				//crew fees done from load tipple
				int temp = tipple.loadTipple();
				if(temp != -1) {
					crewFees = temp;
				}
				
				//check for presence of train and the status of fill level 
			} else if (tipple.getCurrentTrain() != null && !tipple.getCurrentTrain().getIsFull()) {
				demurrageFees = tipple.fillTrain(hours);

			} else if (tipple.getCurrentTrain() == null && tipple.getTrainsArrived().size() > 0) {
				tipple.setCurrentTrain(tipple.getTrainsArrived().get(0));
			}
			System.out.println();
			System.out.print("Current hour: " + hours + "  tipple fill lvl: " + tipple.getTippleFillLevel());
			System.out.print("\n Train A " + " Train B " + " Train C " + " Train HC " + " Crew Fees: " + crewFees);
			System.out.println();
			// tracking the cost of train fees on each train in the list
			for (Train train : trainList) {
				if (arrivedTrain == train) {
					System.out.print(" arrived");
					arrivedTrain = null;
				} else if (demurrageFees > 0 && train.getDepartureTime() == hours) {
					System.out.println(" leaves");
					System.out.println(demurrageFees);
				}

				else {
					System.out.print("         ");
				}
			}

			System.out.println();
			hours++;

			if (hours == 24.0) {
				hours = 0.0;

			}
			Thread.sleep(500);
			// add STDdraw for graphics
		}
	}

}