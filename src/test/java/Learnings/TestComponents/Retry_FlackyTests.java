package Learnings.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry_FlackyTests implements IRetryAnalyzer {
	
	int count = 0;
	int maxTry = 1;
	
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		
		if(count<maxTry) {
			count++;
			return true;
			
		}
		return false;
	}

}
