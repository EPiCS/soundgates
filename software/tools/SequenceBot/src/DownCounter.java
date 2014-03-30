
public class DownCounter extends Thread {

	@Override
	public void run() {
		while (!isInterrupted()) {
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				// TODO Automatisch generierter Erfassungsblock
				e.printStackTrace();
			}
			if (manipulate > 0) {
				manipulate--;
			}
		}
	}

	int speed = 0;
	float manipulate;
	float scaler;

	public void add(float value){
		if (!isAlive()){
			manipulate = value / scaler;	
		} else {
			manipulate += value;
		}
		
	}
	
	public float get(){
		return manipulate * scaler;
	}
	
	public DownCounter(int speed, float toDownCount, float scaler) {
		manipulate = toDownCount;
		this.speed = speed;
		this.scaler = scaler;
	}

}
