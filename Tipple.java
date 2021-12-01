import java.util.ArrayList;

public class Tipple {
	private static double tippleCapacity = 1.50;
	private double tippleFillLevel;
	private int numberOfCrews;
	private int crewCost;
	private boolean isFull;
	private Train currentTrain;
	ArrayList<Train> trainsArrived;

	public Tipple() {

		this.numberOfCrews = 0;
		this.crewCost = 0;
		this.tippleFillLevel = 1.50;
		this.isFull = true;
		trainsArrived = new ArrayList<Train>();

	}

	public Tipple(int numberOfCrews_, int crewCost_, double tippleFillLevel_, boolean isFull_, Train currentTrain_) {

		numberOfCrews = numberOfCrews_;
		crewCost = crewCost_;
		tippleFillLevel = tippleFillLevel_;
		isFull = isFull_;
		currentTrain = currentTrain_;
		trainsArrived = new ArrayList<Train>();

	}

	public ArrayList<Train> getTrainsArrived() {
		return this.trainsArrived;
	}

	public int getNumberOfCrews() {
		return this.numberOfCrews;
	}

	public void setNumberOfCrews(int numberOfCrews) {
		this.numberOfCrews = numberOfCrews;
	}

	public int getCrewCost() {
		return this.crewCost;
	}

	public void setCrewCost(int crewCost) {
		this.crewCost = crewCost;
	}

	public Train getCurrentTrain() {

		return currentTrain;
	}

	public void setCurrentTrain(Train currentTrain) {
		this.currentTrain = currentTrain;

	}

	public void setCapacity(double capacity) {

		Tipple.tippleCapacity = capacity;
	}

	public double getTippleCapacity() {

		return Tipple.tippleCapacity;
	}

	public void setTippleFillLevel(double tippleFillLevel) {
		this.tippleFillLevel = tippleFillLevel;

	}

	public double getTippleFillLevel() {
		return this.tippleFillLevel;
	}

	public boolean getIsFull() {

		return this.isFull;
	}

	/*
	 * manage the order of trainsCheck to see if any trains arrived at this hour. If
	 * so, how many trains are in the system, and is one of them the high-capacity
	 * train? If the high-capacity train arrives and que other trains.
	 */
	public void addToTippleQue(Train newTrain) {

		trainsArrived.add(newTrain);

	}

	public void SubtractFromTippleQue(Train newTrain) {
		trainsArrived.remove(newTrain);
	}

	public void setIsFull(boolean isFull) {
		this.isFull = isFull;

	}

	/**
	 * @param trainsArrived the trainsArrived to set
	 */
	public void setTrainsArrived(ArrayList<Train> trainsArrived) {
		this.trainsArrived = trainsArrived;
	}

	public int crewFees() {
		int crewFees = 12000 * this.numberOfCrews - 3000;
		this.crewCost += crewFees;
		return this.crewCost;
	}

	public int loadTipple() {
		// if the tipple is full boolean is true and the edge case of the fill level
		// must be the same
		if (this.tippleFillLevel >= Tipple.tippleCapacity) {
			this.isFull = true;
			this.tippleFillLevel = Tipple.tippleCapacity;

		}

		else {
			this.isFull = false;
			setNumberOfCrews(1);
			this.tippleFillLevel += Tipple.tippleCapacity * (this.numberOfCrews / 6.0);
			if (this.tippleFillLevel >= Tipple.tippleCapacity) {
				this.isFull = true;
				this.tippleFillLevel = Tipple.tippleCapacity;

			}

			return this.crewFees();
		}
		return -1;
	}

	public int fillTrain(double hour) {
		int demurrageFees = 0;

		if (currentTrain.getNumberOfEngines() == 3) {
			double fillLevel = this.currentTrain.getFillLevel();
			this.currentTrain.setFillLevel(fillLevel + (1 / 3.0));

			tippleFillLevel -= this.currentTrain.getTrainCapacity() * (1 / 3.0);
			// checks the fill level of the train and departs when full
			if (currentTrain.getFillLevel() >= 0.99) {
				tippleFillLevel += this.currentTrain.getTrainCapacity() - currentTrain.getFillLevel();
				currentTrain.setIsFull(true);
				currentTrain.setDepartureTime(hour);
				demurrageFees = currentTrain.calculateTrainFees();
				currentTrain = null;
				// checks if que has trains
				if (trainsArrived.size() > 0) {
					currentTrain = trainsArrived.get(0);
					trainsArrived.remove(0);
				}

			}

		} else if (currentTrain.getNumberOfEngines() == 5) {
			double tippleFillLevel = this.currentTrain.getFillLevel();
			this.currentTrain.setFillLevel(tippleFillLevel + (1 / 6.0));
			// rate of decrease in tipple
			tippleFillLevel -= tippleCapacity * (1 / 6);
			// when train is full set to depart
			if (currentTrain.getFillLevel() >= .99) {
				tippleFillLevel += this.currentTrain.getTrainCapacity() - currentTrain.getFillLevel();
				currentTrain.setIsFull(true);
				currentTrain.setDepartureTime(hour);
				demurrageFees = currentTrain.calculateTrainFees();
				currentTrain = null;

				if (trainsArrived.size() > 0) {
					currentTrain = trainsArrived.get(0);
					trainsArrived.remove(0);
				}

			}

		}

		else {

		}
		// returns to main so value can be passed into print statements
		return demurrageFees;
	}

}