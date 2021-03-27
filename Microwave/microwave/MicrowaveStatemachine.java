package microwave.microwave;

import microwave.ITimer;

public class MicrowaveStatemachine implements IMicrowaveStatemachine {
	protected class SCInterfaceImpl implements SCInterface {
	
		private SCInterfaceOperationCallback operationCallback;
		
		public void setSCInterfaceOperationCallback(
				SCInterfaceOperationCallback operationCallback) {
			this.operationCallback = operationCallback;
		}
		private boolean high;
		
		public void raiseHigh() {
			high = true;
			runCycle();
		}
		
		private boolean low;
		
		public void raiseLow() {
			low = true;
			runCycle();
		}
		
		private boolean digit;
		
		private long digitValue;
		
		public void raiseDigit(long value) {
			digit = true;
			digitValue = value;
			runCycle();
		}
		
		protected long getDigitValue() {
			if (! digit ) 
				throw new IllegalStateException("Illegal event value access. Event Digit is not raised!");
			return digitValue;
		}
		
		private boolean timer;
		
		public void raiseTimer() {
			timer = true;
			runCycle();
		}
		
		private boolean start;
		
		public void raiseStart() {
			start = true;
			runCycle();
		}
		
		private boolean stop;
		
		public void raiseStop() {
			stop = true;
			runCycle();
		}
		
		private boolean open;
		
		public void raiseOpen() {
			open = true;
			runCycle();
		}
		
		private boolean close;
		
		public void raiseClose() {
			close = true;
			runCycle();
		}
		
		private long power;
		
		public long getPower() {
			return power;
		}
		
		public void setPower(long value) {
			this.power = value;
		}
		
		protected void clearEvents() {
			high = false;
			low = false;
			digit = false;
			timer = false;
			start = false;
			stop = false;
			open = false;
			close = false;
		}
	}
	
	protected SCInterfaceImpl sCInterface;
	
	private boolean initialized = false;
	
	public enum State {
		micro_onde_main_Composant,
		micro_onde_main_Composant_r1_Puissance,
		micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance,
		micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low,
		micro_onde_main_Composant_r1_Puissance_r1_Puissance_High,
		micro_onde_main_Composant_r1_Timer,
		micro_onde_main_Composant_r1_Timer_r2_Timer_Ready,
		micro_onde_main_Composant_r1_Timer_r2__1_digit,
		micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits,
		micro_onde_main_Composant_r1_Cuisson,
		micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking,
		micro_onde_main_Composant_r1_Cuisson_r3_still_time,
		micro_onde_main_Composant_r1_Cuisson_r3_Beep_on,
		micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte,
		micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee,
		micro_onde_main_Composant_r1_Cuisson_r3_Beep_off,
		micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking,
		micro_onde_main_Composant_porte_Init_Porte,
		micro_onde_main_Composant_porte_Porte_Ferm_e,
		micro_onde_main_Composant_porte_Porte_Ouverte,
		$NullState$
	};
	
	private final State[] stateVector = new State[2];
	
	private int nextStateIndex;
	
	
	private ITimer timer;
	
	private final boolean[] timeEvents = new boolean[5];
	private long time;
	
	protected void setTime(long value) {
		time = value;
	}
	
	protected long getTime() {
		return time;
	}
	
	private long noDigits;
	
	protected void setNoDigits(long value) {
		noDigits = value;
	}
	
	protected long getNoDigits() {
		return noDigits;
	}
	
	private long count;
	
	protected void setCount(long value) {
		count = value;
	}
	
	protected long getCount() {
		return count;
	}
	
	private boolean doorOpen;
	
	protected void setDoorOpen(boolean value) {
		doorOpen = value;
	}
	
	protected boolean getDoorOpen() {
		return doorOpen;
	}
	
	public MicrowaveStatemachine() {
		sCInterface = new SCInterfaceImpl();
	}
	
	public void init() {
		this.initialized = true;
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		if (this.sCInterface.operationCallback == null) {
			throw new IllegalStateException("Operation callback for interface sCInterface must be set.");
		}
		
		for (int i = 0; i < 2; i++) {
			stateVector[i] = State.$NullState$;
		}
		clearEvents();
		clearOutEvents();
		sCInterface.setPower(0);
		
		setTime(0);
		
		setNoDigits(0);
		
		setCount(5);
		
		setDoorOpen(false);
	}
	
