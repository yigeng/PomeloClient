package com.klaus.test;

public class PvETest {
	public static void main (String args[])
	{
		String uri_dev = "http://115.28.21.134:14100";
        String uri_localhost = "http://127.0.0.1:14100";
        String uri_ledou = "http://182.254.190.144:14100";
        String key = "alfxkwld";
		for (int i=0;i<1;i++)
		{
			int score = (int)(Math.random()*4000);
			//System.out.println(score);
			String user = ""+i;
			//System.out.println(user);
			try {
				int time = (int)(Math.random()*2000);
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PomeloAgent agent = new PomeloAgent(uri_ledou,key,user,score);
			agent.pvp();
		}
	}
}
