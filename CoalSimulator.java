import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CoalSimulator {
	/*
	 * Step 1: Check to see if any trains arrived at this hour. If so, how many
	 * trains are in the system, and is one of them the high-capacity train? If the
	 * high-capacity train arrives: Go to Step 7.
	 *
	 * • Step 2: If three standard trains are in the system, at least two are going
	 * to have to wait on hold and incur demurrage costs. Add $30,000 to cost for
	 * this hour’s waiting. Go to Step 5.
	 * 
	 * Step 3: If two trains are in the system, at least one will have to wait and
	 * incur demurrage costs. Add $15,000 to costs for this hour’s waiting. Go to
	 * Step 5.
	 * 
	 * • Step 4: If no trains are currently in the system, then let the system be
	 * idle, except check to see if the tipple is not at full capacity. If it is not
	 * at full capacity, then begin filling it. In 1 hr, two crews can fill one-half
	 * trainload into the tipple. Add $11,000 to cost for working crews if they fill
	 * the tipple during this hour. Go back and check for next hour to see if trains
	 * arrive. Proceed to next hour. Go to Step 1.
	 * 
	 * • Step 5: By now, we must see if we can actually fill the train that is
	 * on-line to be filled. If so, then take one-third of a trainload away from the
	 * tipple and put it into the train. If the train is full, send it on its way
	 * and reset to 0 the load value of the current train being loaded. Otherwise,
	 * keep track of the size of the load in the current train being loaded. Proceed
	 * to the next hour. Go to Step 1.
	 * 
	 * 
	 * • Step 6: If the tipple needs filling, we should fill it with two crews, add
	 * a half trainload to the tipple, add $1,000 demurrage cost for this train to
	 * sit on the tracks waiting for the tipple to load, and add $21,000 to the
	 * labor costs for the crews at work. Proceed to next hour. Go to Step 1.
	 * 
	 * 
	 * • Step 7: Now that a high-capacity train with five engines has arrived, to
	 * prevent exorbitant demurrage costs for making this train wait in lieu of
	 * another, we will automatically switch to service the high-capacity train. If
	 * another train is currently being serviced, then we will put it on hold for
	 * now.
	 * 
	 * 
	 * • Step 8: If three standard trains are now waiting, all three are going to
	 * have to wait for the high-capacity train and incur demurrage costs. Add
	 * $45,000 to costs for this hour’s waiting.
	 * 
	 * • Step 9: If two standard trains are now waiting for the high-capacity train
	 * to be serviced, both will have to wait a qnd incur demurrage costs. Add
	 * $30,000 to costs for this hour’s waiting.
	 * 
	 * 
	 * • Step 10: If one standard train is now waiting for the high-capacity train
	 * to be serviced, then the standard train will have to incur demurrage costs
	 * for this hour. Add $15,000 to costs for this hour of waiting.
	 * 
	 * 
	 * • Step 11: By now, we must see if we can actually fill the high-capacity
	 * train that is on-line to be filled. If so, then no demurrage costs will be
	 * incurred for this one train for this hour. If the tipple needs filling, we
	 * should fill it with two crews, add a half trainload to the tipple, add to
	 * costs $25,000 for the high-capacity train to sit on the tracks waiting and
	 * $21,000 for the crews at work. Proceed to the next hour and go back to Step 1
	 * now if the tipple was filled and the high-capacity train had to wait.
	 * 
	 * 
	 * • Step 12: If we did not need to fill the tipple, then take one-third of a
	 * trainload away from the tipple and put it into the train. If the highcapacity
	 * train is full, send it on its way and let the load value of the current train
	 * being worked on go to 0 since it has now left. Otherwise, keep track of the
	 * size of the load in the current train being serviced. Proceed to next hour
	 * and go back to Step 1.
	 * 
	 */

	public static ArrayList<String> CSVtoArrayList(String CSVFileName) {
		// the method that we used in homework 4 is being used again to read in the info
		// except for this time it is information about trains
		ArrayList<String> arrlist = new ArrayList<String>();

		if (CSVFileName != null) {
			String[] splitData = CSVFileName.split("\\,", -1); // the -1 helps handle the null values

			for (int i = 0; i < splitData.length; i++) {
				// if it is null, replace it with a 0
				if (splitData[i].length() == 0) {
					splitData[i] = "0";
				}
				// as long as it is not null and the length is not 0, trim the value and add it
				// to the arraylist
				if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
					arrlist.add(splitData[i].trim());
				}
			}
		}
		return arrlist;
	}

	public static <T extends Comparable<T>> ArrayList<Train> sortTrains(ArrayList<Train> trainList, int sortBy) {
		T val1, val2;
		int length = trainList.size();
		for (int i = 0; i < length; i++) {
			int position = i;
			for (int j = i + 1; j < length; j++) {
				if (sortBy == 1) {
					Integer tempVal = trainList.get(position).getTrainID();
					val2 = (T) tempVal;
					tempVal = trainList.get(j).getTrainID();
					val1 = (T) tempVal;

				} else if (sortBy == 2) {
					Double tempVal = trainList.get(position).getArrivalTime();
					val2 = (T) tempVal;
					tempVal = trainList.get(j).getArrivalTime();
					val1 = (T) tempVal;
				} else if (sortBy == 3) {
					Double tempVal = trainList.get(position).getDepartureTime();
					val2 = (T) tempVal;
					tempVal = trainList.get(j).getDepartureTime();
					val1 = (T) tempVal;
				} else if (sortBy == 4) {
					Integer tempVal = trainList.get(position).getNumberOfEngines();
					val2 = (T) tempVal;
					tempVal = trainList.get(j).getNumberOfEngines();
					val1 = (T) tempVal;
				} else {
					Integer tempVal = trainList.get(position).calculateTrainFees();
					val2 = (T) tempVal;
					tempVal = trainList.get(j).calculateTrainFees();
					val1 = (T) tempVal;
				}
				if (val1.compareTo(val2) < 0) {
					position = j;
				}
			}
			Collections.swap(trainList, i, position);

		}

		return (trainList);
	}

	public static <T extends Comparable<T>> void search(ArrayList<Train> trainList, int searchBy, T searchTerm) {
		int location;
		T compareVal;
		int length = trainList.size();
		int i = 0;

		if (searchBy == 1) {
			while (i < length + 1) {
				if (i == length) {
					System.out.println("Nothing with that search term was found. Exiting.");
					break;
				} else {
					Integer tempVal = trainList.get(i).getTrainID();
					compareVal = (T) tempVal;
					if (compareVal.equals(searchTerm)) {
						System.out.printf("%-17s  ", "Train ID");
						System.out.printf("%-17s  ", "Number of Engines");
						System.out.printf("%-17s  ", "Arrival Time");
						System.out.printf("%-17s  ", "Train Fees");
						System.out.printf("%-17s  \n", "Departure Time");
						trainList.get(i).printInfo();
						break;
					}
				}
				i++;
			}
		} else if (searchBy == 2) {
			while (i < length + 1) {
				if (i == length) {
					System.out.println("Nothing with that search term was found. Exiting.");
					break;
				} else {
					Double tempVal = trainList.get(i).getArrivalTime();
					compareVal = (T) tempVal;
					if (compareVal.equals(searchTerm)) {
						System.out.printf("%-17s  ", "Train ID");
						System.out.printf("%-17s  ", "Number of Engines");
						System.out.printf("%-17s  ", "Arrival Time");
						System.out.printf("%-17s  ", "Train Fees");
						System.out.printf("%-17s  \n", "Departure Time");
						trainList.get(i).printInfo();
						break;
					}
				}
				i++;
			}
		} else {
			while (i < length + 1) {
				if (i == length) {
					System.out.println("Nothing with that search term was found. Exiting.");
					break;
				} else {
					Double tempVal = trainList.get(i).getDepartureTime();
					compareVal = (T) tempVal;
					if (compareVal.equals(searchTerm)) {
						System.out.printf("%-17s  ", "Train ID");
						System.out.printf("%-17s  ", "Number of Engines");
						System.out.printf("%-17s  ", "Arrival Time");
						System.out.printf("%-17s  ", "Train Fees");
						System.out.printf("%-17s  \n", "Departure Time");
						trainList.get(i).printInfo();
						break;
					}
				}
				i++;
			}
		}

	}

	public static ArrayList<Train> createTrainList() {
		ArrayList<Train> trainList = new ArrayList<Train>();
		ArrayList<String> data = new ArrayList<String>();
		BufferedReader readBuffer = null;
		try {
			// creates a string for the input line then asks the user for the file location
			// C:\Users\eatca\eclipse-workspace\FinalProject\src\trainInfo1.csv
			String inputLine;
			String fileLoc = null;
			Scanner scnr = new Scanner(System.in);
			System.out.println("Welcome to train cost optimizer!");
			System.out.println("To run this program a CSV file will be needed with the following format");
			// TO-DO make this be more informative on what info is needed
			System.out.println(
					"Column 1 - Train ID (integer)   Column 2 - Number of Engines(integer)  Column 3 - isFull(boolean) Column 4 - fillLevel (double between 0 -1.5) Column 4 - Arrival Time (number between 1 - 24)");
			System.out.println("Enter the file location of your train information:");
			// takes in teh file location
			fileLoc = scnr.nextLine();
			readBuffer = new BufferedReader(new FileReader(fileLoc));
			int lineCount = 0;
			while ((inputLine = readBuffer.readLine()) != null) {
				// reads in the file line by line and creates a train object from each line

				if (lineCount != 0) {
					data = CSVtoArrayList(inputLine);
					int trainId = Integer.valueOf(data.get(0));
					int numberOfEngines = Integer.valueOf(data.get(1));
					boolean isFull = Boolean.valueOf(data.get(2));
					double fillLevel = Double.valueOf(data.get(3));
					double arriveTime = Double.valueOf(data.get(4));
					trainList.add(new Train(trainId, numberOfEngines, isFull, fillLevel, arriveTime));
				}
				lineCount++;

			}

		} catch (Exception e) {
			System.out.println("There was an error reading the file.");

		}
		// returns the list of trains
		return (trainList);
	}

	public static void main(String[] args) throws InterruptedException {
		// built in for randomizing arrive and depart attribute
		Math.random();
		Random rand = new Random();
		Scanner scnr = new Scanner(System.in);
		int days = 0;
		double hours = 00.00;
		if (hours == 0) {
		}
		// instance of tipple
		Tipple tipple = new Tipple();
		// array list of trains type Train

		ArrayList<Train> trainList = createTrainList();
		trainList = sortTrains(trainList, 2);
		while (true) {

//
//			if (hours == 0) {
//				trainList = new ArrayList<Train>();
//				// semi random instances of trains
//				for (int i = 0; i < 3; i++) {
//					double arrive = (double) rand.nextInt(14);
//					double depart = arrive + 8;
//					Train trainOne = new Train(3, false, false, 0.0, arrive + 5, depart);
//
//					trainList.add(trainOne);
//
//				}
//				for (int j = 0; j < 1; j++) {
//					double arriveHC = (double) rand.nextInt(2);
//					double departHC = arriveHC + 16;
//
//					Train trainFourHC = new Train(5, false, false, 0.0, arriveHC + 11, departHC);
//					trainList.add(trainFourHC);
//
//				}
			// }
			// used in fill train(),instantiate variables to pass from tipple
			int demurrageFees = 0;
			int crewFees = 0;
			Train arrivedTrain = null;

			for (Train train : trainList) {
				// step 1 check trains in train list for high capacity and move to front of list

				if (train.getArrivalTime() == hours) {
					arrivedTrain = train;
				}
				// when a train arrives and the tipple is empty
				if (train.getArrivalTime() == hours && tipple.getCurrentTrain() == null) {

					// prioritize high capacity
					if (train.getNumberOfEngines() == 5) {
						tipple.setCurrentTrain(train);
						train.setDepartureTime(hours + 6.0);
					} else {
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
				// crew fees done from load tipple
				int temp = tipple.loadTipple();
				if (temp != -1) {
					crewFees = temp;
				}

				// check for presence of train and the status of fill level
			} else if (tipple.getCurrentTrain() != null && !tipple.getCurrentTrain().getIsFull()) {
				demurrageFees = tipple.fillTrain(hours);

			} else if (tipple.getCurrentTrain() == null && tipple.getTrainsArrived().size() > 0) {
				tipple.setCurrentTrain(tipple.getTrainsArrived().get(0));
			}
//			System.out.println();
//			System.out.print("Current hour: " + hours + "  tipple fill lvl: " + tipple.getTippleFillLevel());
//			System.out.print("\n Train A " + " Train B " + " Train C " + " Train HC " + " Crew Fees: " + crewFees);
//			System.out.println();
//			// tracking the cost of train fees on each train in the list
//			for (Train train : trainList) {
//				if (arrivedTrain == train) {
//					System.out.print(" arrived");
//					arrivedTrain = null;
//				} else if (demurrageFees > 0 && train.getDepartureTime() == hours) {
//					System.out.println(" leaves");
//					System.out.println(demurrageFees);
//				}
//
//				else {
//					System.out.print("         ");
//				}
//			}
			hours++;
			if (hours == 24.0) {
				hours = 0.0;
				days += 1;
			}
			if (trainList.get(trainList.size() - 1).getDepartureTime() != -1) {
				break;
			}

		}
		String userInput;
		int userChoice, searchBy, sortBy;
		while (true) {
			try {
				System.out.println("Would you like to search or sort through the data?");
				System.out.println("Enter 1 to search and 2 to sort: ");
				userInput = scnr.next();
				userChoice = Integer.valueOf(userInput);
				if (userChoice == 1) {
					break;
				}
				if (userChoice == 2) {
					break;
				} else {
					throw new Exception("1 or 2 not entered");
				}
			} catch (Exception e) {
				System.out.println("Error please enter 1 or 2");
			}
		}
		if (userChoice == 1) {
			while (true) {
				try {
					System.out.println("How would you like to search");
					System.out.println("1 - Train ID");
					System.out.println("2 - Arrival Time");
					System.out.println("3 - Departure Time");
					System.out.println("Enter an integer between 1-3: ");
					userInput = scnr.next();
					searchBy = Integer.valueOf(userInput);
					if (searchBy > 3) {
						throw new Exception("index too large");
					} else if (searchBy < 1) {
						throw new Exception("index too small");
					} else {
						break;
					}
				} catch (Exception e) {
					System.out.println("Error please enter an integer between 1-3");
				}
			}
			if (searchBy == 1) {
				while (true) {

					try {
						System.out.println("Enter a train ID to search: ");
						userInput = scnr.next();
						Integer searchTerm = Integer.valueOf(userInput);
						search(trainList, searchBy, searchTerm);
						break;
					} catch (Exception e) {
						System.out.println("Integer train ID not entered, try again");
					}
				}
			} else if (searchBy == 2) {
				while (true) {

					try {
						System.out.println("Enter an Arrival time to search: ");
						Double searchTerm = scnr.nextDouble();
						search(trainList, searchBy, searchTerm);
						break;
					} catch (Exception e) {
						System.out.println("Valid arrival time not entered, try again");
					}
				}
			} else {
				while (true) {

					try {
						System.out.println("Enter a Departure time to search: ");
						Double searchTerm = scnr.nextDouble();
						
						search(trainList, searchBy, searchTerm);
						break;
					} catch (Exception e) {
						System.out.println("Valid departure time not entered, try again");
					}
				}

			}

		}
		if (userChoice == 2) {
			while (true) {
				try {
					System.out.println("Enter how you would like to sort the train list");
					System.out.println("1 - Train ID");
					System.out.println("2 - Arrival Time");
					System.out.println("3 - Departure Time");
					System.out.println("4 - Number of Engines");
					System.out.println("5 - Demurrage Cost");
					System.out.println("Enter an integer between 1-5");
					userInput = scnr.next();
					sortBy = Integer.valueOf(userInput);
					if (sortBy > 5) {
						throw new Exception("index too large");
					} else if (sortBy < 1) {
						throw new Exception("index too small");
					} else {
						break;
					}

				} catch (Exception e) {
					System.out.println("Error please enter an integer between 1-5");
				}
			}
			sortTrains(trainList, sortBy);
			for (Train train : trainList) {
				train.printInfo();
			}
		}

	}

}
