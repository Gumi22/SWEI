package uebungen;

import mycrm.Main;
import BIF.SWE2.Interfaces.IApplication;
import BIF.SWE2.Interfaces.IUEB1;

public class UEB1Impl implements IUEB1 {

	@Override
	public IApplication GetApplication() {
		return new Main();
	}

	@Override
	public void HelloWorld() {
		// I'm fine		
	}
}