	public void enter() {
		if (!initialized) {
			throw new IllegalStateException(
				"The state machine needs to be initialized first by calling the init() function."
			);
		}
		if (timer == null) {
			throw new IllegalStateException("timer not set.");
		}
		enterSequence_Micro_onde_main_default();
	}
	
	public void runCycle() {
		if (!initialized)
			throw new IllegalStateException(
					"The state machine needs to be initialized first by calling the init() function.");
		clearOutEvents();
		for (nextStateIndex = 0; nextStateIndex < stateVector.length; nextStateIndex++) {
			switch (stateVector[nextStateIndex]) {
			case micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance:
				micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance_react(true);
				break;
			case micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low:
				micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low_react(true);
				break;
			case micro_onde_main_Composant_r1_Puissance_r1_Puissance_High:
				micro_onde_main_Composant_r1_Puissance_r1_Puissance_High_react(true);
				break;
			case micro_onde_main_Composant_r1_Timer_r2_Timer_Ready:
				micro_onde_main_Composant_r1_Timer_r2_Timer_Ready_react(true);
				break;
			case micro_onde_main_Composant_r1_Timer_r2__1_digit:
				micro_onde_main_Composant_r1_Timer_r2__1_digit_react(true);
				break;
			case micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits:
				micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking:
				micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_still_time:
				micro_onde_main_Composant_r1_Cuisson_r3_still_time_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Beep_on:
				micro_onde_main_Composant_r1_Cuisson_r3_Beep_on_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte:
				micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee:
				micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Beep_off:
				micro_onde_main_Composant_r1_Cuisson_r3_Beep_off_react(true);
				break;
			case micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking:
				micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking_react(true);
				break;
			case micro_onde_main_Composant_porte_Init_Porte:
				micro_onde_main_Composant_porte_Init_Porte_react(true);
				break;
			case micro_onde_main_Composant_porte_Porte_Ferm_e:
				micro_onde_main_Composant_porte_Porte_Ferm_e_react(true);
				break;
			case micro_onde_main_Composant_porte_Porte_Ouverte:
				micro_onde_main_Composant_porte_Porte_Ouverte_react(true);
				break;
			default:
				// $NullState$
			}
		}
		clearEvents();
	}
	public void exit() {
		exitSequence_Micro_onde_main();
	}
	
	/**
	 * @see IStatemachine#isActive()
	 */
	public boolean isActive() {
		return stateVector[0] != State.$NullState$||stateVector[1] != State.$NullState$;
	}
	
	/** 
	* Always returns 'false' since this state machine can never become final.
	*
	* @see IStatemachine#isFinal()
	*/
	public boolean isFinal() {
		return false;
	}
	/**
	* This method resets the incoming events (time events included).
	*/
	protected void clearEvents() {
		sCInterface.clearEvents();
		for (int i=0; i<timeEvents.length; i++) {
			timeEvents[i] = false;
		}
	}
	
	/**
	* This method resets the outgoing events.
	*/
	protected void clearOutEvents() {
	}
	
