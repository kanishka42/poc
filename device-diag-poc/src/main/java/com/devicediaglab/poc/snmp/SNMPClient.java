package com.devicediaglab.poc.snmp;

import java.util.Hashtable;
import java.util.Vector;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SNMPClient {
	
	// SNMP variable
	private Snmp msnmpClient = null;
	
	// transport mapping variable
	private TransportMapping mTransportMappings = null;
	
	// Community target consist of all snmp information
	private CommunityTarget communityTarget = null;
	
	/**
	 * 
	 * @param strHost = server host
	 * @param iPort = server port
	 * @param strCommunity = this is the same user name in ssh let set right for u can monitor server or read or write on server
	 * @param iVersion = set version for snmp 1, 2c, 3 (in here snmp v2)
	 * @param iTimeout = set timeout after u send request and wait result from server (very important belong to network)
	 * @param iRetry = set times retries when send request after timeout
	 * @throws Exception
	 */
	public SNMPClient(String strHost, int iPort, String strCommunity, int iVersion, int iTimeout, int iRetry) throws Exception {
		
		this.mTransportMappings = new DefaultUdpTransportMapping();
		
		this.mTransportMappings.listen();
		
		this.msnmpClient = new Snmp(this.mTransportMappings);
		
		this.communityTarget = new CommunityTarget();
		
		
		//set all parameters
		this.communityTarget.setAddress(new UdpAddress(strHost + "/" + iPort));
		this.communityTarget.setCommunity(new OctetString(strCommunity));
		this.communityTarget.setRetries(iRetry);
		this.communityTarget.setTimeout(iTimeout);
		this.communityTarget.setVersion(iVersion -1);
	}
	
	/**
	 * Send get function
	 * @param strOID
	 * @return
	 */
	public String sendGet(String strOID) {
		String strResult = null;
		
		PDU pdu = new PDU();
		
		try {
			// add oid into PDU
			pdu.add(new VariableBinding (new OID(strOID)));
			pdu.setType(PDU.GET);
			ResponseEvent response = this.msnmpClient.send(pdu, this.communityTarget);
			
			if (response != null) {
				PDU pduResult = response.getResponse();
				
				if (pduResult.getErrorStatus() == PDU.noError) {
					Variable variable = pduResult.getVariable(new OID(strOID));
					
					if (variable != null) {
						strResult = variable.toString();
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		pdu.clear();
		
		return strResult;
	}
	
	
	public String sendGetNext(String strOID) {
		String strResult = null;
		
		PDU pdu = new PDU();
		
		try {
			pdu.add(new VariableBinding(new OID(strOID)));
			pdu.setType(PDU.GETNEXT);
			ResponseEvent response = this.msnmpClient.send(pdu, this.communityTarget);
			
			if (response != null) {
				PDU pduResult = response.getResponse();
				
				if (pduResult.getErrorStatus() == PDU.noError) {
					VariableBinding variable = pduResult.getVariableBindings().get(0);
					
					if (variable != null) {
						strResult = variable.toValueString();
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		pdu.clear();
		
		return strResult;
	}
	
	public Hashtable<String, String> sendGetBulk (String strOID) {
		Hashtable<String, String> hResult = null;
		
		PDU pdu = new PDU();
		
		try {
			pdu.add(new VariableBinding(new OID(strOID)));
			pdu.setType(PDU.GETBULK);
			pdu.setMaxRepetitions(20);
			
			ResponseEvent response = this.msnmpClient.send(pdu, this.communityTarget);
			
			if (response != null) {
				hResult = new Hashtable<>();
				
				PDU pduResult = response.getResponse();
				
				if (pduResult.getErrorStatus() == PDU.noError) {
					
					Vector <? extends VariableBinding> vtVariable = pduResult.getVariableBindings();
					
					if (vtVariable != null) {
						
						for (VariableBinding variable : vtVariable) {
							hResult.put(variable.getOid().toString(), variable.toValueString());
						}
					}
				}
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		pdu.clear();
		
		return hResult;
	}
	
	/**
	 * Close all in/out streams
	 */
	public void close() {
		try {
			
			if (this.mTransportMappings != null) {
				this.mTransportMappings.close();
			}
			
		} catch (Exception exp) {
			exp.printStackTrace();
		}
		
		try {
			
			if (this.msnmpClient != null) {
				this.msnmpClient.close();
			}
			
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

}
