package com.devicediaglab.poc.snmp;

public class SNMPMain {
	
	public static void main (String[] args) throws Exception {
		
		// Reference: http://ireasoning.com/downloadmibbrowserfree.php
		// http://docs.oracle.com/cd/E19698-01/816-7609/6mdjrf88m/index.html
		// http://www.braindeadprojects.com/ancient/web/files/DOCSIS/docsdiag/
		
		// snmpwalk -v2c -c index demo.snmplabs.com 1.3.6
		
		/**
		 * Reference: 
		 * 	http://snmpsim.sourceforge.net/public-snmp-simulator.html
		 * 	http://snmpsim.sourceforge.net/
		 */
		String strHost = "demo.snmplabs.com";
		//String strHost = "104.236.166.95";		
		//String strHost = "127.0.0.1";
		
		int iPort = 161;
		String strCommunity = "public"; //"admin_test";
		int iVersion = 2;
		int iTimeout = 2000;
		int iRetry = 2;
		
		SNMPClient snmp = new SNMPClient(strHost, iPort, strCommunity, iVersion, iTimeout, iRetry);
		
		System.out.println(snmp.sendGet(".1.3.6.1.2.1.6.13.1.3.195.218.254.105.53.0.0.0.0.0"));
		System.out.println(snmp.sendGet(".1.3.6.1.2.1.2.2.1.5.1"));
		
		snmp.close();
		
		System.out.println("***************************");
		
		
		String oidmib2 = "mib2";
		String oidsnmp = "snmp";
		String oidhost = "host";
		Snmpwalk snmpwalk = new Snmpwalk(strHost, oidsnmp, strCommunity);
		snmpwalk.execSnmpwalk();
	}

}