	/**
	* Returns true if the given state is currently active otherwise false.
	*/
	public boolean isStateActive(State state) {
	
		switch (state) {
		case micro_onde_main_Composant:
			return stateVector[0].ordinal() >= State.
					micro_onde_main_Composant.ordinal()&& stateVector[0].ordinal() <= State.micro_onde_main_Composant_porte_Porte_Ouverte.ordinal();
		case micro_onde_main_Composant_r1_Puissance:
			return stateVector[0].ordinal() >= State.
					micro_onde_main_Composant_r1_Puissance.ordinal()&& stateVector[0].ordinal() <= State.micro_onde_main_Composant_r1_Puissance_r1_Puissance_High.ordinal();
		case micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_High:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Puissance_r1_Puissance_High;
		case micro_onde_main_Composant_r1_Timer:
			return stateVector[0].ordinal() >= State.
					micro_onde_main_Composant_r1_Timer.ordinal()&& stateVector[0].ordinal() <= State.micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits.ordinal();
		case micro_onde_main_Composant_r1_Timer_r2_Timer_Ready:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Timer_r2_Timer_Ready;
		case micro_onde_main_Composant_r1_Timer_r2__1_digit:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Timer_r2__1_digit;
		case micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits;
		case micro_onde_main_Composant_r1_Cuisson:
			return stateVector[0].ordinal() >= State.
					micro_onde_main_Composant_r1_Cuisson.ordinal()&& stateVector[0].ordinal() <= State.micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking.ordinal();
		case micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking;
		case micro_onde_main_Composant_r1_Cuisson_r3_still_time:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_still_time;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_on:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Beep_on;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_off:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Beep_off;
		case micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking:
			return stateVector[0] == State.micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking;
		case micro_onde_main_Composant_porte_Init_Porte:
			return stateVector[1] == State.micro_onde_main_Composant_porte_Init_Porte;
		case micro_onde_main_Composant_porte_Porte_Ferm_e:
			return stateVector[1] == State.micro_onde_main_Composant_porte_Porte_Ferm_e;
		case micro_onde_main_Composant_porte_Porte_Ouverte:
			return stateVector[1] == State.micro_onde_main_Composant_porte_Porte_Ouverte;
		default:
			return false;
		}
	}
	
	/**
	* Set the {@link ITimer} for the state machine. It must be set
	* externally on a timed state machine before a run cycle can be correctly
	* executed.
	* 
	* @param timer
	*/
	public void setTimer(ITimer timer) {
		this.timer = timer;
	}
	
	/**
	* Returns the currently used timer.
	* 
	* @return {@link ITimer}
	*/
	public ITimer getTimer() {
		return timer;
	}
	
	public void timeElapsed(int eventID) {
		timeEvents[eventID] = true;
		runCycle();
	}
	
	public SCInterface getSCInterface() {
		return sCInterface;
	}
	
	public void raiseHigh() {
		sCInterface.raiseHigh();
	}
	
	public void raiseLow() {
		sCInterface.raiseLow();
	}
	
	public void raiseDigit(long value) {
		sCInterface.raiseDigit(value);
	}
	
	public void raiseTimer() {
		sCInterface.raiseTimer();
	}
	
	public void raiseStart() {
		sCInterface.raiseStart();
	}
	
	public void raiseStop() {
		sCInterface.raiseStop();
	}
	
	public void raiseOpen() {
		sCInterface.raiseOpen();
	}
	
	public void raiseClose() {
		sCInterface.raiseClose();
	}
	
	public long getPower() {
		return sCInterface.getPower();
	}
	
	public void setPower(long value) {
		sCInterface.setPower(value);
	}
	
	private void effect_Micro_onde_main_Composant_r1_Cuisson_tr1() {
		exitSequence_Micro_onde_main_Composant_r1_Cuisson();
		enterSequence_Micro_onde_main_Composant_r1_Puissance_default();
	}
	
	/* Entry action for state 'Init Puissance'. */
	private void entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance() {
		sCInterface.operationCallback.clearDisplay();
		
		setNoDigits(0);
		
		setCount(5);
	}
	
	/* Entry action for state 'Puissance Low'. */
	private void entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low() {
		sCInterface.operationCallback.display("Low");
		
		sCInterface.setPower(1);
	}
	
	/* Entry action for state 'Puissance High'. */
	private void entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High() {
		sCInterface.operationCallback.display("High");
		
		sCInterface.setPower(2);
	}
	
	/* Entry action for state 'Timer Ready'. */
	private void entryAction_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready() {
		sCInterface.operationCallback.clearDisplay();
		
		setTime(0);
	}
	
	/* Entry action for state '1 digit'. */
	private void entryAction_Micro_onde_main_Composant_r1_Timer_r2__1_digit() {
		sCInterface.operationCallback.displayTime(sCInterface.getDigitValue());
		
		setTime(sCInterface.getDigitValue());
	}
	
	/* Entry action for state '2 digit -> 4 digits'. */
	private void entryAction_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits() {
		setTime((time * 10));
		
		setTime(getTime() + (sCInterface.getDigitValue()));
		
		sCInterface.operationCallback.clearDisplay();
		
		sCInterface.operationCallback.displayTime(getTime());
		
		setNoDigits((noDigits + 1));
	}
	
