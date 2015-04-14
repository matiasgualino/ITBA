package utilities;


public class Chronometer {
	private long begin, end;
	
	public Chronometer(){
		super();
	}

	public void start() {
		begin = System.currentTimeMillis();
	}

	public void stop() {
		end = System.currentTimeMillis();
	}

	public long getFinalTime() {
		return end - begin;
	}

	public long getFinalMilliseconds() {
		return end - begin;
	}

	public double getFinalSeconds() {
		return (end - begin) / 1000.0;
	}

	public double getFinalMinutes() {
		return (end - begin) / 60000.0;
	}

	public double getFinalHours() {
		return (end - begin) / 3600000.0;
	}
	public long getTime() {
		return System.currentTimeMillis() - begin;
	}

	public long getMilliseconds() {
		return System.currentTimeMillis() - begin;
	}

	public double getSeconds() {
		return (System.currentTimeMillis() - begin) / 1000.0;
	}

	public double getMinutes() {
		return (System.currentTimeMillis() - begin) / 60000.0;
	}

	public double getHours() {
		return (System.currentTimeMillis() - begin) / 3600000.0;
	}
}