	/* Entry action for state 'Begin Cooking'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking() {
		timer.setTimer(this, 0, (0 * 1000), false);
		
		sCInterface.operationCallback.cook();
	}
	
	/* Entry action for state 'still time'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_still_time() {
		timer.setTimer(this, 1, (1 * 1000), false);
		
		setTime((time - 1));
	}
	
	/* Entry action for state 'Beep on'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on() {
		timer.setTimer(this, 2, (1 * 1000), false);
		
		sCInterface.operationCallback.beepOn();
		
		sCInterface.operationCallback.display("Finished");
	}
	
	/* Entry action for state 'Porte ouverte'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte() {
		sCInterface.operationCallback.clearDisplay();
		
		sCInterface.operationCallback.display("Waiting");
		
		sCInterface.operationCallback.stopCook();
		
		sCInterface.operationCallback.openDoor();
	}
	
	/* Entry action for state 'Porte fermee'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee() {
		sCInterface.operationCallback.clearDisplay();
		
		sCInterface.operationCallback.displayTime(getTime());
	}
	
	/* Entry action for state 'Beep off'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off() {
		timer.setTimer(this, 3, (1 * 1000), false);
		
		setCount((count - 1));
		
		sCInterface.operationCallback.clearDisplay();
		
		sCInterface.operationCallback.beepOff();
	}
	
	/* Entry action for state 'Stop cooking'. */
	private void entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking() {
		timer.setTimer(this, 4, (0 * 1000), false);
		
		sCInterface.operationCallback.stopCook();
		
		sCInterface.operationCallback.clearDisplay();
	}
	
	/* Entry action for state 'Porte Fermée'. */
	private void entryAction_Micro_onde_main_Composant_porte_Porte_Ferm_e() {
		sCInterface.operationCallback.closeDoor();
		
		setDoorOpen(false);
	}
	
	/* Entry action for state 'Porte Ouverte'. */
	private void entryAction_Micro_onde_main_Composant_porte_Porte_Ouverte() {
		sCInterface.operationCallback.openDoor();
		
		setDoorOpen(true);
	}
	
	/* Exit action for state 'Begin Cooking'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking() {
		timer.unsetTimer(this, 0);
	}
	
	/* Exit action for state 'still time'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_still_time() {
		timer.unsetTimer(this, 1);
		
		sCInterface.operationCallback.clearDisplay();
		
		sCInterface.operationCallback.displayTime(getTime());
	}
	
	/* Exit action for state 'Beep on'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on() {
		timer.unsetTimer(this, 2);
	}
	
	/* Exit action for state 'Porte fermee'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee() {
		sCInterface.operationCallback.closeDoor();
	}
	
	/* Exit action for state 'Beep off'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off() {
		timer.unsetTimer(this, 3);
	}
	
	/* Exit action for state 'Stop cooking'. */
	private void exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking() {
		timer.unsetTimer(this, 4);
	}
	
	/* 'default' enter sequence for state Composant */
	private void enterSequence_Micro_onde_main_Composant_default() {
		enterSequence_Micro_onde_main_Composant_r1_default();
		enterSequence_Micro_onde_main_Composant_porte_default();
	}
	
	/* 'default' enter sequence for state Puissance */
	private void enterSequence_Micro_onde_main_Composant_r1_Puissance_default() {
		enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_default();
	}
	
	/* 'default' enter sequence for state Init Puissance */
	private void enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance_default() {
		entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance;
	}
	
	/* 'default' enter sequence for state Puissance Low */
	private void enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low_default() {
		entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low;
	}
	
	/* 'default' enter sequence for state Puissance High */
	private void enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High_default() {
		entryAction_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Puissance_r1_Puissance_High;
	}
	
	/* 'default' enter sequence for state Timer */
	private void enterSequence_Micro_onde_main_Composant_r1_Timer_default() {
		enterSequence_Micro_onde_main_Composant_r1_Timer_r2_default();
	}
	
	/* 'default' enter sequence for state Timer Ready */
	private void enterSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready_default() {
		entryAction_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Timer_r2_Timer_Ready;
	}
	
	/* 'default' enter sequence for state 1 digit */
	private void enterSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit_default() {
		entryAction_Micro_onde_main_Composant_r1_Timer_r2__1_digit();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Timer_r2__1_digit;
	}
	
	/* 'default' enter sequence for state 2 digit -> 4 digits */
	private void enterSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits_default() {
		entryAction_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits;
	}
	
	/* 'default' enter sequence for state Cuisson */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_default() {
		enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_default();
	}
	
	/* 'default' enter sequence for state Begin Cooking */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking;
	}
	
	/* 'default' enter sequence for state still time */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_still_time;
	}
	
	/* 'default' enter sequence for state Beep on */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Beep_on;
	}
	
	/* 'default' enter sequence for state Porte ouverte */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte;
	}
	
	/* 'default' enter sequence for state Porte fermee */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee;
	}
	
	/* 'default' enter sequence for state Beep off */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Beep_off;
	}
	
	/* 'default' enter sequence for state Stop cooking */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking_default() {
		entryAction_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
		nextStateIndex = 0;
		stateVector[0] = State.micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking;
	}
	
	/* 'default' enter sequence for state Init Porte */
	private void enterSequence_Micro_onde_main_Composant_porte_Init_Porte_default() {
		nextStateIndex = 1;
		stateVector[1] = State.micro_onde_main_Composant_porte_Init_Porte;
	}
	
	/* 'default' enter sequence for state Porte Fermée */
	private void enterSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e_default() {
		entryAction_Micro_onde_main_Composant_porte_Porte_Ferm_e();
		nextStateIndex = 1;
		stateVector[1] = State.micro_onde_main_Composant_porte_Porte_Ferm_e;
	}
	
	/* 'default' enter sequence for state Porte Ouverte */
	private void enterSequence_Micro_onde_main_Composant_porte_Porte_Ouverte_default() {
		entryAction_Micro_onde_main_Composant_porte_Porte_Ouverte();
		nextStateIndex = 1;
		stateVector[1] = State.micro_onde_main_Composant_porte_Porte_Ouverte;
	}
	
	/* 'default' enter sequence for region Micro onde main */
	private void enterSequence_Micro_onde_main_default() {
		react_microwave_Micro_onde_main__entry_Default();
	}
	
	/* 'default' enter sequence for region r1 */
	private void enterSequence_Micro_onde_main_Composant_r1_default() {
		react_microwave_Micro_onde_main_Composant_r1__entry_Default();
	}
	
	/* 'default' enter sequence for region r1 */
	private void enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_default() {
		react_microwave_Micro_onde_main_Composant_r1_Puissance_r1__entry_Default();
	}
	
	/* 'default' enter sequence for region r2 */
	private void enterSequence_Micro_onde_main_Composant_r1_Timer_r2_default() {
		react_microwave_Micro_onde_main_Composant_r1_Timer_r2__entry_Default();
	}
	
	/* 'default' enter sequence for region r3 */
	private void enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_default() {
		react_microwave_Micro_onde_main_Composant_r1_Cuisson_r3__entry_Default();
	}
	
	/* 'default' enter sequence for region porte */
	private void enterSequence_Micro_onde_main_Composant_porte_default() {
		react_microwave_Micro_onde_main_Composant_porte__entry_Default();
	}
	
	/* Default exit sequence for state Puissance */
	private void exitSequence_Micro_onde_main_Composant_r1_Puissance() {
		exitSequence_Micro_onde_main_Composant_r1_Puissance_r1();
	}
	
	/* Default exit sequence for state Init Puissance */
	private void exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Puissance Low */
	private void exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Puissance High */
	private void exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Timer */
	private void exitSequence_Micro_onde_main_Composant_r1_Timer() {
		exitSequence_Micro_onde_main_Composant_r1_Timer_r2();
	}
	
	/* Default exit sequence for state Timer Ready */
	private void exitSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state 1 digit */
	private void exitSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state 2 digit -> 4 digits */
	private void exitSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Cuisson */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson() {
		exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3();
	}
	
	/* Default exit sequence for state Begin Cooking */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
	}
	
	/* Default exit sequence for state still time */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
	}
	
	/* Default exit sequence for state Beep on */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
	}
	
	/* Default exit sequence for state Porte ouverte */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
	}
	
	/* Default exit sequence for state Porte fermee */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
	}
	
	/* Default exit sequence for state Beep off */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
	}
	
	/* Default exit sequence for state Stop cooking */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking() {
		nextStateIndex = 0;
		stateVector[0] = State.$NullState$;
		
		exitAction_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
	}
	
	/* Default exit sequence for state Init Porte */
	private void exitSequence_Micro_onde_main_Composant_porte_Init_Porte() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state Porte Fermée */
	private void exitSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for state Porte Ouverte */
	private void exitSequence_Micro_onde_main_Composant_porte_Porte_Ouverte() {
		nextStateIndex = 1;
		stateVector[1] = State.$NullState$;
	}
	
	/* Default exit sequence for region Micro onde main */
	private void exitSequence_Micro_onde_main() {
		switch (stateVector[0]) {
		case micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_High:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High();
			break;
		case micro_onde_main_Composant_r1_Timer_r2_Timer_Ready:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__1_digit:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_still_time:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_on:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_off:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
			break;
		default:
			break;
		}
		
		switch (stateVector[1]) {
		case micro_onde_main_Composant_porte_Init_Porte:
			exitSequence_Micro_onde_main_Composant_porte_Init_Porte();
			break;
		case micro_onde_main_Composant_porte_Porte_Ferm_e:
			exitSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e();
			break;
		case micro_onde_main_Composant_porte_Porte_Ouverte:
			exitSequence_Micro_onde_main_Composant_porte_Porte_Ouverte();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_Micro_onde_main_Composant_r1() {
		switch (stateVector[0]) {
		case micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_High:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High();
			break;
		case micro_onde_main_Composant_r1_Timer_r2_Timer_Ready:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__1_digit:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_still_time:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_on:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_off:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r1 */
	private void exitSequence_Micro_onde_main_Composant_r1_Puissance_r1() {
		switch (stateVector[0]) {
		case micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low();
			break;
		case micro_onde_main_Composant_r1_Puissance_r1_Puissance_High:
			exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r2 */
	private void exitSequence_Micro_onde_main_Composant_r1_Timer_r2() {
		switch (stateVector[0]) {
		case micro_onde_main_Composant_r1_Timer_r2_Timer_Ready:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__1_digit:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit();
			break;
		case micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits:
			exitSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region r3 */
	private void exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3() {
		switch (stateVector[0]) {
		case micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_still_time:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_on:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Beep_off:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
			break;
		case micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking:
			exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
			break;
		default:
			break;
		}
	}
	
	/* Default exit sequence for region porte */
	private void exitSequence_Micro_onde_main_Composant_porte() {
		switch (stateVector[1]) {
		case micro_onde_main_Composant_porte_Init_Porte:
			exitSequence_Micro_onde_main_Composant_porte_Init_Porte();
			break;
		case micro_onde_main_Composant_porte_Porte_Ferm_e:
			exitSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e();
			break;
		case micro_onde_main_Composant_porte_Porte_Ouverte:
			exitSequence_Micro_onde_main_Composant_porte_Porte_Ouverte();
			break;
		default:
			break;
		}
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main__entry_Default() {
		enterSequence_Micro_onde_main_Composant_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main_Composant_r1_Puissance_r1__entry_Default() {
		enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main_Composant_r1_Timer_r2__entry_Default() {
		enterSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main_Composant_r1_Cuisson_r3__entry_Default() {
		enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main_Composant_r1__entry_Default() {
		enterSequence_Micro_onde_main_Composant_r1_Puissance_default();
	}
	
	/* Default react sequence for initial entry  */
	private void react_microwave_Micro_onde_main_Composant_porte__entry_Default() {
		enterSequence_Micro_onde_main_Composant_porte_Init_Porte_default();
	}
	
	/* The reactions of exit default. */
	private void react_microwave_Micro_onde_main_Composant_r1_Cuisson_r3__exit_Default() {
		effect_Micro_onde_main_Composant_r1_Cuisson_tr1();
	}
	
	private boolean react() {
		return false;
	}
	
	private boolean micro_onde_main_Composant_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (react()==false) {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Puissance_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_react(try_transition)==false) {
				if (((sCInterface.timer) && (sCInterface.getPower()!=0))) {
					exitSequence_Micro_onde_main_Composant_r1_Puissance();
					enterSequence_Micro_onde_main_Composant_r1_Timer_default();
				} else {
					if (sCInterface.stop) {
						exitSequence_Micro_onde_main_Composant_r1_Puissance();
						enterSequence_Micro_onde_main_Composant_r1_Puissance_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Puissance_react(try_transition)==false) {
				if (sCInterface.low) {
					exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
					enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low_default();
				} else {
					if (sCInterface.high) {
						exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Init_Puissance();
						enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Puissance_react(try_transition)==false) {
				if (sCInterface.high) {
					exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low();
					enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Puissance_r1_Puissance_High_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Puissance_react(try_transition)==false) {
				if (sCInterface.low) {
					exitSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_High();
					enterSequence_Micro_onde_main_Composant_r1_Puissance_r1_Puissance_Low_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Timer_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_react(try_transition)==false) {
				if (((sCInterface.start) && (getDoorOpen()==false))) {
					exitSequence_Micro_onde_main_Composant_r1_Timer();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_default();
				} else {
					if (sCInterface.timer) {
						exitSequence_Micro_onde_main_Composant_r1_Timer();
						enterSequence_Micro_onde_main_Composant_r1_Timer_default();
					} else {
						if (((sCInterface.start) && (getTime()==0))) {
							exitSequence_Micro_onde_main_Composant_r1_Timer();
							enterSequence_Micro_onde_main_Composant_r1_Timer_default();
						} else {
							did_transition = false;
						}
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Timer_r2_Timer_Ready_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Timer_react(try_transition)==false) {
				if (sCInterface.digit) {
					exitSequence_Micro_onde_main_Composant_r1_Timer_r2_Timer_Ready();
					enterSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Timer_r2__1_digit_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Timer_react(try_transition)==false) {
				if (sCInterface.digit) {
					exitSequence_Micro_onde_main_Composant_r1_Timer_r2__1_digit();
					enterSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Timer_react(try_transition)==false) {
				if (((sCInterface.digit) && (getNoDigits()<3))) {
					exitSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits();
					enterSequence_Micro_onde_main_Composant_r1_Timer_r2__2_digit____4_digits_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_react(try_transition)==false) {
				if (sCInterface.stop) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson();
					enterSequence_Micro_onde_main_Composant_r1_Puissance_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (timeEvents[0]) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time_default();
				} else {
					if (sCInterface.open) {
						exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking();
						enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_still_time_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (sCInterface.open) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_default();
				} else {
					if (((timeEvents[1]) && (getTime()>0))) {
						exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
						enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time_default();
					} else {
						if (getTime()==0) {
							exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_still_time();
							enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking_default();
						} else {
							did_transition = false;
						}
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Beep_on_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (timeEvents[2]) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (sCInterface.close) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (sCInterface.open) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_ouverte_default();
				} else {
					if (sCInterface.start) {
						exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Porte_fermee();
						enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Begin_Cooking_default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Beep_off_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (((timeEvents[3]) && (getCount()>0))) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on_default();
				} else {
					if (getCount()==0) {
						exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_off();
						react_microwave_Micro_onde_main_Composant_r1_Cuisson_r3__exit_Default();
					} else {
						did_transition = false;
					}
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (micro_onde_main_Composant_r1_Cuisson_react(try_transition)==false) {
				if (timeEvents[4]) {
					exitSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Stop_cooking();
					enterSequence_Micro_onde_main_Composant_r1_Cuisson_r3_Beep_on_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_porte_Init_Porte_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.open) {
				exitSequence_Micro_onde_main_Composant_porte_Init_Porte();
				enterSequence_Micro_onde_main_Composant_porte_Porte_Ouverte_default();
			} else {
				if (sCInterface.close) {
					exitSequence_Micro_onde_main_Composant_porte_Init_Porte();
					enterSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e_default();
				} else {
					did_transition = false;
				}
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_porte_Porte_Ferm_e_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.open) {
				exitSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e();
				enterSequence_Micro_onde_main_Composant_porte_Porte_Ouverte_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
	private boolean micro_onde_main_Composant_porte_Porte_Ouverte_react(boolean try_transition) {
		boolean did_transition = try_transition;
		
		if (try_transition) {
			if (sCInterface.close) {
				exitSequence_Micro_onde_main_Composant_porte_Porte_Ouverte();
				enterSequence_Micro_onde_main_Composant_porte_Porte_Ferm_e_default();
			} else {
				did_transition = false;
			}
		}
		return did_transition;
	}
	
}
